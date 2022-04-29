package br.dev.mavpf.autocontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.dev.mavpf.autocontrol.routes.NavRoutes
import br.dev.mavpf.autocontrol.ui.caradd.CarAddView
import br.dev.mavpf.autocontrol.ui.cardetail.CarDetailView
import br.dev.mavpf.autocontrol.ui.carselect.CarSelectView
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
                NavHost(
                    navController = navController,
                    startDestination = "carselect"
                ) {
                    composable(
                        "carselect"
                    ) { CarSelectView(navController) }

                    composable(
                        "cardetails/{licencePlate}",
                        arguments = listOf(navArgument("licencePlate") {
                            type = NavType.StringType
                        })
                    ) { navBackStackEntry ->
                        CarDetailView(
                            navController,
                            navBackStackEntry.arguments?.getString("licencePlate") ?: ""
                        )
                    }

                }
            }
        }
    }

}