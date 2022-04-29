package br.dev.mavpf.autocontrol.ui.carselect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.Cars
import br.dev.mavpf.autocontrol.routes.NavRoutes
import br.dev.mavpf.autocontrol.ui.caradd.CarAddView

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CarSelectView(navRoutes: NavHostController) {
    val carSelectViewModel = hiltViewModel<CarSelectViewModel>()

    val lazyPagingItems: List<Cars> by carSelectViewModel.getCars().observeAsState(listOf())
    val rememberState = rememberLazyListState()

    var openDialogState = remember { mutableStateOf(false)}

    Column {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Menu, null)
                }
            }
        )

        LazyColumn(
            state = rememberState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(items = lazyPagingItems) { it ->
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(2.dp),
                    onClick = {
                        navRoutes.navigate("cardetails" + "/" + it.licenceplate)
                    }
                ) {
                    ConstraintLayout {
                        val (col1, col2) = createRefs()

                        Column(
                            modifier = Modifier
                                .constrainAs(col1) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                }
                        ) {
                            Row {
                                Text(
                                    buildAnnotatedString {
                                        append(it.maker)
                                        append(" ")
                                        append(it.model)
                                    }
                                )
                            }
                            Row {
                                Text(text = it.licenceplate)
                            }
                        }
                        Column(
                            modifier = Modifier.constrainAs(col2) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                            }
                        ) {
                            Text(text = it.mileage.toString())
                        }
                    }
                }
            }
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openDialogState.value = true
                    }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        ) {

        }
    }

    if (openDialogState.value) {
        Dialog(onDismissRequest = { openDialogState.value = false }) {
            openDialogState.value = CarAddView().value
        }
    }
}
