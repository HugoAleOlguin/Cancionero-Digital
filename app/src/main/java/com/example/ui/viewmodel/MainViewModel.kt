package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.repository.HymnRepository
import com.example.data.sync.HymnSyncManager
import com.example.data.sync.SyncResult
import com.example.search.FuzzyHymnSearchEngine
import com.example.search.MatchOccurrence
import com.example.search.SearchableHymn
import com.example.ui.components.ScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Evento de scroll para desplazar la lista automáticamente hacia una ocurrencia encontrada.
 */
data class ScrollEvent(
    val listIndex: Int,
    val matchOccurrence: MatchOccurrence,
    val timestamp: Long
)

/**
 * ViewModel principal que orquesta la persistencia local en Room, la sincronización Wi-Fi
 * y el motor de búsqueda difuso en memoria.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    val hymnRepository = HymnRepository(
        hymnDao = database.hymnDao(),
        favoriteHymnDao = database.favoriteHymnDao(),
        context = application
    )
    val syncManager = HymnSyncManager(application, hymnRepository)

    private val sharedPreferences = application.getSharedPreferences("HymnAppState", Context.MODE_PRIVATE)

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    private val _readerFontSize = MutableStateFlow(18f)
    val readerFontSize = _readerFontSize.asStateFlow()

    private val _fontFamilyType = MutableStateFlow("Serif")
    val fontFamilyType = _fontFamilyType.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _currentScreen = MutableStateFlow(ScreenType.ALL)
    val currentScreen = _currentScreen.asStateFlow()

    private val _selectedAuthor = MutableStateFlow<String?>(null)
    val selectedAuthor = _selectedAuthor.asStateFlow()

    private val _availableAuthors = MutableStateFlow<List<String>>(emptyList())
    val availableAuthors = _availableAuthors.asStateFlow()

    private val _favoritesCount = MutableStateFlow(0)
    val favoritesCount = _favoritesCount.asStateFlow()

    private var allSearchableHymns: List<SearchableHymn> = emptyList()

    private val _filteredHymns = MutableStateFlow<List<SearchableHymn>>(emptyList())
    val filteredHymns = _filteredHymns.asStateFlow()

    private val _globalMatches = MutableStateFlow<List<MatchOccurrence>>(emptyList())
    val globalMatches = _globalMatches.asStateFlow()

    private val _currentMatchIndex = MutableStateFlow(-1)
    val currentMatchIndex = _currentMatchIndex.asStateFlow()

    private val _scrollToItemEvent = MutableStateFlow<ScrollEvent?>(null)
    val scrollToItemEvent = _scrollToItemEvent.asStateFlow()

    private val _catalogVersion = MutableStateFlow(syncManager.getLocalVersion())
    val catalogVersion = _catalogVersion.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing = _isSyncing.asStateFlow()

    init {
        // Cargar configuraciones guardadas de lectura
        _isDarkMode.value = sharedPreferences.getBoolean("is_dark_mode", false)
        _readerFontSize.value = sharedPreferences.getFloat("reader_font_size", 18f)
        _fontFamilyType.value = sharedPreferences.getString("font_family_type", "Serif") ?: "Serif"

        viewModelScope.launch {
            // 1. Asegurar pre-población de Room desde assets si es primera ejecución
            hymnRepository.ensureInitialized()

            // 2. Observar favoritos reactivamente
            launch {
                hymnRepository.favoriteHymnIds.collectLatest { favIds ->
                    _favoritesCount.value = favIds.size
                    recalculateFilteredHymns()
                }
            }

            // 3. Observar catálogo de alabanzas reactivamente
            launch {
                hymnRepository.allHymns.collectLatest { entities ->
                    allSearchableHymns = entities.map { SearchableHymn.from(it) }

                    _availableAuthors.value = entities
                        .map { it.author }
                        .filter { it.isNotBlank() }
                        .distinct()
                        .sorted()

                    recalculateFilteredHymns()
                }
            }

            // 4. Disparar sincronización silenciosa sobre Wi-Fi en segundo plano
            launch(Dispatchers.IO) {
                if (syncManager.isConnectedToWifi()) {
                    _isSyncing.value = true
                    val result = syncManager.syncIfWifiAvailable()
                    if (result is SyncResult.Success) {
                        _catalogVersion.value = result.version
                    }
                    _isSyncing.value = false
                }
            }
        }
    }

    fun toggleDarkMode() {
        val newValue = !_isDarkMode.value
        _isDarkMode.value = newValue
        sharedPreferences.edit().putBoolean("is_dark_mode", newValue).apply()
    }

    fun increaseFontSize() {
        if (_readerFontSize.value < 36f) {
            val newSize = _readerFontSize.value + 2f
            _readerFontSize.value = newSize
            sharedPreferences.edit().putFloat("reader_font_size", newSize).apply()
        }
    }

    fun decreaseFontSize() {
        if (_readerFontSize.value > 12f) {
            val newSize = _readerFontSize.value - 2f
            _readerFontSize.value = newSize
            sharedPreferences.edit().putFloat("reader_font_size", newSize).apply()
        }
    }

    fun setFontFamilyType(type: String) {
        _fontFamilyType.value = type
        sharedPreferences.edit().putString("font_family_type", type).apply()
    }

    fun setScreen(screen: ScreenType) {
        _currentScreen.value = screen
        recalculateFilteredHymns()
    }

    fun setSelectedAuthor(author: String?) {
        _selectedAuthor.value = author
        recalculateFilteredHymns()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        recalculateFilteredHymns()
    }

    fun toggleFavorite(hymnId: Int) {
        viewModelScope.launch {
            hymnRepository.toggleFavorite(hymnId)
        }
    }

    fun syncCatalogManually() {
        viewModelScope.launch(Dispatchers.IO) {
            _isSyncing.value = true
            val result = syncManager.syncIfWifiAvailable(force = true)
            if (result is SyncResult.Success) {
                _catalogVersion.value = result.version
            }
            _isSyncing.value = false
        }
    }

    fun saveLastViewedHymnId(hymnId: Int) {
        sharedPreferences.edit().putInt("last_viewed_hymn_id", hymnId).apply()
    }

    fun getLastViewedHymnIndex(): Int {
        val lastId = sharedPreferences.getInt("last_viewed_hymn_id", 1)
        val index = _filteredHymns.value.indexOfFirst { it.hymn.id == lastId }
        return if (index >= 0) index else 0
    }

    private fun recalculateFilteredHymns() {
        val query = _searchQuery.value
        val screen = _currentScreen.value
        val author = _selectedAuthor.value

        // Filtrar por pantalla (Todas vs Favoritos)
        val screenFiltered = if (screen == ScreenType.FAVORITES) {
            allSearchableHymns.filter { it.hymn.isFavorite }
        } else {
            allSearchableHymns
        }

        // Filtrar por autor
        val authorFiltered = if (author != null) {
            screenFiltered.filter { it.hymn.author == author }
        } else {
            screenFiltered
        }

        if (query.isBlank()) {
            _filteredHymns.value = authorFiltered
            _globalMatches.value = emptyList()
            _currentMatchIndex.value = -1
            _scrollToItemEvent.value = null
            return
        }

        // Ejecutar motor de búsqueda difuso y multi-palabra
        val searchResults = FuzzyHymnSearchEngine.search(authorFiltered, query)
        val filteredList = searchResults.map { it.hymn }
        val matches = searchResults.flatMap { it.occurrences }

        _filteredHymns.value = filteredList
        _globalMatches.value = matches

        if (matches.isNotEmpty()) {
            _currentMatchIndex.value = 0
            val occurrence = matches[0]
            val listIndex = filteredList.indexOfFirst { it.hymn.id == occurrence.hymnId }
            if (listIndex != -1) {
                _scrollToItemEvent.value = ScrollEvent(listIndex, occurrence, System.currentTimeMillis())
            }
        } else {
            _currentMatchIndex.value = -1
            _scrollToItemEvent.value = null
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
}
