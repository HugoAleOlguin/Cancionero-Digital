package com.example.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * Cargador de imágenes nativo de alto rendimiento para Jetpack Compose.
 * Utiliza BitmapFactory y LruCache en memoria sin requerir librerías externas pesadas (como Coil o Glide),
 * garantizando que el APK base no aumente ni un solo byte de tamaño.
 */
object ModernImageLoader {

    // Caché en memoria de hasta 1/8 de la memoria disponible (máx. ~25MB)
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = maxMemory / 8

    private val memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }

    /**
     * Carga un mapa de bits desde almacenamiento interno con decodificación eficiente y caché.
     */
    suspend fun loadBitmap(filePath: String?): Bitmap? = withContext(Dispatchers.IO) {
        if (filePath.isNullOrBlank()) return@withContext null

        // 1. Comprobar caché en memoria
        memoryCache.get(filePath)?.let { return@withContext it }

        val file = File(filePath)
        if (!file.exists() || !file.canRead()) return@withContext null

        return@withContext try {
            // Decodificar dimensiones primero para calcular muestreo
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeFile(filePath, options)

            // Escalar a una resolución óptima para el visor (ancho objetivo ~720px, alto ~360px)
            options.inSampleSize = calculateInSampleSize(options, 720, 360)
            options.inJustDecodeBounds = false
            options.inPreferredConfig = Bitmap.Config.RGB_565 // Optimización de memoria (2 bytes por pixel)

            val decodedBitmap = BitmapFactory.decodeFile(filePath, options)
            if (decodedBitmap != null) {
                memoryCache.put(filePath, decodedBitmap)
            }
            decodedBitmap
        } catch (e: Exception) {
            null
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun clearCache() {
        memoryCache.evictAll()
    }
}

/**
 * Composable reactivo que carga de forma asíncrona un archivo de imagen en segundo plano
 * y lo expone como un [ImageBitmap] para Compose sin bloquear el hilo principal.
 */
@Composable
fun rememberCoverImage(filePath: String?): ImageBitmap? {
    val imageState = produceState<ImageBitmap?>(initialValue = null, key1 = filePath) {
        val bitmap = ModernImageLoader.loadBitmap(filePath)
        value = bitmap?.asImageBitmap()
    }
    return imageState.value
}
