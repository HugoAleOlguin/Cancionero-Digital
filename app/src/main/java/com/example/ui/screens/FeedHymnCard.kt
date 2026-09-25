package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.HymnEntity
import com.example.search.MatchOccurrence
import com.example.search.SearchableHymn
import com.example.search.normalize
import com.example.ui.components.DownloadIcon
import com.example.ui.components.StarBorderIcon
import com.example.ui.components.VideoPlayIcon
import com.example.ui.theme.GoldenMain
import com.example.ui.theme.JetCarbon
import com.example.util.PdfGenerator

/**
 * Resaltador visual de texto coincidente tolerante a mayúsculas y acentos.
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
        val tokens = normalizedQuery.split(" ").filter { it.isNotBlank() }

        // Resaltar coincidencias de frase completa o palabras individuales
        val searchTargets = if (normalizedText.contains(normalizedQuery)) {
            listOf(normalizedQuery)
        } else {
            tokens
        }

        for (target in searchTargets) {
            var index = normalizedText.indexOf(target)
            while (index != -1 && target.isNotEmpty()) {
                val end = index + target.length
                val range = index until end

                val occurrence = MatchOccurrence(
                    hymnId = hymnId,
                    isTitle = isTitle,
                    stanzaIndex = stanzaIndex,
                    charRange = range
                )

                val isActive = if (currentMatchIndex in globalMatches.indices) {
                    val currentOcc = globalMatches[currentMatchIndex]
                    currentOcc.hymnId == hymnId && currentOcc.isTitle == isTitle &&
                            currentOcc.stanzaIndex == stanzaIndex && currentOcc.charRange == range
                } else {
                    false
                }

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

                index = normalizedText.indexOf(target, index + 1)
            }
        }

        builder.toAnnotatedString()
    }
}

/**
 * Tarjeta individual que muestra la alabanza completa con estrofas centradas,
 * acciones de favoritos, descarga de PDF, reproducción y compartir.
 */
@Composable
fun FeedHymnCard(
    searchableHymn: SearchableHymn,
    fontSize: Float,
    fontFamily: FontFamily = FontFamily.Serif,
    searchQuery: String,
    globalMatches: List<MatchOccurrence>,
    currentMatchIndex: Int,
    isDarkMode: Boolean,
    onToggleFavorite: () -> Unit,
    onDownload: () -> Unit
) {
    val hymn = searchableHymn.hymn
    val versions = remember(hymn) { hymn.getVersions() }
    var selectedVersionIndex by remember(hymn.id) { mutableStateOf(0) }
    val activeContent = if (selectedVersionIndex in versions.indices) versions[selectedVersionIndex] else hymn.content
    val stanzas = remember(activeContent) { activeContent.split("\n\n") }
    val context = LocalContext.current
    var showShareMenu by remember { mutableStateOf(false) }

    val cardBg = if (isDarkMode) Color(0xFF1E1E1E) else Color.White
    val headerBg = if (isDarkMode) Color(0xFF2A2A2A) else Color(0xFFF8F9FA)
    val textPrimary = if (isDarkMode) Color.White else JetCarbon
    val textSecondary = if (isDarkMode) Color(0xFFB0B0B0) else Color.Gray

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
            // Encabezado interno con ID y Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(headerBg)
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = annotatedTitle,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = fontFamily
                    )
                    if (hymn.author.isNotEmpty()) {
                        Text(
                            text = hymn.author,
                            color = textSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = fontFamily,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Selector de Versiones numérico minimalista ([ 1 ] [ 2 ])
                if (versions.size > 1) {
                    Row(
                        modifier = Modifier.padding(end = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        versions.indices.forEach { index ->
                            val isSelected = (index == selectedVersionIndex)
                            val btnBg = if (isSelected) GoldenMain else if (isDarkMode) Color(0xFF383838) else Color(0xFFE4E4E8)
                            val btnTextColor = if (isSelected) Color.White else textSecondary
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(btnBg)
                                    .clickable { selectedVersionIndex = index }
                                    .padding(horizontal = 7.dp, vertical = 3.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = btnTextColor,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                // Botón de Favorito
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

                // Botón de Descarga PDF
                IconButton(
                    onClick = onDownload,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("download_button_${hymn.id}")
                ) {
                    DownloadIcon(tint = GoldenMain, modifier = Modifier.size(20.dp))
                }

                // Botón de Compartir
                Box {
                    IconButton(
                        onClick = { showShareMenu = true },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("share_button_${hymn.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartir Canto",
                            tint = GoldenMain,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = showShareMenu,
                        onDismissRequest = { showShareMenu = false },
                        modifier = Modifier.background(if (isDarkMode) Color(0xFF2A2A2A) else Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Compartir como PDF", color = textPrimary) },
                            onClick = {
                                showShareMenu = false
                                try {
                                    val legacyHymn = com.example.data.Hymn(
                                        id = hymn.id,
                                        title = hymn.title,
                                        content = activeContent,
                                        isFavorite = hymn.isFavorite,
                                        link = hymn.link,
                                        author = hymn.author
                                    )
                                    val uri = PdfGenerator.downloadHymnPdf(context, legacyHymn)
                                    if (uri != null) {
                                        val shareIntent = Intent().apply {
                                            action = Intent.ACTION_SEND
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            type = "application/pdf"
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(Intent.createChooser(shareIntent, "Compartir PDF"))
                                    } else {
                                        Toast.makeText(context, "No se pudo generar el PDF para compartir", Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error al compartir PDF: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Compartir como Texto", color = textPrimary) },
                            onClick = {
                                showShareMenu = false
                                try {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        val authorText = if (hymn.author.isNotEmpty()) "\nAutor: ${hymn.author}" else ""
                                        putExtra(Intent.EXTRA_TEXT, "${hymn.id} - ${hymn.title.uppercase()}$authorText\n\n$activeContent")
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Compartir Alabanza"))
                                } catch (e: Exception) {
                                    Toast.makeText(context, "No se pudo compartir la alabanza", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }

                // Botón de YouTube
                if (hymn.link.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hymn.link))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "No se pudo abrir el enlace de YouTube", Toast.LENGTH_SHORT).show()
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

            // Cuerpo del canto con estrofas centradas
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
                            fontFamily = fontFamily,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                        if (index < stanzas.lastIndex) {
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
