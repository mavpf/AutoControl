package br.dev.mavpf.autocontrol.ui.gasadd

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.ui.carselect.MenuOpen
import java.lang.reflect.Modifier

@Composable
fun GasAddView(navRoutes: NavHostController){
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.menu_gas)) },
        navigationIcon = {
                         IconButton(onClick = { navRoutes.popBackStack()}) {
                             Icon(Icons.Default.ArrowBack, null)
                         }
        }
    )
}