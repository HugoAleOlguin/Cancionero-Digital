package com.example.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.data.Hymn
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object PdfGenerator {

    /**
     * Genera un PDF multi-página de tamaño A4 de la alabanza seleccionada.
     * Ofrece un tamaño de letra fijo y sumamente legible (17pt), distribuyendo
     * el contenido en dos o más hojas si es necesario con un algoritmo inteligente.
     * Incorpora además cabecera de corte ornemental y pie de página con paginación real.
     */
    fun downloadHymnPdf(context: Context, hymn: Hymn): Uri? {
        val pdfDocument = PdfDocument()
        
        // Dimensiones estándar de una página A4 en puntos PostScript (72 puntos = 1 pulgada)
        // A4 mide exactamente 595 x 842 puntos
        val pageWidth = 595
        val pageHeight = 842
        
        val marginX = 40f
        val marginY = 54f
        
        // Pinceles estilizados para la imprenta elegante del himnario
        val paintTitle = Paint().apply {
            isAntiAlias = true
            color = Color.rgb(33, 43, 54) // Gris carbón selecto
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        val paintAuthor = Paint().apply {
            isAntiAlias = true
            color = Color.rgb(100, 110, 120) // Gris medio elegante
            textSize = 10f
            typeface = Typeface.create(Typeface.SERIF, Typeface.ITALIC)
            textAlign = Paint.Align.CENTER
        }
        
        val paintText = Paint().apply {
            isAntiAlias = true
            color = Color.rgb(55, 65, 81) // Gris oscuro elegante
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }
        
        val paintHeaderFooter = Paint().apply {
            isAntiAlias = true
            color = Color.rgb(120, 120, 120) // Gris sobrio
            textSize = 10f
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }

        val paintOrnament = Paint().apply {
            isAntiAlias = true
            color = Color.rgb(100, 116, 139) // Gris pizarra medio
            textSize = 11f
            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
            textAlign = Paint.Align.CENTER
        }

        val paintDivider = Paint().apply {
            color = Color.rgb(156, 163, 175) // Gris grey500 fino
            strokeWidth = 0.5f // Línea muy fina y elegante
            style = Paint.Style.STROKE
        }

        val bottomLimitY = 745f
        val titleSpace = 65f
        
        // Fragmentación del himno en estrofas
        val stanzas = hymn.content.split("\n\n")
        
        // Algoritmo de Escalamiento Inteligente y Selección de Tamaño de Fuente
        // Intentamos ajustar todo el himno en 1 sola página disminuyendo la fuente
        // progresivamente desde 17pt hasta un mínimo de 11pt.
        var optimalFontSize = 17f
        var finalPages: List<List<String>> = emptyList()
        val minFontSize = 11f
        var fontSizeTemp = 17f
        
        while (fontSizeTemp >= minFontSize) {
            val lineHeight = fontSizeTemp * 1.55f
            val spacingHeight = fontSizeTemp * 1.1f
            
            val tempPagesList = mutableListOf<MutableList<String>>()
            var tempCurrentPageLines = mutableListOf<String>()
            tempPagesList.add(tempCurrentPageLines)
            
            var currentY = marginY + titleSpace
            
            stanzas.forEachIndexed { stanzaIndex, stanza ->
                val lines = stanza.split("\n")
                val stanzaHeight = lines.size * lineHeight
                
                // Si la estrofa completa no cabe en este fragmento de página, abrimos otra
                if (currentY + stanzaHeight > bottomLimitY && tempCurrentPageLines.any { it.isNotBlank() }) {
                    tempCurrentPageLines = mutableListOf()
                    tempPagesList.add(tempCurrentPageLines)
                    currentY = marginY + 30f
                }
                
                lines.forEach { line ->
                    if (currentY + lineHeight > bottomLimitY) {
                        tempCurrentPageLines = mutableListOf()
                        tempPagesList.add(tempCurrentPageLines)
                        currentY = marginY + 30f
                    }
                    tempCurrentPageLines.add(line)
                    currentY += lineHeight
                }
                
                if (stanzaIndex < stanzas.lastIndex) {
                    if (currentY + spacingHeight <= bottomLimitY) {
                        tempCurrentPageLines.add("") // Espacio entre estrofas
                        currentY += spacingHeight
                    } else {
                        tempCurrentPageLines = mutableListOf()
                        tempPagesList.add(tempCurrentPageLines)
                        currentY = marginY + 30f
                    }
                }
            }
            
            val filteredPages = tempPagesList.filter { p -> p.any { it.isNotBlank() } }
            finalPages = filteredPages
            optimalFontSize = fontSizeTemp
            
            // Si cabe todo perfectamente en una página, conservamos este tamaño y salimos del bucle
            if (filteredPages.size <= 1) {
                break
            }
            fontSizeTemp -= 0.5f // Decremento sutil para ajuste perfecto
        }
        
        val totalPages = finalPages.size.coerceAtLeast(1)
        
        // Renderizado definitivo sobre el documento PDF
        for (pageIndex in 1..totalPages) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageIndex).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            
            // Fondo blanco
            canvas.drawColor(Color.WHITE)
            
            // Cabecera Ornamental
            canvas.drawLine(marginX, 54f, pageWidth - marginX, 54f, paintDivider)
            canvas.drawText("❖", pageWidth / 2f, 66f, paintOrnament)
            
            // Determinar origen Y para renderizar cuerpo de texto
            var renderY = marginY + 30f
            if (pageIndex == 1) {
                // Título principal arriba centrada en la primera hoja
                paintTitle.textSize = 21f
                val displayTitle = "${hymn.id} - ${hymn.title.uppercase()}"
                if (hymn.author.isNotEmpty()) {
                    canvas.drawText(displayTitle, pageWidth / 2f, marginY + 20f, paintTitle)
                    canvas.drawText(hymn.author, pageWidth / 2f, marginY + 38f, paintAuthor)
                } else {
                    canvas.drawText(displayTitle, pageWidth / 2f, marginY + 25f, paintTitle)
                }
                renderY = marginY + titleSpace
            }
            
            // Obtener el contenido de líneas a dibujar en esta hoja
            val linesToDraw = if (pageIndex <= finalPages.size) finalPages[pageIndex - 1] else listOf()
            
            // Distribución vertical inteligente para centrado perfecto de estrofas en la página
            var linesHeight = 0f
            val spacingHeight = optimalFontSize * 1.1f
            val lineHeight = optimalFontSize * 1.55f
            paintText.textSize = optimalFontSize
            
            linesToDraw.forEach { line ->
                if (line.isEmpty()) {
                    linesHeight += spacingHeight
                } else {
                    linesHeight += lineHeight
                }
            }
            
            val availableHeightForPage = if (pageIndex == 1) {
                bottomLimitY - (marginY + titleSpace)
            } else {
                bottomLimitY - (marginY + 30f)
            }
            
            val verticalOffset = ((availableHeightForPage - linesHeight) / 2f).coerceAtLeast(0f)
            renderY += verticalOffset
            
            // Imprimir líneas centradas horizontalmente conforme a requerimientos
            linesToDraw.forEach { line ->
                if (line.isNotEmpty()) {
                    canvas.drawText(line, pageWidth / 2f, renderY, paintText)
                    renderY += lineHeight
                } else {
                    renderY += spacingHeight
                }
            }
            
            // Pie de Página - Separador y Numeración real "X de Y"
            canvas.drawLine(marginX, pageHeight - 54f, pageWidth - marginX, pageHeight - 54f, paintDivider)
            canvas.drawText("cuadernillo digital  |  $pageIndex de $totalPages", pageWidth / 2f, pageHeight - 38f, paintHeaderFooter)
            
            pdfDocument.finishPage(page)
        }
        
        // Escritura y Guardado del Archivo Generado
        val sanitizedTitle = hymn.title.replace(" ", "_")
            .replace("/", "-")
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")
            .replace("Ñ", "N")
            .replace("ñ", "n")
        val filename = "Alabanza_$sanitizedTitle.pdf"
        var uri: Uri? = null
        var outputStream: OutputStream? = null
        
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    outputStream = resolver.openOutputStream(uri)
                }
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = File(downloadsDir, filename)
                outputStream = FileOutputStream(file)
                uri = Uri.fromFile(file)
            }
            
            if (outputStream != null) {
                pdfDocument.writeTo(outputStream)
                Toast.makeText(context, "Descargado en Descargas: $filename", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al generar PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            try {
                outputStream?.close()
            } catch (e: Exception) {}
            pdfDocument.close()
        }
        
        return uri
    }
}
