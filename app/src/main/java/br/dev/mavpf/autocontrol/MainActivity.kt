package br.dev.mavpf.autocontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import br.dev.mavpf.autocontrol.routes.NavRoutes
import br.dev.mavpf.autocontrol.ui.theme.AutoControlTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoControlTheme {
                //Nav Control
                val navController = rememberNavController()
                NavRoutes(navController = navController)
            }
        }
    }

}