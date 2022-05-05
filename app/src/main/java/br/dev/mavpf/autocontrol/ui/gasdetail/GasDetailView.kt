package br.dev.mavpf.autocontrol.ui.gasdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.GasTypes
import br.dev.mavpf.autocontrol.ui.gasadd.gasCRUDView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GasDetailView(navRoutes: NavHostController){
    
    val viewModel = hiltViewModel<GasDetailViewModel>()
    val rememberListState = rememberLazyListState()
    val lazyPagingItems: List<GasTypes> by viewModel.getGas().observeAsState(listOf())
    var openDialogAddState by remember { mutableStateOf(false)}
    var openDialogChangeState by remember { mutableStateOf(false)}
    var gasName by remember { mutableStateOf(GasTypes("",0,""))}

    Column() {

        TopAppBar(
            title = { Text(text = stringResource(id = R.string.menu_gas)) },
            navigationIcon = {
                IconButton(onClick = { navRoutes.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            }
        )

        LazyColumn(
            state = rememberListState
        ) {
            if (lazyPagingItems.isEmpty()) {
                item {
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(45.dp),
                        elevation = 10.dp,
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Box(modifier = Modifier.wrapContentSize(Alignment.Center)) {
                            Text(text = stringResource(id = R.string.no_gas_type))
                        }
                    }
                }
            } else {
                items(items = lazyPagingItems) {
                    Card(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .defaultMinSize(minHeight = 60.dp)
                        ,
                        elevation = 10.dp,
                        shape = RoundedCornerShape(2.dp),
                        onClick = {
                            gasName = GasTypes(it.gasname, it.octanes, it.obs)
                            openDialogChangeState = true
                        }
                    ) {
                        Column(Modifier.fillMaxHeight()) {

                            Row(
                                Modifier
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.gas_type),
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(id = R.string.gas_octanes),
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(id = R.string.gas_obs),
                                    modifier = Modifier.weight(1f),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row(
                                Modifier
                                    .wrapContentSize()
                                    .fillMaxSize()
                                    .padding(5.dp)
                            ) {
                                Text(
                                    text = it.gasname,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = it.octanes.toString(),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = it.obs,
                                    modifier = Modifier
                                        .weight(1f)
                                        .wrapContentSize()
                                )
                            }
                        }
                    }
                }
            }
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        openDialogAddState = true
                    }
                ) {
                    Icon(Icons.Filled.Add, null)
                }
            }
        ) {

        }

        if (openDialogAddState){
            Dialog(onDismissRequest = { openDialogAddState = false}) {
                openDialogAddState = gasCRUDView("insert",gasName)
            }
        }

        if (openDialogChangeState){
            Dialog(onDismissRequest = { openDialogChangeState = false }) {
                openDialogChangeState = gasCRUDView(crud = "change", gasValue = gasName)
            }
        }
    }
}