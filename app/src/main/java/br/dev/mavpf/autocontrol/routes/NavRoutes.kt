package br.dev.mavpf.autocontrol.routes

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.dev.mavpf.autocontrol.ui.cardetail.CarDetailView
import br.dev.mavpf.autocontrol.ui.carselect.CarSelectView
import br.dev.mavpf.autocontrol.ui.gasdetail.GasDetailView

@Composable
fun NavRoutes(navController: NavHostController) {
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

        composable(
            "gasdetail"
        ) { GasDetailView(navRoutes = navController) }

    }
}
