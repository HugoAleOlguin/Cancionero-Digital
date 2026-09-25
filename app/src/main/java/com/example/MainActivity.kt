package com.example

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.HymnFeedScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.MainViewModel

/**
 * Punto de entrada principal del Cancionero Digital.
 * Configura la ventana con FLAG_KEEP_SCREEN_ON para lectura litúrgica ininterrumpida
 * y renderiza el feed reactivo de alabanzas.
 */
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mantener la pantalla encendida permanentemente durante el culto/reunión
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            MyApplicationTheme(darkTheme = isDarkMode) {
                HymnFeedScreen(viewModel = viewModel)
            }
        }
    }
}

/**
 * Composable de nivel superior para previsualizaciones y pruebas de screenshot (Roborazzi).
 */
@Composable
fun HymnApp(viewModel: MainViewModel = viewModel()) {
    HymnFeedScreen(viewModel = viewModel)
}
