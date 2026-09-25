package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GoldenMain
import kotlinx.coroutines.launch

/**
 * Barra lateral de desplazamiento rápido interactiva (Fast Scrollbar).
 * Muestra el progreso actual del scroll y permite arrastrar o tocar para saltar a cualquier posición.
 */
@Composable
fun FastScrollbar(
    lazyListState: LazyListState,
    totalItems: Int,
    modifier: Modifier = Modifier
) {
    if (totalItems <= 1) return

    val coroutineScope = rememberCoroutineScope()
    var isDragging by remember { mutableStateOf(false) }

    val scrollFraction by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) 0f
            else {
                val firstVisible = visibleItems.first().index
                val lastVisible = visibleItems.last().index
                val visibleCount = lastVisible - firstVisible + 1
                if (visibleCount >= totalItems) 0f
                else firstVisible.toFloat() / (totalItems - visibleCount).toFloat()
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .width(28.dp)
            .fillMaxHeight()
    ) {
        val maxHeightPx = constraints.maxHeight.toFloat()
        val thumbHeightDp = 48.dp
        val thumbHeightPx = with(LocalDensity.current) { thumbHeightDp.toPx() }
        val trackHeightPx = maxHeightPx - thumbHeightPx

        val thumbOffsetPx = (scrollFraction * trackHeightPx).coerceIn(0f, trackHeightPx)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(totalItems, trackHeightPx) {
                    detectTapGestures(
                        onPress = { offset ->
                            val clickY = offset.y
                            val fraction = ((clickY - thumbHeightPx / 2f) / trackHeightPx).coerceIn(0f, 1f)
                            val targetIndex = (fraction * (totalItems - 1)).toInt().coerceIn(0, totalItems - 1)
                            coroutineScope.launch {
                                lazyListState.scrollToItem(targetIndex)
                            }
                        }
                    )
                }
                .pointerInput(totalItems, trackHeightPx) {
                    detectDragGestures(
                        onDragStart = { isDragging = true },
                        onDragEnd = { isDragging = false },
                        onDragCancel = { isDragging = false },
                        onDrag = { change, _ ->
                            change.consume()
                            val currentY = change.position.y
                            val fraction = ((currentY - thumbHeightPx / 2f) / trackHeightPx).coerceIn(0f, 1f)
                            val targetIndex = (fraction * (totalItems - 1)).toInt().coerceIn(0, totalItems - 1)
                            coroutineScope.launch {
                                lazyListState.scrollToItem(targetIndex)
                            }
                        }
                    )
                },
            contentAlignment = Alignment.TopCenter
        ) {
            // Track
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(
                        color = if (isDragging) Color.Gray.copy(alpha = 0.4f) else Color.Gray.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(1.dp)
                    )
            )

            // Thumb
            Box(
                modifier = Modifier
                    .offset(y = with(LocalDensity.current) { thumbOffsetPx.toDp() })
                    .width(8.dp)
                    .height(thumbHeightDp)
                    .background(
                        color = if (isDragging) GoldenMain else GoldenMain.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(4.dp)
                    )
            )
        }
    }
}
