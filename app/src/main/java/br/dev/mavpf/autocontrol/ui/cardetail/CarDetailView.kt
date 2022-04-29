package br.dev.mavpf.autocontrol.ui.cardetail

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun CarDetailView (navRoutes: NavHostController, licencePlate: String){
    Text(text = licencePlate)
}