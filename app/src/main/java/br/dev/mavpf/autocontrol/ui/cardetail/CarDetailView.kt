package br.dev.mavpf.autocontrol.ui.cardetail

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.types.FuelConsumption

enum class MultiFloatingState {
    Expanded,
    Collapsed
}

@Composable
fun CarDetailView(navRoutes: NavHostController, licencePlate: String) {
    val viewModel = hiltViewModel<CarDetailViewModel>()

    val model: String by viewModel.selectCar(licencePlate).observeAsState(String())

    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Collapsed) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            title = { Text(text = "$model $licencePlate") },
            actions = {
            }
        )
        CardConsumption(viewModel)
        CardTotalCosts(viewModel)
        Scaffold(
            floatingActionButton = {
                MultiFloatingActionButton(
                    multiFloatingState = multiFloatingState,
                    onMultiFabStateChange = {
                        multiFloatingState = it
                    }
                )
            }
        ) {
        }
    }
}

@Composable
private fun MultiFloatingActionButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit
) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    FloatingActionButton(onClick = {
        onMultiFabStateChange (
            if (transition.currentState == MultiFloatingState.Expanded) {
                MultiFloatingState.Collapsed
            } else {
                MultiFloatingState.Expanded
            }
        )
    }) {
        Icon(Icons.Default.Add, null, Modifier.rotate(rotate))

    }
}

@Composable
private fun CardConsumption(viewModel: CarDetailViewModel) {
    val dataset: List<FuelConsumption> = viewModel.getConsumptionAverages()
    Card(
        modifier = Modifier
            .padding(5.dp)
            .shadow(15.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ConstraintLayout {
            val (col1, col2) = createRefs()

            Column(
                Modifier
                    .constrainAs(col1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .padding(5.dp))
            {
                Text(text = stringResource(id = R.string.consuption_header))
            }
            Column(Modifier.constrainAs(col2) {
                top.linkTo(col1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                LazyRow(
                    Modifier
                        .align(CenterHorizontally)
                        .wrapContentSize()
                ) {
                    items(dataset) { index ->
                        Column(
                            Modifier
                                .wrapContentWidth()
                                .padding(5.dp)
                        ) {
                            Row(
                                Modifier
                                    .align(CenterHorizontally)
                                    .wrapContentWidth()
                            ) {
                                Text(text = index.fuelType)
                            }
                            Row(Modifier.align(CenterHorizontally)) {
                                Text(text = index.fuelAverage.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardTotalCosts(viewModel: CarDetailViewModel) {

    val dataset = viewModel.getCosts()
    Card(
        modifier = Modifier
            .padding(5.dp)
            .shadow(15.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column {


            Row {
                Text(
                    text = stringResource(id = R.string.total_cost_header),
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Row {


                LazyColumn {
                    items(dataset) { index ->
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            val (text1, text2) = createRefs()

                            Text(text = index.costName,
                                modifier = Modifier
                                    .constrainAs(text1) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                    })
                            Text(text = index.costValue.toString(),
                                modifier = Modifier
                                    .constrainAs(text2) {
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                    })
                        }
                    }
                }
            }
            Row {
                Text(
                    text = stringResource(id = R.string.total_cost_footer),
                    Modifier.padding(top = 10.dp)
                )
                Text(
                    text = viewModel.totalCost.toString(),
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }

        }
    }
}