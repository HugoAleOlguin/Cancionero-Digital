package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GoldenMain
import kotlin.math.cos
import kotlin.math.sin

/**
 * Logotipo canónico del Cancionero Digital: nota musical dorada dibujada en Canvas.
 */
@Composable
fun AppLogo(modifier: Modifier = Modifier, isDarkMode: Boolean = false) {
    val noteColor = GoldenMain

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val scaleX = w / 108f
        val scaleY = h / 108f

        val notePath = Path().apply {
            moveTo(54f * scaleX, 26f * scaleY)
            cubicTo(53.8f * scaleX, 26f * scaleY, 53.6f * scaleX, 26f * scaleY, 53.4f * scaleX, 26.1f * scaleY)
            cubicTo(52.6f * scaleX, 26.3f * scaleY, 52f * scaleX, 27f * scaleY, 52f * scaleX, 27.9f * scaleY)
            lineTo(52f * scaleX, 57.2f * scaleY)
            cubicTo(50f * scaleX, 55.9f * scaleY, 47.6f * scaleX, 55.1f * scaleY, 45f * scaleX, 55.1f * scaleY)
            cubicTo(38.9f * scaleX, 55.1f * scaleY, 34f * scaleX, 60f * scaleY, 34f * scaleX, 66f * scaleY)
            cubicTo(34f * scaleX, 72f * scaleY, 38.9f * scaleX, 77f * scaleY, 45f * scaleX, 77f * scaleY)
            cubicTo(51.1f * scaleX, 77f * scaleY, 56f * scaleX, 72.1f * scaleY, 56f * scaleX, 66f * scaleY)
            lineTo(56f * scaleX, 34.8f * scaleY)
            cubicTo(60.3f * scaleX, 37.2f * scaleY, 65.3f * scaleX, 38.7f * scaleY, 70.6f * scaleX, 38.9f * scaleY)
            cubicTo(70.7f * scaleX, 38.9f * scaleY, 70.8f * scaleX, 38.9f * scaleY, 71f * scaleX, 38.9f * scaleY)
            cubicTo(71.9f * scaleX, 38.9f * scaleY, 72.7f * scaleX, 38.2f * scaleY, 72.8f * scaleX, 37.3f * scaleY)
            cubicTo(72.9f * scaleX, 36.3f * scaleY, 72.2f * scaleX, 35.5f * scaleY, 71.2f * scaleX, 35.4f * scaleY)
            cubicTo(65.4f * scaleX, 34.9f * scaleY, 60f * scaleX, 32.7f * scaleY, 55.8f * scaleX, 29.2f * scaleY)
            cubicTo(55.2f * scaleX, 28.7f * scaleY, 54.6f * scaleX, 28.4f * scaleY, 53.8f * scaleX, 28.3f * scaleY)
            cubicTo(54.4f * scaleX, 28.3f * scaleY, 54.2f * scaleX, 28.2f * scaleY, 54f * scaleX, 28.2f * scaleY)
            close()
        }
        drawPath(path = notePath, color = noteColor)
    }
}

/**
 * Icono de descarga de precisión gráfica implementado en Compose nativo.
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
            path(fill = SolidColor(tint)) {
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
 * Icono dinámico Sol/Luna dibujado sobre Canvas para alternar entre modo claro y oscuro.
 */
@Composable
fun ThemeToggleIcon(isDarkMode: Boolean, modifier: Modifier = Modifier, tint: Color = Color.White) {
    Canvas(modifier = modifier) {
        val radius = size.minDimension / 2f
        val center = Offset(size.width / 2f, size.height / 2f)
        if (isDarkMode) {
            drawCircle(
                color = tint,
                radius = radius * 0.85f,
                center = center
            )
            drawCircle(
                color = Color(0xFF1E1E1E),
                radius = radius * 0.78f,
                center = Offset(center.x - radius * 0.42f, center.y - radius * 0.15f)
            )
        } else {
            drawCircle(
                color = tint,
                radius = radius * 0.45f,
                center = center
            )
            val rayCount = 8
            for (i in 0 until rayCount) {
                val angle = (i * (2 * Math.PI) / rayCount).toFloat()
                val startX = center.x + (radius * 0.62f) * cos(angle)
                val startY = center.y + (radius * 0.62f) * sin(angle)
                val endX = center.x + (radius * 0.85f) * cos(angle)
                val endY = center.y + (radius * 0.85f) * sin(angle)

                drawLine(
                    color = tint,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

/**
 * Icono de libros de biblioteca.
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
 * Icono de estrella para favoritos.
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
 * Icono de reproducción de video (YouTube).
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

/**
 * Icono de documento PDF implementado en vector nativo.
 */
@Composable
fun PdfFileIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_pdf_file",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(6f, 2f)
                lineTo(14f, 2f)
                lineTo(20f, 8f)
                lineTo(20f, 20f)
                curveTo(20f, 21.1f, 19.1f, 22f, 18f, 22f)
                lineTo(6f, 22f)
                curveTo(4.9f, 22f, 4f, 21.1f, 4f, 20f)
                lineTo(4f, 4f)
                curveTo(4f, 2.9f, 4.9f, 2f, 6f, 2f)
                close()
                moveTo(13f, 3.5f)
                lineTo(13f, 9f)
                lineTo(18.5f, 9f)
                close()
                moveTo(8f, 13f)
                lineTo(16f, 13f)
                lineTo(16f, 14.5f)
                lineTo(8f, 14.5f)
                close()
                moveTo(8f, 16.5f)
                lineTo(14f, 16.5f)
                lineTo(14f, 18f)
                lineTo(8f, 18f)
                close()
            }
        }.build(),
        contentDescription = "Documento PDF",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Icono de mensaje / compartir texto implementado en vector nativo.
 */
@Composable
fun ShareTextIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_share_text",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(20f, 2f)
                lineTo(4f, 2f)
                curveTo(2.9f, 2f, 2f, 2.9f, 2f, 4f)
                lineTo(2f, 22f)
                lineTo(6f, 18f)
                lineTo(20f, 18f)
                curveTo(21.1f, 18f, 22f, 17.1f, 22f, 16f)
                lineTo(22f, 4f)
                curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
                close()
                moveTo(6f, 9f)
                lineTo(18f, 9f)
                lineTo(18f, 10.5f)
                lineTo(6f, 10.5f)
                close()
                moveTo(6f, 13f)
                lineTo(14f, 13f)
                lineTo(14f, 14.5f)
                lineTo(6f, 14.5f)
                close()
            }
        }.build(),
        contentDescription = "Compartir Texto",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Icono de copiar al portapapeles en vector nativo.
 */
@Composable
fun ClipboardCopyIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_clipboard_copy",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(16f, 1f)
                lineTo(4f, 1f)
                curveTo(2.9f, 1f, 2f, 1.9f, 2f, 3f)
                lineTo(2f, 17f)
                lineTo(4f, 17f)
                lineTo(4f, 3f)
                lineTo(16f, 3f)
                lineTo(16f, 1f)
                close()
                moveTo(19f, 5f)
                lineTo(8f, 5f)
                curveTo(6.9f, 5f, 6f, 5.9f, 6f, 7f)
                lineTo(6f, 21f)
                curveTo(6f, 22.1f, 6.9f, 23f, 8f, 23f)
                lineTo(19f, 23f)
                curveTo(20.1f, 23f, 21f, 22.1f, 21f, 21f)
                lineTo(21f, 7f)
                curveTo(21f, 5.9f, 20.1f, 5f, 19f, 5f)
                close()
                moveTo(17f, 19f)
                lineTo(10f, 19f)
                lineTo(10f, 7f)
                lineTo(17f, 7f)
                lineTo(17f, 19f)
                close()
            }
        }.build(),
        contentDescription = "Copiar Portapapeles",
        modifier = modifier,
        tint = tint
    )
}

/**
 * Icono de destellos / estrella estética para el Tema Moderno.
 */
@Composable
fun SparkleThemeIcon(modifier: Modifier = Modifier, tint: Color) {
    Icon(
        imageVector = ImageVector.Builder(
            name = "custom_sparkle_theme",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(tint)) {
                moveTo(12f, 2f)
                curveTo(12f, 7.52f, 16.48f, 12f, 22f, 12f)
                curveTo(16.48f, 12f, 12f, 16.48f, 12f, 22f)
                curveTo(12f, 16.48f, 7.52f, 12f, 2f, 12f)
                curveTo(7.52f, 12f, 12f, 7.52f, 12f, 2f)
                close()
            }
        }.build(),
        contentDescription = "Tema Moderno",
        modifier = modifier,
        tint = tint
    )
}

