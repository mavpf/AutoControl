package br.dev.mavpf.autocontrol.routes

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavRoutes (val routes: String) {
    object CarSelect : NavRoutes("carselect")
    object CarAdd: NavRoutes("caradd")
    object CarDetail: NavRoutes("cardetail")
    object FuelAdd: NavRoutes("fueladd/{licencePlate}")
    object FuelConfig: NavRoutes("fuelconfig")
    object ServiceAdd: NavRoutes("serviceadd/{licencePlate}")
    object ServiceConfig: NavRoutes("serviceconfig")
}
