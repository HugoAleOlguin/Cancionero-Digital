package com.example.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.HymnEntity
import com.example.ui.components.AppDrawer
import com.example.ui.components.FastScrollbar
import com.example.ui.components.ModernActionSheet
import com.example.ui.components.ScreenType
import com.example.ui.components.SearchHeader
import com.example.ui.theme.GoldenMain
import com.example.ui.theme.JetCarbon
import com.example.ui.theme.ThemeMode
import com.example.ui.viewmodel.MainViewModel
import com.example.util.PdfGenerator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Pantalla principal del Cancionero Digital: Feed continuo de lectura con búsqueda de 0ms
 * y sincronización silenciosa sobre Wi-Fi.
 */
@OptIn(FlowPreview::class)
@Composable
fun HymnFeedScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val readerFontSize by viewModel.readerFontSize.collectAsState()
    val fontFamilyType by viewModel.fontFamilyType.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val selectedAuthor by viewModel.selectedAuthor.collectAsState()
    val availableAuthors by viewModel.availableAuthors.collectAsState()
    val favoritesCount by viewModel.favoritesCount.collectAsState()
    val hymns by viewModel.filteredHymns.collectAsState()
    val globalMatches by viewModel.globalMatches.collectAsState()
    val currentMatchIndex by viewModel.currentMatchIndex.collectAsState()
    val scrollToItemEvent by viewModel.scrollToItemEvent.collectAsState()
    val catalogVersion by viewModel.catalogVersion.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    val themeMode by viewModel.themeMode.collectAsState()
    val dlcStatus by viewModel.dlcStatus.collectAsState()

    var showDownloadSuccessDialog by remember { mutableStateOf<Pair<String, Uri>?>(null) }
    var activeHymnForActionSheet by remember { mutableStateOf<HymnEntity?>(null) }

    val fontFamily = remember(fontFamilyType) {
        when (fontFamilyType) {
            "SansSerif" -> FontFamily.SansSerif
            "Monospace" -> FontFamily.Monospace
            else -> FontFamily.Serif
        }
    }

    val backgroundColor = if (isDarkMode) Color(0xFF121212) else Color(0xFFFFFFFF)
    val textPrimaryColor = if (isDarkMode) Color.White else JetCarbon
    val textSecondaryColor = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray

    val initialIndex = remember { viewModel.getLastViewedHymnIndex() }
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)

    // Desplazamiento reactivo e instantáneo a la ocurrencia encontrada
    LaunchedEffect(scrollToItemEvent) {
        scrollToItemEvent?.let { event ->
            if (event.listIndex in hymns.indices) {
                lazyListState.animateScrollToItem(event.listIndex)
            }
        }
    }

    // Persistencia debounced de la última alabanza leída
    LaunchedEffect(lazyListState, hymns) {
        snapshotFlow { lazyListState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .debounce(500)
            .collect { index ->
                if (index in hymns.indices) {
                    val hymnId = hymns[index].hymn.id
                    viewModel.saveLastViewedHymnId(hymnId)
                }
            }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                isDarkMode = isDarkMode,
                currentScreen = currentScreen,
                selectedAuthor = selectedAuthor,
                availableAuthors = availableAuthors,
                favoritesCount = favoritesCount,
                fontFamilyType = fontFamilyType,
                catalogVersion = catalogVersion,
                isSyncing = isSyncing,
                themeMode = themeMode,
                dlcStatus = dlcStatus,
                onSelectAll = {
                    scope.launch { drawerState.close() }
                    viewModel.updateSearchQuery("")
                    viewModel.setScreen(ScreenType.ALL)
                },
                onSelectFavorites = {
                    scope.launch { drawerState.close() }
                    viewModel.updateSearchQuery("")
                    viewModel.setScreen(ScreenType.FAVORITES)
                },
                onSelectAuthor = { author ->
                    scope.launch { drawerState.close() }
                    viewModel.updateSearchQuery("")
                    viewModel.setSelectedAuthor(author)
                    viewModel.setScreen(ScreenType.ALL)
                },
                onClearAuthor = {
                    viewModel.setSelectedAuthor(null)
                },
                onChangeFontFamily = { type ->
                    viewModel.setFontFamilyType(type)
                },
                onSelectThemeMode = { mode ->
                    viewModel.setThemeMode(mode)
                    scope.launch { drawerState.close() }
                },
                onDownloadModernTheme = {
                    viewModel.downloadModernTheme()
                },
                onUninstallModernTheme = {
                    viewModel.uninstallModernTheme()
                },
                onCheckAppUpdate = {
                    Toast.makeText(context, "Buscando actualizaciones de la app...", Toast.LENGTH_SHORT).show()
                },
                onSyncCatalog = {
                    viewModel.syncCatalogManually()
                    Toast.makeText(context, "Comprobando catálogo en GitHub...", Toast.LENGTH_SHORT).show()
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            SearchHeader(
                searchQuery = searchQuery,
                currentScreen = currentScreen,
                isDarkMode = isDarkMode,
                globalMatches = globalMatches,
                currentMatchIndex = currentMatchIndex,
                onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                onOpenDrawer = { scope.launch { drawerState.open() } },
                onToggleDarkMode = { viewModel.toggleDarkMode() },
                onIncreaseFontSize = { viewModel.increaseFontSize() },
                onDecreaseFontSize = { viewModel.decreaseFontSize() },
                onPreviousMatch = { viewModel.selectPreviousMatch() },
                onNextMatch = { viewModel.selectNextMatch() },
                onClearSearch = {
                    val visibleIndex = lazyListState.firstVisibleItemIndex
                    val visibleHymnId = if (visibleIndex in hymns.indices) hymns[visibleIndex].hymn.id else null
                    viewModel.updateSearchQuery("")
                    if (visibleHymnId != null) {
                        scope.launch {
                            val targetIndex = viewModel.filteredHymns.value.indexOfFirst { it.hymn.id == visibleHymnId }
                            if (targetIndex != -1) {
                                lazyListState.scrollToItem(targetIndex)
                            }
                        }
                    }
                }
            )

            // Contenedor del Feed de lectura y FastScrollbar
            Box(modifier = Modifier.fillMaxSize()) {
                if (hymns.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Text(
                                text = if (currentScreen == ScreenType.FAVORITES) "No tienes cantos favoritos guardados" else "No se encontraron alabanzas",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = textPrimaryColor,
                                textAlign = TextAlign.Center
                            )
                            if (searchQuery.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Prueba con otra frase o número",
                                    fontSize = 13.sp,
                                    color = textSecondaryColor,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 12.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(hymns, key = { it.hymn.id }) { item ->
                            if (themeMode == ThemeMode.MODERN) {
                                val coverPath = remember(item.hymn.id) {
                                    viewModel.getCoverPathForHymn(item.hymn.id, item.hymn.title, item.hymn.author)
                                }
                                ModernHymnCard(
                                    searchableHymn = item,
                                    coverPath = coverPath,
                                    fontSize = readerFontSize,
                                    fontFamily = fontFamily,
                                    searchQuery = searchQuery,
                                    globalMatches = globalMatches,
                                    currentMatchIndex = currentMatchIndex,
                                    isDarkMode = isDarkMode,
                                    onToggleFavorite = { viewModel.toggleFavorite(item.hymn.id) },
                                    onOpenShareSheet = { activeHymnForActionSheet = item.hymn },
                                    onOpenYoutube = {
                                        try {
                                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(item.hymn.link))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "No se pudo abrir el enlace de YouTube", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            } else {
                                FeedHymnCard(
                                    searchableHymn = item,
                                    fontSize = readerFontSize,
                                    fontFamily = fontFamily,
                                    searchQuery = searchQuery,
                                    globalMatches = globalMatches,
                                    currentMatchIndex = currentMatchIndex,
                                    isDarkMode = isDarkMode,
                                    onToggleFavorite = { viewModel.toggleFavorite(item.hymn.id) },
                                    onOpenShareSheet = { activeHymnForActionSheet = item.hymn },
                                    onOpenYoutube = {
                                        try {
                                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(item.hymn.link))
                                            context.startActivity(intent)
                                        } catch (e: Exception) {
                                            Toast.makeText(context, "No se pudo abrir el enlace de YouTube", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // FastScrollbar a la derecha
                    FastScrollbar(
                        lazyListState = lazyListState,
                        totalItems = hymns.size,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
    }

    // Diálogo de descarga PDF exitosa
    showDownloadSuccessDialog?.let { (title, uri) ->
        AlertDialog(
            onDismissRequest = { showDownloadSuccessDialog = null },
            title = {
                Text(
                    text = "PDF Generado con Éxito",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                Text(
                    text = "La alabanza \"$title\" se guardó en tu carpeta de Descargas.",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDownloadSuccessDialog = null
                        try {
                            val openIntent = android.content.Intent(android.content.Intent.ACTION_VIEW).apply {
                                setDataAndType(uri, "application/pdf")
                                addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            context.startActivity(openIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "No se encontró aplicación para abrir PDFs", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text("Abrir PDF", color = GoldenMain, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDownloadSuccessDialog = null }) {
                    Text("Aceptar", color = textSecondaryColor)
                }
            },
            containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color.White,
            titleContentColor = textPrimaryColor,
            textContentColor = textPrimaryColor
        )
    }

    // Bottom Action Sheet modal tipo iOS para el Tema Moderno
    activeHymnForActionSheet?.let { hymn ->
        val coverPath = remember(hymn.id) {
            viewModel.getCoverPathForHymn(hymn.id, hymn.title, hymn.author)
        }
        ModernActionSheet(
            hymn = hymn,
            coverPath = coverPath,
            isDarkMode = isDarkMode,
            onDismiss = { activeHymnForActionSheet = null },
            onDownloadPdf = {
                val legacyHymn = com.example.data.Hymn(
                    id = hymn.id,
                    title = hymn.title,
                    content = hymn.content,
                    isFavorite = hymn.isFavorite,
                    link = hymn.link,
                    author = hymn.author
                )
                val uri = PdfGenerator.downloadHymnPdf(context, legacyHymn)
                if (uri != null) {
                    showDownloadSuccessDialog = Pair(hymn.title, uri)
                } else {
                    Toast.makeText(context, "Error al generar PDF", Toast.LENGTH_SHORT).show()
                }
            },
            onSharePdf = {
                try {
                    val legacyHymn = com.example.data.Hymn(
                        id = hymn.id,
                        title = hymn.title,
                        content = hymn.content,
                        isFavorite = hymn.isFavorite,
                        link = hymn.link,
                        author = hymn.author
                    )
                    val uri = PdfGenerator.downloadHymnPdf(context, legacyHymn)
                    if (uri != null) {
                        val shareIntent = android.content.Intent().apply {
                            action = android.content.Intent.ACTION_SEND
                            putExtra(android.content.Intent.EXTRA_STREAM, uri)
                            type = "application/pdf"
                            addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(android.content.Intent.createChooser(shareIntent, "Compartir PDF"))
                    } else {
                        Toast.makeText(context, "No se pudo generar el PDF para compartir", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al compartir PDF: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            onShareText = {
                try {
                    val sendIntent = android.content.Intent().apply {
                        action = android.content.Intent.ACTION_SEND
                        val authorText = if (hymn.author.isNotEmpty()) "\nAutor: ${hymn.author}" else ""
                        putExtra(android.content.Intent.EXTRA_TEXT, "${hymn.id} - ${hymn.title.uppercase()}$authorText\n\n${hymn.content}")
                        type = "text/plain"
                    }
                    context.startActivity(android.content.Intent.createChooser(sendIntent, "Compartir Alabanza"))
                } catch (e: Exception) {
                    Toast.makeText(context, "No se pudo compartir la alabanza", Toast.LENGTH_SHORT).show()
                }
            },
            onCopyClipboard = {
                try {
                    val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                    val authorText = if (hymn.author.isNotEmpty()) "\nAutor: ${hymn.author}" else ""
                    val clip = android.content.ClipData.newPlainText(
                        "Alabanza ${hymn.id}",
                        "${hymn.id} - ${hymn.title.uppercase()}$authorText\n\n${hymn.content}"
                    )
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(context, "¡Letra copiada al portapapeles!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al copiar texto", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}
