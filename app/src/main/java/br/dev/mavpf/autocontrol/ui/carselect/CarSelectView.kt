package br.dev.mavpf.autocontrol.ui.carselect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.Cars
import br.dev.mavpf.autocontrol.ui.caradd.carAddView

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CarSelectView(navRoutes: NavHostController) {
    val carSelectViewModel = hiltViewModel<CarSelectViewModel>()

    val lazyPagingItems: List<Cars> by carSelectViewModel.getCars().observeAsState(listOf())
    val rememberState = rememberLazyListState()

    var openDialogState by remember { mutableStateOf(false)}

    Column {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
               MenuOpen(navRoutes)
            }
        )

        LazyColumn(
            state = rememberState,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(items = lazyPagingItems) {
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
                        openDialogState = true
                    }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        ) {

        }
    }

    if (openDialogState) {
        Dialog(onDismissRequest = { openDialogState = false }) {
            openDialogState = carAddView()
        }
    }
}


@Composable
fun MenuOpen(navRoutes: NavHostController){

    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded = true

        }) {
            Icon(
                Icons.Filled.Menu,
                null
            )
        }
    }

    DropdownMenu(expanded = expanded , onDismissRequest = { expanded = false }, Modifier.width(IntrinsicSize.Max)) {
        DropdownMenuItem(onClick = { navRoutes.navigate("gasdetail") }) {
            Icon(
                Icons.Filled.LocalGasStation,
                null,
                Modifier.weight(0.3f))
            Text(text = stringResource(id = R.string.menu_gas),
                Modifier
                .weight(1f)
                .padding(2.dp))
        }
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            Icon(
                Icons.Filled.HomeRepairService,
                null,
                Modifier
                    .weight(0.3f))
            Text(text = stringResource(id = R.string.menu_service),
                Modifier
                    .weight(1f)
                    .padding(2.dp))
        }
    }
}