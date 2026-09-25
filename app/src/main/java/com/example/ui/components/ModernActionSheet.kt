package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.database.HymnEntity
import com.example.ui.theme.GoldenMain
import com.example.ui.theme.JetCarbon
import com.example.util.rememberCoverImage

/**
 * Bottom Action Sheet modal al estilo iOS / Fluent Glassmorphism
 * para acciones avanzadas de compartir y exportar alabanzas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernActionSheet(
    hymn: HymnEntity,
    coverPath: String?,
    isDarkMode: Boolean,
    onDismiss: () -> Unit,
    onDownloadPdf: () -> Unit,
    onSharePdf: () -> Unit,
    onShareText: () -> Unit,
    onCopyClipboard: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val textPrimary = if (isDarkMode) Color.White else JetCarbon
    val textSecondary = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)
    val containerBg = if (isDarkMode) Color(0xFF131822) else Color(0xFFF9F7F2)
    val itemBg = if (isDarkMode) Color(0xFF1B2230) else Color(0xFFEFECE4)
    val itemBorder = if (isDarkMode) Color(0x22FFFFFF) else Color(0x33C5A03A)

    val coverBitmap = rememberCoverImage(coverPath)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = containerBg,
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 8.dp)
                    .width(42.dp)
                    .height(4.5.dp)
                    .clip(CircleShape)
                    .background(if (isDarkMode) Color(0xFF4A5568) else Color(0xFFCBD5E1))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 24.dp)
        ) {
            // Header del canto con miniatura HD
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(itemBg)
                    .border(1.dp, itemBorder, RoundedCornerShape(16.dp))
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF232D3F), Color(0xFF0F172A))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (coverBitmap != null) {
                        Image(
                            bitmap = coverBitmap,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        AppLogo(modifier = Modifier.size(24.dp), isDarkMode = isDarkMode)
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${hymn.id} - ${hymn.title.uppercase()}",
                        color = textPrimary,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (hymn.author.isNotBlank()) hymn.author else "Cancionero Digital",
                        color = textSecondary,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de acciones tipo iOS Sheet
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ActionSheetButton(
                    title = "Descargar PDF",
                    description = "Guardar archivo en la carpeta Descargas",
                    icon = { DownloadIcon(tint = GoldenMain, modifier = Modifier.size(20.dp)) },
                    itemBg = itemBg,
                    itemBorder = itemBorder,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    onClick = {
                        onDismiss()
                        onDownloadPdf()
                    }
                )

                ActionSheetButton(
                    title = "Compartir como PDF",
                    description = "Enviar archivo PDF a WhatsApp u otra app",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = GoldenMain,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    itemBg = itemBg,
                    itemBorder = itemBorder,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    onClick = {
                        onDismiss()
                        onSharePdf()
                    }
                )

                ActionSheetButton(
                    title = "Compartir Letra",
                    description = "Enviar texto con formato por WhatsApp",
                    icon = { ShareTextIcon(tint = GoldenMain, modifier = Modifier.size(20.dp)) },
                    itemBg = itemBg,
                    itemBorder = itemBorder,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    onClick = {
                        onDismiss()
                        onShareText()
                    }
                )

                ActionSheetButton(
                    title = "Copiar al Portapapeles",
                    description = "Copiar la letra completa del canto",
                    icon = { ClipboardCopyIcon(tint = GoldenMain, modifier = Modifier.size(20.dp)) },
                    itemBg = itemBg,
                    itemBorder = itemBorder,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    onClick = {
                        onDismiss()
                        onCopyClipboard()
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón cancelar
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            ) {
                Text(
                    text = "Cancelar",
                    color = GoldenMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ActionSheetButton(
    title: String,
    description: String,
    icon: @Composable () -> Unit,
    itemBg: Color,
    itemBorder: Color,
    textPrimary: Color,
    textSecondary: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(itemBg)
            .border(1.dp, itemBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GoldenMain.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = textPrimary,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                color = textSecondary,
                fontSize = 11.sp
            )
        }
    }
}
