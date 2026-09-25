package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.search.MatchOccurrence
import com.example.search.SearchableHymn
import com.example.ui.components.StarBorderIcon
import com.example.ui.components.VideoPlayIcon
import com.example.ui.theme.GoldenMain
import com.example.ui.theme.JetCarbon
import com.example.util.rememberCoverImage

/**
 * Forma poligonal simétrica para el visor de 70px (clip-path en V).
 * Corta suavemente las esquinas inferiores con una punta central estilizada.
 */
private val PolygonalVisorShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    moveTo(0f, 0f)
    lineTo(w, 0f)
    lineTo(w, h * 0.82f)
    lineTo(w * 0.5f, h)
    lineTo(0f, h * 0.82f)
    close()
}

/**
 * Tarjeta individual moderna (V3.2):
 * - Visor poligonal de 70px con fotografía HD y gradiente oscuro de legibilidad.
 * - Toolbar limpio con selector iOS de versiones, favorito, compartir y YouTube directo.
 * - Estrofas con espaciado armónico compacto y badges sutiles para CORO y números.
 */
@Composable
fun ModernHymnCard(
    searchableHymn: SearchableHymn,
    coverPath: String?,
    fontSize: Float,
    fontFamily: FontFamily = FontFamily.Serif,
    searchQuery: String,
    globalMatches: List<MatchOccurrence>,
    currentMatchIndex: Int,
    isDarkMode: Boolean,
    onToggleFavorite: () -> Unit,
    onOpenShareSheet: () -> Unit,
    onOpenYoutube: () -> Unit
) {
    val hymn = searchableHymn.hymn
    val versions = remember(hymn) { hymn.getVersions() }
    var selectedVersionIndex by remember(hymn.id) { mutableStateOf(0) }
    val activeContent = if (selectedVersionIndex in versions.indices) versions[selectedVersionIndex] else hymn.content
    val rawStanzas = remember(activeContent) { activeContent.split("\n\n") }

    val cardBg = if (isDarkMode) Color(0xFF141A24) else Color.White
    val cardBorder = if (isDarkMode) Color(0x26FFFFFF) else Color(0x33C5A03A)
    val toolbarBg = if (isDarkMode) Color(0x990D1118) else Color(0xFFF7F5EE)
    val textPrimary = if (isDarkMode) Color(0xFFF8FAFC) else JetCarbon
    val textSecondary = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)

    val coverBitmap = rememberCoverImage(coverPath)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hymn_item_${hymn.id}"),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, cardBorder)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // --- 1. VISOR POLIGONAL DE 70PX ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(PolygonalVisorShape)
            ) {
                // Imagen de fondo o gradiente de respaldo sin assets
                if (coverBitmap != null) {
                    Image(
                        bitmap = coverBitmap,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = if (isDarkMode) {
                                        listOf(Color(0xFF1E2838), Color(0xFF0F141F), Color(0xFF281E12))
                                    } else {
                                        listOf(Color(0xFF2F3C4F), Color(0xFF1D2635), Color(0xFF45361E))
                                    }
                                )
                            )
                    )
                }

                // Superposición de sombra oscura para garantizar legibilidad 100%
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x1F000000),
                                    Color(0x80000000),
                                    Color(0xEE000000)
                                )
                            )
                        )
                )

                // Borde inferior sutil dorado sobre el corte poligonal
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(0f, h * 0.82f)
                        lineTo(w * 0.5f, h)
                        lineTo(w, h * 0.82f)
                    }
                    drawPath(
                        path = path,
                        color = GoldenMain.copy(alpha = 0.8f),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 2.dp.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )
                }

                // Metadatos sobre el visor (ID, Autor y Título con sombra de alto contraste)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Badge con ID en oro litúrgico
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(GoldenMain)
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "${hymn.id}",
                                color = Color(0xFF12161A),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        // Autor de la alabanza
                        if (hymn.author.isNotBlank()) {
                            Text(
                                text = hymn.author,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    val annotatedTitle = buildHighlightedText(
                        originalText = hymn.title.uppercase(),
                        query = searchQuery,
                        hymnId = hymn.id,
                        isTitle = true,
                        stanzaIndex = -1,
                        globalMatches = globalMatches,
                        currentMatchIndex = currentMatchIndex
                    )

                    Text(
                        text = annotatedTitle,
                        color = Color.White,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.85f),
                                offset = Offset(0f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }

            // --- 2. TOOLBAR ELEVADO LIMPIO ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(toolbarBg)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Selector de versiones numérico estilo iOS segmented ([ 1 ] [ 2 ])
                if (versions.size > 1) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(if (isDarkMode) Color(0x33000000) else Color(0x1F000000))
                            .border(1.dp, if (isDarkMode) Color(0x1AFFFFFF) else Color(0x14000000), RoundedCornerShape(999.dp))
                            .padding(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        versions.indices.forEach { index ->
                            val isSelected = (index == selectedVersionIndex)
                            val pillBg by animateColorAsState(
                                targetValue = if (isSelected) GoldenMain else Color.Transparent,
                                animationSpec = tween(180),
                                label = "pillBg"
                            )
                            val pillTextColor = if (isSelected) Color(0xFF12161A) else textSecondary

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(pillBg)
                                    .clickable { selectedVersionIndex = index }
                                    .padding(horizontal = 9.dp, vertical = 3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = pillTextColor,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                                )
                            }
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                // Grupo de botones de acción a la derecha
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Botón de Favoritos
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("favorite_button_${hymn.id}")
                    ) {
                        if (hymn.isFavorite) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Favorito",
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            StarBorderIcon(
                                tint = GoldenMain,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Botón Compartir (Abre Action Sheet iOS)
                    IconButton(
                        onClick = onOpenShareSheet,
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("share_button_${hymn.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir Canto",
                            tint = GoldenMain,
                            modifier = Modifier.size(19.dp)
                        )
                    }

                    // Botón de YouTube directo en la barra
                    if (hymn.link.isNotBlank()) {
                        IconButton(
                            onClick = onOpenYoutube,
                            modifier = Modifier
                                .size(34.dp)
                                .testTag("youtube_button_${hymn.id}")
                        ) {
                            VideoPlayIcon(
                                tint = Color(0xFFFF0000),
                                modifier = Modifier.size(21.dp)
                            )
                        }
                    }
                }
            }

            // --- 3. LETRA DEL CANTO CON ESTROFAS COMPACTAS Y BADGES ---
            SelectionContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    rawStanzas.forEachIndexed { index, rawStanza ->
                        val (stanzaTag, stanzaText) = remember(rawStanza) { parseStanzaTag(rawStanza) }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                        ) {
                            // Badge sutil de estrofa o coro
                            if (stanzaTag != null) {
                                val isCoro = stanzaTag == "CORO"
                                val tagBg = if (isCoro) {
                                    GoldenMain.copy(alpha = 0.18f)
                                } else if (isDarkMode) {
                                    Color(0x33FFFFFF)
                                } else {
                                    Color(0x1F000000)
                                }
                                val tagColor = if (isCoro) {
                                    GoldenMain
                                } else if (isDarkMode) {
                                    Color(0xFFCBD5E1)
                                } else {
                                    Color(0xFF475569)
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(tagBg)
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = stanzaTag,
                                        color = tagColor,
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                            }

                            val annotatedStanza = buildHighlightedText(
                                originalText = stanzaText,
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
                                lineHeight = (fontSize * 1.48f).sp,
                                fontFamily = fontFamily,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Divisor armónico sutil entre estrofas
                        if (index < rawStanzas.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .width(22.dp)
                                    .height(1.dp)
                                    .background(GoldenMain.copy(alpha = 0.28f))
                            )
                        }
                    }
                }
            }
        }
    }
}
