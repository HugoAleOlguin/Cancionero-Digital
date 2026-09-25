package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.dlc.DlcStatus
import com.example.ui.theme.GoldenMain
import com.example.ui.theme.JetCarbon
import com.example.ui.theme.ThemeMode

enum class ScreenType {
    ALL, FAVORITES
}

/**
 * Menú lateral de navegación Drawer desacoplado y estilizado con la estética litúrgica canónica.
 */
@Composable
fun AppDrawer(
    isDarkMode: Boolean,
    currentScreen: ScreenType,
    selectedAuthor: String?,
    availableAuthors: List<String>,
    favoritesCount: Int,
    fontFamilyType: String,
    catalogVersion: Int,
    isSyncing: Boolean,
    themeMode: ThemeMode = ThemeMode.CLASSIC,
    dlcStatus: DlcStatus = DlcStatus.NotDownloaded,
    onSelectAll: () -> Unit,
    onSelectFavorites: () -> Unit,
    onSelectAuthor: (String) -> Unit,
    onClearAuthor: () -> Unit,
    onChangeFontFamily: (String) -> Unit,
    onSelectThemeMode: (ThemeMode) -> Unit = {},
    onDownloadModernTheme: () -> Unit = {},
    onUninstallModernTheme: () -> Unit = {},
    onCheckAppUpdate: () -> Unit,
    onSyncCatalog: () -> Unit
) {
    val textPrimaryColor = if (isDarkMode) Color.White else JetCarbon
    val textSecondaryColor = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray
    val dividerColor = if (isDarkMode) Color(0xFF424242) else Color(0xFFE0E0E0)
    var authorsExpanded by remember { mutableStateOf(false) }

    ModalDrawerSheet(
        drawerContainerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFFFDFBF7),
        drawerContentColor = textPrimaryColor,
        modifier = Modifier.width(280.dp).testTag("navigation_drawer")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // --- 1. CABECERA FIJA ---
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AppLogo(
                    isDarkMode = isDarkMode,
                    modifier = Modifier.size(44.dp)
                )
                Text(
                    text = "Cuadernillo\nDigital",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimaryColor,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))

            // --- 2. CONTENIDO SCROLLEABLE CENTRAL ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Navegación: Todas las alabanzas
                NavigationDrawerItem(
                    icon = { LibraryBooksIcon(tint = if (currentScreen == ScreenType.ALL && selectedAuthor == null) GoldenMain else textSecondaryColor) },
                    label = { Text("Todas las Alabanzas", fontWeight = FontWeight.Bold) },
                    selected = currentScreen == ScreenType.ALL && selectedAuthor == null,
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = if (isDarkMode) Color(0xFF2E2D2A) else Color(0xFFFBF8EE),
                        selectedTextColor = if (isDarkMode) Color.White else JetCarbon,
                        unselectedTextColor = textSecondaryColor,
                        selectedIconColor = GoldenMain,
                        unselectedIconColor = textSecondaryColor
                    ),
                    onClick = {
                        onClearAuthor()
                        onSelectAll()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_menu_all")
                )

                // Navegación: Favoritos con contador
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
                                        text = favoritesCount.toString(),
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
                        onClearAuthor()
                        onSelectFavorites()
                    },
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp).testTag("drawer_menu_favorites")
                )

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))

                // SECCIÓN: FILTRADO POR AUTOR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { authorsExpanded = !authorsExpanded }
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "CANTOS POR AUTOR",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = textSecondaryColor.copy(alpha = 0.8f),
                        letterSpacing = 0.8.sp
                    )
                    Icon(
                        imageVector = if (authorsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (authorsExpanded) "Colapsar autores" else "Expandir autores",
                        tint = textSecondaryColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }

                if (authorsExpanded) {
                    availableAuthors.forEach { author ->
                        val isSelected = selectedAuthor == author
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp)
                                .background(
                                    color = if (isSelected) {
                                        if (isDarkMode) Color(0xFF2E2D2A) else Color(0xFFFBF8EE)
                                    } else {
                                        Color.Transparent
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSelectAuthor(author)
                                }
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = if (isSelected) GoldenMain else textSecondaryColor.copy(alpha = 0.6f),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = author,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) {
                                    if (isDarkMode) Color.White else JetCarbon
                                } else {
                                    textPrimaryColor.copy(alpha = 0.9f)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))

                // SECCIÓN: TEMA
                Text(
                    text = "TEMA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = textSecondaryColor.copy(alpha = 0.8f),
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
                )

                val isClassicSelected = (themeMode == ThemeMode.CLASSIC)
                val isModernSelected = (themeMode == ThemeMode.MODERN)
                val isDownloaded = (dlcStatus is DlcStatus.Downloaded)
                val isDownloading = (dlcStatus is DlcStatus.Downloading)

                // Selector en 2 tarjetas lado a lado (Clásico vs Moderno)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Tarjeta: Clásico
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isClassicSelected) GoldenMain.copy(alpha = 0.15f)
                                else if (isDarkMode) Color(0xFF262626)
                                else Color(0xFFF2EFE9)
                            )
                            .border(
                                width = if (isClassicSelected) 1.5.dp else 1.dp,
                                color = if (isClassicSelected) GoldenMain else dividerColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { onSelectThemeMode(ThemeMode.CLASSIC) }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "📜", fontSize = 22.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Clásico",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = if (isClassicSelected) GoldenMain else textPrimaryColor
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isClassicSelected) "Activo ✓" else "Seleccionar",
                                fontSize = 10.5.sp,
                                fontWeight = if (isClassicSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isClassicSelected) GoldenMain else textSecondaryColor
                            )
                        }
                    }

                    // Tarjeta: Moderno
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isModernSelected) GoldenMain.copy(alpha = 0.15f)
                                else if (isDarkMode) Color(0xFF262626)
                                else Color(0xFFF2EFE9)
                            )
                            .border(
                                width = if (isModernSelected) 1.5.dp else 1.dp,
                                color = if (isModernSelected) GoldenMain else dividerColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable {
                                if (isDownloaded) {
                                    onSelectThemeMode(ThemeMode.MODERN)
                                } else if (!isDownloading) {
                                    onDownloadModernTheme()
                                }
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "✨", fontSize = 22.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Moderno",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                color = if (isModernSelected) GoldenMain else textPrimaryColor
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            when (dlcStatus) {
                                is DlcStatus.NotDownloaded -> {
                                    Text(
                                        text = "Descargar",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldenMain
                                    )
                                }
                                is DlcStatus.Downloading -> {
                                    Text(
                                        text = "${(dlcStatus.progress * 100).toInt()}%",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldenMain
                                    )
                                }
                                is DlcStatus.Downloaded -> {
                                    Text(
                                        text = if (isModernSelected) "Activo ✓" else "Seleccionar",
                                        fontSize = 10.5.sp,
                                        fontWeight = if (isModernSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isModernSelected) GoldenMain else textSecondaryColor
                                    )
                                }
                                is DlcStatus.Error -> {
                                    Text(
                                        text = "Reintentar",
                                        fontSize = 10.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFEF4444)
                                    )
                                }
                            }
                        }
                    }
                }

                // Feedback visual de descarga claro y obvio
                if (isDownloading) {
                    val progress = (dlcStatus as DlcStatus.Downloading).progress
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = GoldenMain,
                            trackColor = if (isDarkMode) Color(0xFF333333) else Color(0xFFE0E0E0)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Descargando Tema Moderno... ${(progress * 100).toInt()}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldenMain,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }

                // Opción para desinstalar el tema descargado
                if (isDownloaded) {
                    Text(
                        text = "Desinstalar Tema Moderno",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFEF4444).copy(alpha = 0.85f),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { onUninstallModernTheme() }
                            .padding(vertical = 4.dp)
                    )
                }

                if (dlcStatus is DlcStatus.Error) {
                    Text(
                        text = "Error al descargar. Toca 'Moderno' para reintentar.",
                        fontSize = 10.5.sp,
                        color = Color(0xFFEF4444),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            // --- 3. AJUSTE DE TIPOGRAFÍA INFERIOR ---
            HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tipografía:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = textPrimaryColor.copy(alpha = 0.8f)
                )

                var fontDropdownExpanded by remember { mutableStateOf(false) }

                Box {
                    Row(
                        modifier = Modifier
                            .background(
                                color = if (isDarkMode) Color(0xFF2E2D2A) else Color(0xFFFBF8EE),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isDarkMode) Color(0xFF424242) else Color(0xFFE2E8F0),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { fontDropdownExpanded = true }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = when (fontFamilyType) {
                                "SansSerif" -> "Sans"
                                "Monospace" -> "Mono"
                                else -> "Serif"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else JetCarbon
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Cambiar fuente",
                            tint = GoldenMain,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = fontDropdownExpanded,
                        onDismissRequest = { fontDropdownExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Serif", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                            onClick = {
                                onChangeFontFamily("Serif")
                                fontDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sans", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold) },
                            onClick = {
                                onChangeFontFamily("SansSerif")
                                fontDropdownExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Mono", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold) },
                            onClick = {
                                onChangeFontFamily("Monospace")
                                fontDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            HorizontalDivider(color = dividerColor, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))

            // --- 4. PIE DE PÁGINA (ESTADO DE CATÁLOGO Y ACTUALIZACIÓN) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Indicador de Catálogo y Sincronización Wi-Fi
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSyncCatalog() }
                        .padding(vertical = 2.dp)
                ) {
                    if (isSyncing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(12.dp),
                            strokeWidth = 1.5.dp,
                            color = GoldenMain
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sincronizando...",
                            fontSize = 11.sp,
                            color = GoldenMain,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Catálogo v$catalogVersion",
                            fontSize = 11.sp,
                            color = textSecondaryColor.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sincronizar",
                            tint = GoldenMain,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                // Versión de la Aplicación
                Text(
                    text = "Cancionero Digital v4.0",
                    fontSize = 10.sp,
                    color = textSecondaryColor.copy(alpha = 0.5f)
                )
            }
        }
    }
}
