package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.search.MatchOccurrence
import com.example.ui.theme.JetCarbon

/**
 * Cabecera superior que integra el buscador instantáneo, navegación de ocurrencias
 * y controles de lectura rápidos.
 */
@Composable
fun SearchHeader(
    searchQuery: String,
    currentScreen: ScreenType,
    isDarkMode: Boolean,
    globalMatches: List<MatchOccurrence>,
    currentMatchIndex: Int,
    onSearchQueryChange: (String) -> Unit,
    onOpenDrawer: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onIncreaseFontSize: () -> Unit,
    onDecreaseFontSize: () -> Unit,
    onPreviousMatch: () -> Unit,
    onNextMatch: () -> Unit,
    onClearSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val headerBackground = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val headerBorderColor = if (isDarkMode) Color(0xFF2D2D2D) else Color(0xFFE2E8F0)
    val textPrimaryColor = if (isDarkMode) Color.White else JetCarbon
    val textSecondaryColor = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray
    val searchBarBackground = if (isDarkMode) Color(0xFF2A2A2A) else Color(0xFFEEEEEE)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(headerBackground)
            .border(width = 1.dp, color = headerBorderColor, shape = RoundedCornerShape(0.dp))
            .padding(top = 18.dp, bottom = 14.dp, start = 12.dp, end = 12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Fila superior: Menú, Logo/Título, Tema y Controles de fuente
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
                        onClick = onOpenDrawer,
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

                    if (currentScreen != ScreenType.FAVORITES) {
                        AppLogo(
                            isDarkMode = isDarkMode,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Text(
                        text = if (currentScreen == ScreenType.FAVORITES) "Mis Favoritos" else "Cuadernillo Digital",
                        color = textPrimaryColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = onToggleDarkMode,
                        modifier = Modifier
                            .size(28.dp)
                            .testTag("theme_toggle_button")
                    ) {
                        ThemeToggleIcon(
                            isDarkMode = isDarkMode,
                            tint = if (isDarkMode) Color.White else JetCarbon,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(
                        onClick = onDecreaseFontSize,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text(
                            text = "A-",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondaryColor
                        )
                    }

                    IconButton(
                        onClick = onIncreaseFontSize,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Text(
                            text = "A+",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = textPrimaryColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Fila de búsqueda con contador de coincidencias y flechas arriba/abajo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        onValueChange = onSearchQueryChange,
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

                    // Contador de coincidencias en vivo
                    if (searchQuery.isNotEmpty()) {
                        Text(
                            text = if (globalMatches.isNotEmpty()) "${currentMatchIndex + 1} / ${globalMatches.size}" else "0 / 0",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = textSecondaryColor,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        IconButton(
                            onClick = onClearSearch,
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

                // Flechas de navegación de ocurrencias
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    IconButton(
                        onClick = onPreviousMatch,
                        enabled = globalMatches.isNotEmpty(),
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("prev_match_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Anterior",
                            tint = if (globalMatches.isNotEmpty()) textPrimaryColor else textSecondaryColor.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = onNextMatch,
                        enabled = globalMatches.isNotEmpty(),
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("next_match_button")
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
}
