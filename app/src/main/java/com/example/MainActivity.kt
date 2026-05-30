package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.Hymn
import com.example.data.HymnDataProvider
import com.example.data.FavoriteDatabase
import com.example.data.FavoriteRepository
import com.example.ui.theme.MyApplicationTheme
import com.example.util.PdfGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            MyApplicationTheme(darkTheme = isDarkMode, dynamicColor = false) {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("main_scaffold")
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        HymnApp(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

// Helper data class holding cached normalized contents of the hymn for instant searches with zero GC allocations
data class SearchableHymn(
    val hymn: com.example.data.Hymn,
    val normalizedTitle: String,
    val normalizedContent: String,
    val splitStanzas: List<String>
)

// Represents a unique occurrence of a searched term
data class MatchOccurrence(
    val hymnId: Int,
    val isTitle: Boolean,
    val stanzaIndex: Int, // -1 if isTitle is true
    val charRange: IntRange
)

// Scroll event triggers to let the compose list react and jump to the target item
data class ScrollEvent(
    val listIndex: Int,
    val matchOccurrence: MatchOccurrence,
    val timestamp: Long
)

// Clean string normalizer to strip accents and convert to lowercase for flawless typo-resistant matching
fun String.normalize(): String {
    val temp = java.text.Normalizer.normalize(this, java.text.Normalizer.Form.NFD)
    val pattern = java.util.regex.Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(temp).replaceAll("").lowercase()
}

// ViewModel reactivo para manejar la búsqueda instantánea, el tamaño de fuente, el tema y la navegación de ocurrencias
enum class ScreenType {
    ALL, FAVORITES
}

// ViewModel reactivo para manejar la búsqueda instantánea, el tamaño de fuente, el tema y la navegación de ocurrencias
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteDatabase = FavoriteDatabase.getDatabase(application)
    private val favoriteRepository = FavoriteRepository(favoriteDatabase.favoriteHymnDao())

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _readerFontSize = MutableStateFlow(18f)
    val readerFontSize = _readerFontSize.asStateFlow()

    private val _currentScreen = MutableStateFlow(ScreenType.ALL)
    val currentScreen = _currentScreen.asStateFlow()

    private val _favoriteHymnIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteHymnIds = _favoriteHymnIds.asStateFlow()

    // Cache the searchable dataset immediately on initialization (0ms search latency & optimized scroll rendering)
    private val searchableHymns: List<SearchableHymn> = HymnDataProvider.hymns.map { hymn ->
        val displayTitle = "${hymn.id} - ${hymn.title}"
        SearchableHymn(
            hymn = hymn,
            normalizedTitle = displayTitle.normalize(),
            normalizedContent = hymn.content.normalize(),
            splitStanzas = hymn.content.split("\n\n")
        )
    }

    private val _filteredHymns = MutableStateFlow<List<SearchableHymn>>(searchableHymns)
    val filteredHymns = _filteredHymns.asStateFlow()

    private val _globalMatches = MutableStateFlow<List<MatchOccurrence>>(emptyList())
    val globalMatches = _globalMatches.asStateFlow()

    private val _currentMatchIndex = MutableStateFlow(-1)
    val currentMatchIndex = _currentMatchIndex.asStateFlow()

    private val _scrollToItemEvent = MutableStateFlow<ScrollEvent?>(null)
    val scrollToItemEvent = _scrollToItemEvent.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteRepository.favoriteHymnIds.collect { ids ->
                _favoriteHymnIds.value = ids.toSet()
                recalculateFilteredHymns()
            }
        }
    }

    fun setScreen(screen: ScreenType) {
        _currentScreen.value = screen
        recalculateFilteredHymns()
    }

    fun toggleFavorite(hymnId: Int) {
        viewModelScope.launch {
            val favorites = _favoriteHymnIds.value
            if (favorites.contains(hymnId)) {
                favoriteRepository.removeFavorite(hymnId)
            } else {
                favoriteRepository.addFavorite(hymnId)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        recalculateFilteredHymns()
    }

    fun recalculateFilteredHymns() {
        val query = _searchQuery.value
        val screen = _currentScreen.value
        val favorites = _favoriteHymnIds.value

        val trimmedQuery = query.trim().normalize()
        
        // Map hymns to include their current favorite state
        val hymnsSource = searchableHymns.map { item ->
            val isFav = favorites.contains(item.hymn.id)
            if (item.hymn.isFavorite != isFav) {
                item.copy(hymn = item.hymn.copy(isFavorite = isFav))
            } else {
                item
            }
        }

        // Apply screen filter
        val screenFiltered = if (screen == ScreenType.FAVORITES) {
            hymnsSource.filter { it.hymn.isFavorite }
        } else {
            hymnsSource
        }

        // Apply search query filter
        val finalFiltered = if (trimmedQuery.isEmpty()) {
            screenFiltered
        } else {
            screenFiltered.filter { item ->
                item.hymn.id.toString() == trimmedQuery ||
                        item.normalizedTitle.contains(trimmedQuery) ||
                        item.normalizedContent.contains(trimmedQuery)
            }
        }

        _filteredHymns.value = finalFiltered

        // Recompute global matches for highlights
        if (trimmedQuery.isEmpty()) {
            _globalMatches.value = emptyList()
            _currentMatchIndex.value = -1
            _scrollToItemEvent.value = null
        } else {
            val matches = mutableListOf<MatchOccurrence>()
            finalFiltered.forEach { item ->
                val hymnId = item.hymn.id
                
                // Title matches
                var index = item.normalizedTitle.indexOf(trimmedQuery)
                while (index != -1 && trimmedQuery.isNotEmpty()) {
                    matches.add(
                        MatchOccurrence(
                            hymnId = hymnId,
                            isTitle = true,
                            stanzaIndex = -1,
                            charRange = index until (index + trimmedQuery.length)
                        )
                    )
                    index = item.normalizedTitle.indexOf(trimmedQuery, index + 1)
                }

                // Content stanzas matches
                item.splitStanzas.forEachIndexed { sIdx, stanza ->
                    val normalizedStanza = stanza.normalize()
                    var sIndex = normalizedStanza.indexOf(trimmedQuery)
                    while (sIndex != -1 && trimmedQuery.isNotEmpty()) {
                        matches.add(
                            MatchOccurrence(
                                hymnId = hymnId,
                                isTitle = false,
                                stanzaIndex = sIdx,
                                charRange = sIndex until (sIndex + trimmedQuery.length)
                            )
                        )
                        sIndex = normalizedStanza.indexOf(trimmedQuery, sIndex + 1)
                    }
                }
            }

            _globalMatches.value = matches
            
            val currentIdx = _currentMatchIndex.value
            if (matches.isEmpty()) {
                _currentMatchIndex.value = -1
                _scrollToItemEvent.value = null
            } else if (currentIdx < 0 || currentIdx >= matches.size) {
                _currentMatchIndex.value = 0
                val occurrence = matches[0]
                val listIndex = finalFiltered.indexOfFirst { it.hymn.id == occurrence.hymnId }
                if (listIndex != -1) {
                    _scrollToItemEvent.value = ScrollEvent(listIndex, occurrence, System.currentTimeMillis())
                }
            } else {
                // Keep same focus
                val occurrence = matches[currentIdx]
                val listIndex = finalFiltered.indexOfFirst { it.hymn.id == occurrence.hymnId }
                if (listIndex != -1) {
                    _scrollToItemEvent.value = ScrollEvent(listIndex, occurrence, System.currentTimeMillis())
                }
            }
        }
    }

    fun selectNextMatch() {
        val matches = _globalMatches.value
        if (matches.isEmpty()) return
        val nextIndex = (_currentMatchIndex.value + 1) % matches.size
        _currentMatchIndex.value = nextIndex

        val occurrence = matches[nextIndex]
        val listIndex = _filteredHymns.value.indexOfFirst { it.hymn.id == occurrence.hymnId }
        if (listIndex != -1) {
            _scrollToItemEvent.value = ScrollEvent(listIndex, occurrence, System.currentTimeMillis())
        }
    }

    fun selectPreviousMatch() {
        val matches = _globalMatches.value
        if (matches.isEmpty()) return
        val prevIndex = (_currentMatchIndex.value - 1 + matches.size) % matches.size
        _currentMatchIndex.value = prevIndex

        val occurrence = matches[prevIndex]
        val listIndex = _filteredHymns.value.indexOfFirst { it.hymn.id == occurrence.hymnId }
        if (listIndex != -1) {
            _scrollToItemEvent.value = ScrollEvent(listIndex, occurrence, System.currentTimeMillis())
        }
    }

    fun increaseFontSize() {
        if (_readerFontSize.value < 36f) {
            _readerFontSize.value += 2f
        }
    }

    fun decreaseFontSize() {
        if (_readerFontSize.value > 12f) {
            _readerFontSize.value -= 2f
        }
    }
}

// Paleta de colores temáticos de corte tradicional y minimalista de alta legibilidad
val GoldenMain = Color(0xFFC5A03A) // Oro litúrgico cálido
val ParchmentLight = Color(0xFFFDFBF7) // Papel cancionero texturizado/cálido
val JetCarbon = Color(0xFF1E242B) // Carbón profundo premium para texto nítido sin fatiga visual
val DarkHeaderGradient = listOf(Color(0xFF2C394B), Color(0xFF1B2430)) // Azul noche de templo

@Composable
fun HymnApp(viewModel: MainViewModel = viewModel()) {
    HymnFeedScreen(viewModel = viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HymnFeedScreen(viewModel: MainViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val readerFontSize by viewModel.readerFontSize.collectAsState()
    val hymns by viewModel.filteredHymns.collectAsState()
    val globalMatches by viewModel.globalMatches.collectAsState()
    val currentMatchIndex by viewModel.currentMatchIndex.collectAsState()
    val scrollToItemEvent by viewModel.scrollToItemEvent.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val favoritesSet by viewModel.favoriteHymnIds.collectAsState()
    
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backgroundColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFFFFFFF)
    val headerBackground = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFFFFFF)
    val headerBorderColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE2E8F0)
    val textPrimaryColor = if (isDarkMode) Color.White else JetCarbon
    val textSecondaryColor = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray
    val searchBarBackground = if (isDarkMode) Color(0xFF2A2A2A) else Color(0xFFEEEEEE)
    val dividerColor = if (isDarkMode) Color(0xFF424242) else Color(0xFFE0E0E0)

    // Observe and react to the scroll event instantly to auto-scroll to the targeted occurrence item
    LaunchedEffect(scrollToItemEvent) {
        scrollToItemEvent?.let { event ->
            if (event.listIndex in hymns.indices) {
                lazyListState.animateScrollToItem(event.listIndex)
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFDFBF7),
                drawerContentColor = textPrimaryColor,
                modifier = Modifier.width(280.dp).testTag("navigation_drawer")
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                // Minimal Header - Only display the clean title text
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Cuadernillo Digital",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = textPrimaryColor,
                        fontFamily = FontFamily.Serif
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                
                // Navigation Tiles
                NavigationDrawerItem(
                    icon = { LibraryBooksIcon(tint = if (currentScreen == ScreenType.ALL) GoldenMain else textSecondaryColor) },
                    label = { Text("Todas las Alabanzas", fontWeight = FontWeight.Bold) },
                    selected = currentScreen == ScreenType.ALL,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = if (isDarkMode) Color(0xFF2E2D2A) else Color(0xFFFBF8EE),
                        selectedTextColor = if (isDarkMode) Color.White else JetCarbon,
                        unselectedTextColor = textSecondaryColor,
                        selectedIconColor = GoldenMain,
                        unselectedIconColor = textSecondaryColor
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.updateSearchQuery("")
                        viewModel.setScreen(ScreenType.ALL)
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_menu_all")
                )

                NavigationDrawerItem(
                    icon = { 
                        Icon(
                            imageVector = Icons.Default.Star, 
                            contentDescription = "Mis Favoritos",
                            tint = if (currentScreen == ScreenType.FAVORITES) GoldenMain else textSecondaryColor,
                            modifier = Modifier.size(24.dp)
                        ) 
                    },
                    label = { 
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Mis Favoritos", fontWeight = FontWeight.Bold)
                            // Badge with count of favorites
                            Surface(
                                shape = CircleShape,
                                color = if (currentScreen == ScreenType.FAVORITES) GoldenMain else dividerColor,
                                contentColor = if (currentScreen == ScreenType.FAVORITES) Color.White else textSecondaryColor,
                                modifier = Modifier.size(24.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = favoritesSet.size.toString(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    },
                    selected = currentScreen == ScreenType.FAVORITES,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = if (isDarkMode) Color(0xFF2E2D2A) else Color(0xFFFBF8EE),
                        selectedTextColor = if (isDarkMode) Color.White else JetCarbon,
                        unselectedTextColor = textSecondaryColor,
                        selectedIconColor = GoldenMain,
                        unselectedIconColor = textSecondaryColor
                    ),
                    onClick = {
                        scope.launch { drawerState.close() }
                        viewModel.setScreen(ScreenType.FAVORITES)
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_menu_favorites")
                )

                // Footer centered at the absolute bottom of the drawer sheet
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Versión 1.0",
                        fontSize = 11.sp,
                        color = textSecondaryColor.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            // En cabeza superior fijo estilo templo clásico / moderno minimalista
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBackground)
                    .border(width = 1.dp, color = headerBorderColor, shape = RoundedCornerShape(0.dp))
                    .padding(top = 18.dp, bottom = 14.dp, start = 12.dp, end = 12.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier
                                    .size(40.dp)
                                    .testTag("menu_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu Lateral",
                                    tint = textPrimaryColor,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            
                            Text(
                                text = if (currentScreen == ScreenType.FAVORITES) "Mis Favoritos" else "Cuadernillo Digital",
                                color = textPrimaryColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Serif,
                                letterSpacing = 0.5.sp,
                            )
                        }
                        
                        // Controles rápidos de tamaño de texto planos y elegantes junto al Theme Toggle
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            // Light/Dark Theme toggle
                            IconButton(
                                onClick = { viewModel.toggleDarkMode() },
                                modifier = Modifier.size(28.dp).testTag("theme_toggle_button")
                            ) {
                                ThemeToggleIcon(
                                    isDarkMode = isDarkMode,
                                    tint = if (isDarkMode) Color.White else JetCarbon,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = { viewModel.decreaseFontSize() },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Text(
                                    "A-",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textSecondaryColor
                                )
                            }
                            IconButton(
                                onClick = { viewModel.increaseFontSize() },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Text(
                                    "A+",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textPrimaryColor
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    // Barra de búsqueda con controles de navegación "Up" y "Down" integrados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Barra de búsqueda ultra estrecha (altura física máx de 38.dp)
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .background(searchBarBackground, shape = RoundedCornerShape(24.dp))
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Buscar",
                                tint = textSecondaryColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 12.sp,
                                    color = textPrimaryColor,
                                    fontFamily = FontFamily.SansSerif
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("search_text_field"),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (searchQuery.isEmpty()) {
                                            Text(
                                                text = if (currentScreen == ScreenType.FAVORITES) "Buscar en favoritos..." else "Buscar palabra o número...",
                                                fontSize = 11.sp,
                                                color = textSecondaryColor
                                            )
                                        }
                                        innerTextField()
                                    }
                                }
                            )

                            // Real-time counter of matches ("1 / 240")
                            if (searchQuery.isNotEmpty()) {
                                Text(
                                    text = if (globalMatches.isNotEmpty()) "${currentMatchIndex + 1} / ${globalMatches.size}" else "0 / 0",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = textSecondaryColor,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }

                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { viewModel.updateSearchQuery("") },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Limpiar",
                                        tint = textSecondaryColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }

                        // Persistent Up & Down arrow triggers
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            IconButton(
                                onClick = { viewModel.selectPreviousMatch() },
                                enabled = globalMatches.isNotEmpty(),
                                modifier = Modifier.size(32.dp).testTag("prev_match_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Anterior",
                                    tint = if (globalMatches.isNotEmpty()) textPrimaryColor else textSecondaryColor.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.selectNextMatch() },
                                enabled = globalMatches.isNotEmpty(),
                                modifier = Modifier.size(32.dp).testTag("next_match_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Siguiente",
                                    tint = if (globalMatches.isNotEmpty()) textPrimaryColor else textSecondaryColor.copy(alpha = 0.5f),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Lista de alabanzas continua
            if (hymns.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        val emptyStateIcon = if (currentScreen == ScreenType.FAVORITES) Icons.Default.Star else Icons.Default.Search
                        val emptyStateTitle = if (currentScreen == ScreenType.FAVORITES) "Sin favoritos guardados" else "Sin Coincidencias"
                        val emptyStateMsg = if (currentScreen == ScreenType.FAVORITES) {
                            "Aún no tienes himnos agregados a tus favoritos. ¡Toca la estrella en cualquier alabanza para agregarla aquí!"
                        } else {
                            "Prueba buscar por otra palabra, número exacto o estrofa del himno."
                        }
                        
                        Icon(
                            imageVector = emptyStateIcon,
                            contentDescription = "Sin resultados",
                            tint = textSecondaryColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = emptyStateTitle,
                            fontWeight = FontWeight.Bold,
                            color = textPrimaryColor,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = emptyStateMsg,
                            color = textSecondaryColor,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .testTag("hymns_list"),
                    contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp, start = 12.dp, end = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    itemsIndexed(hymns, key = { _, item -> item.hymn.id }) { index, item ->
                        FeedHymnCard(
                            searchableHymn = item,
                            fontSize = readerFontSize,
                            searchQuery = searchQuery,
                            globalMatches = globalMatches,
                            currentMatchIndex = currentMatchIndex,
                            isDarkMode = isDarkMode,
                            onToggleFavorite = {
                                viewModel.toggleFavorite(item.hymn.id)
                            },
                            onDownload = {
                                PdfGenerator.downloadHymnPdf(context, item.hymn)
                            }
                        )
                        if (index < hymns.lastIndex) {
                            Spacer(modifier = Modifier.height(18.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(dividerColor)
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Visual Highlighter helper executing high-level diacritic-agnostic index matching
 * with distinct styling for focused matches.
 */
@Composable
fun buildHighlightedText(
    originalText: String,
    query: String,
    hymnId: Int,
    isTitle: Boolean,
    stanzaIndex: Int,
    globalMatches: List<MatchOccurrence>,
    currentMatchIndex: Int
): AnnotatedString {
    return remember(originalText, query, globalMatches, currentMatchIndex) {
        val builder = AnnotatedString.Builder(originalText)
        val normalizedQuery = query.trim().normalize()
        if (normalizedQuery.isEmpty()) {
            return@remember builder.toAnnotatedString()
        }

        val normalizedText = originalText.normalize()
        var index = normalizedText.indexOf(normalizedQuery)
        while (index != -1 && normalizedQuery.isNotEmpty()) {
            val end = index + normalizedQuery.length
            val range = index until end

            // Determine if this exact range matches the currently active focused occurrence
            val occurrence = MatchOccurrence(
                hymnId = hymnId,
                isTitle = isTitle,
                stanzaIndex = stanzaIndex,
                charRange = range
            )

            val isActive = if (currentMatchIndex in globalMatches.indices) {
                globalMatches[currentMatchIndex] == occurrence
            } else {
                false
            }

            // Visual styling: active focus gets stylish orange, other occurrences get bright yellow
            val highlightBg = if (isActive) Color(0xFFFF9800) else Color(0xFFFFEB3B)
            val highlightFg = if (isActive) Color.White else Color.Black

            builder.addStyle(
                style = SpanStyle(
                    background = highlightBg,
                    color = highlightFg,
                    fontWeight = FontWeight.Bold
                ),
                start = index,
                end = end
            )

            index = normalizedText.indexOf(normalizedQuery, index + 1)
        }
        builder.toAnnotatedString()
    }
}

/**
 * Tarjeta individual que muestra la alabanza de manera completa de forma continua y compacta.
 * No requiere pantallas secundarias y promueve una lectura ininterrumpida y una descarga rápida.
 */
@Composable
fun FeedHymnCard(
    searchableHymn: SearchableHymn,
    fontSize: Float,
    searchQuery: String,
    globalMatches: List<MatchOccurrence>,
    currentMatchIndex: Int,
    isDarkMode: Boolean,
    onToggleFavorite: () -> Unit,
    onDownload: () -> Unit
) {
    val hymn = searchableHymn.hymn
    val stanzas = searchableHymn.splitStanzas

    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val headerBg = if (isDarkMode) Color(0xFF2A2A2A) else Color(0xFFF8F9FA)
    val textPrimary = if (isDarkMode) Color.White else JetCarbon

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hymn_item_${hymn.id}"),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp)
        ) {
            // Encabezado interno de la alabanza: Título a la izquierda límpio (con ID prepandado) y botón plano de descarga
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBg)
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Prepend ID to the title string to make it fully searchable and matching
                val displayTitle = "${hymn.id} - ${hymn.title.uppercase()}"
                val annotatedTitle = buildHighlightedText(
                    originalText = displayTitle,
                    query = searchQuery,
                    hymnId = hymn.id,
                    isTitle = true,
                    stanzaIndex = -1,
                    globalMatches = globalMatches,
                    currentMatchIndex = currentMatchIndex
                )

                Text(
                    text = annotatedTitle,
                    color = textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("favorite_button_${hymn.id}")
                ) {
                    if (hymn.isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorito",
                            tint = Color(0xFFFFC107),
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        StarBorderIcon(
                            tint = GoldenMain,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onDownload,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("download_button_${hymn.id}")
                ) {
                    DownloadIcon(tint = GoldenMain, modifier = Modifier.size(20.dp))
                }

                if (hymn.link.isNotEmpty()) {
                    val context = LocalContext.current
                    IconButton(
                        onClick = {
                            try {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse(hymn.link)
                                )
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                android.widget.Toast.makeText(
                                    context,
                                    "No se pudo abrir el enlace de YouTube",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("youtube_button_${hymn.id}")
                    ) {
                        VideoPlayIcon(tint = Color(0xFFFF0000), modifier = Modifier.size(22.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Cuerpo del canto centrado de forma elegante y seleccionable
            SelectionContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    stanzas.forEachIndexed { index, stanza ->
                        val annotatedStanza = buildHighlightedText(
                            originalText = stanza,
                            query = searchQuery,
                            hymnId = hymn.id,
                            isTitle = false,
                            stanzaIndex = index,
                            globalMatches = globalMatches,
                            currentMatchIndex = currentMatchIndex
                        )

                        Text(
                            text = annotatedStanza,
                            color = textPrimary,
                            fontSize = fontSize.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = (fontSize * 1.55f).sp,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        if (index < stanzas.lastIndex) {
                            // Separador sutil decorativo entre estrofas
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .width(24.dp)
                                    .height(1.dp)
                                    .background(if (isDarkMode) Color.Gray.copy(alpha = 0.3f) else Color.LightGray.copy(alpha = 0.3f))
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Icono de descarga de precisión gráfica implementado en Compose nativo.
 * Evita dependencias en paquetes de iconos pesados y asegura carga rápida.
 */
@Composable
fun DownloadIcon(modifier: Modifier = Modifier, tint: Color = Color.White) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_download",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(tint)
            ) {
                moveTo(19f, 9f)
                lineTo(15f, 9f)
                lineTo(15f, 3f)
                lineTo(9f, 3f)
                lineTo(9f, 9f)
                lineTo(5f, 9f)
                lineTo(12f, 16f)
                close()
                moveTo(5f, 18f)
                lineTo(19f, 18f)
                lineTo(19f, 20f)
                lineTo(5f, 20f)
                close()
            }
        }.build(),
        contentDescription = "Descargar PDF",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Custom canvas-drawn icon representation that renders a crescent moon in dark mode
 * and a glowing sun in light mode. This completely avoids missing resource errors and looks stunning.
 */
@Composable
fun ThemeToggleIcon(isDarkMode: Boolean, modifier: Modifier = Modifier, tint: Color = Color.White) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)
        if (isDarkMode) {
            // Draw a gorgeous crescent moon
            drawCircle(
                color = tint,
                radius = radius * 0.85f,
                center = center
            )
            // Mask overlapping circle to form the crescent moon shape
            drawCircle(
                color = Color(0xFF1E1E1E), // Match dark mode header background
                radius = radius * 0.78f,
                center = androidx.compose.ui.geometry.Offset(center.x - radius * 0.42f, center.y - radius * 0.15f)
            )
        } else {
            // Draw core sun
            drawCircle(
                color = tint,
                radius = radius * 0.45f,
                center = center
            )
            // Draw sun rays
            val rayCount = 8
            for (i in 0 until rayCount) {
                val angle = (i * (2 * Math.PI) / rayCount).toFloat()
                val startX = center.x + (radius * 0.62f) * kotlin.math.cos(angle)
                val startY = center.y + (radius * 0.62f) * kotlin.math.sin(angle)
                val endX = center.x + (radius * 0.85f) * kotlin.math.cos(angle)
                val endY = center.y + (radius * 0.85f) * kotlin.math.sin(angle)
                
                drawLine(
                    color = tint,
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX, endY),
                    strokeWidth = 2.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }
    }
}

/**
 * Custom vector icon representing multiple library books.
 */
@Composable
fun LibraryBooksIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_library_books",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(4f, 6f)
                horizontalLineTo(2f)
                verticalLineTo(20f)
                curveTo(2f, 21.1f, 2.9f, 22f, 4f, 22f)
                horizontalLineTo(18f)
                verticalLineTo(20f)
                horizontalLineTo(4f)
                verticalLineTo(6f)
                close()
                moveTo(20f, 2f)
                horizontalLineTo(8f)
                curveTo(6.9f, 2f, 6f, 2.9f, 6f, 4f)
                verticalLineTo(16f)
                curveTo(6f, 17.1f, 6.9f, 18f, 8f, 18f)
                horizontalLineTo(20f)
                curveTo(21.1f, 18f, 22f, 17.1f, 22f, 16f)
                verticalLineTo(4f)
                curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
                close()
                moveTo(20f, 16f)
                horizontalLineTo(8f)
                verticalLineTo(4f)
                horizontalLineTo(20f)
                verticalLineTo(16f)
                close()
                moveTo(10f, 6f)
                horizontalLineTo(18f)
                verticalLineTo(8f)
                horizontalLineTo(10f)
                close()
                moveTo(10f, 10f)
                horizontalLineTo(18f)
                verticalLineTo(12f)
                horizontalLineTo(10f)
                close()
                moveTo(10f, 14f)
                horizontalLineTo(15f)
                verticalLineTo(16f)
                horizontalLineTo(10f)
                close()
            }
        }.build(),
        contentDescription = "Todas las Alabanzas",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Custom vector icon representing an outlined star border.
 */
@Composable
fun StarBorderIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_star_border",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(22f, 9.24f)
                lineTo(14.81f, 8.62f)
                lineTo(12f, 2f)
                lineTo(9.19f, 8.63f)
                lineTo(2f, 9.24f)
                lineTo(7.46f, 13.97f)
                lineTo(5.82f, 21f)
                lineTo(12f, 17.27f)
                lineTo(18.18f, 21f)
                lineTo(16.54f, 13.97f)
                lineTo(22f, 9.24f)
                close()
                moveTo(12f, 15.4f)
                lineTo(8.24f, 17.67f)
                lineTo(9.24f, 13.38f)
                lineTo(5.92f, 10.51f)
                lineTo(10.3f, 10.13f)
                lineTo(12f, 6.1f)
                lineTo(13.7f, 10.13f)
                lineTo(18.08f, 10.51f)
                lineTo(14.76f, 13.38f)
                lineTo(15.76f, 17.67f)
                lineTo(12f, 15.4f)
                close()
            }
        }.build(),
        contentDescription = "Favorito Desmarcado",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Custom vector icon representing a video play media button (smart display / play circle filled).
 */
@Composable
fun VideoPlayIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_video_play",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
                close()
                moveTo(10f, 16.5f)
                verticalLineTo(7.5f)
                lineTo(16f, 12f)
                lineTo(10f, 16.5f)
                close()
            }
        }.build(),
        contentDescription = "Ver Video",
        modifier = modifier,
        tint = tint
    )
}

