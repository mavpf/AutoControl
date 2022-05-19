package br.dev.mavpf.autocontrol.ui.cardetail

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

enum class States {
    EXPANDED,
    COLLAPSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CarDetailView(navRoutes: NavHostController, licencePlate: String) {
    val viewModel = hiltViewModel<CarDetailViewModel>()

    val model: String by viewModel.selectCar(licencePlate).observeAsState(String())

    var multiFloatingState by remember { mutableStateOf(MultiFloatingState.Collapsed) }


    val items = listOf(
        MinFabItem(
            icon = Icons.Default.LocalGasStation ,
            label = stringResource(id = R.string.gas_add_icon),
            identifier = "GAS"
        ),
        MinFabItem(
            icon = Icons.Default.CarRepair,
            label = stringResource(id = R.string.service_add_icon),
            identifier = "SERVICES"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TopAppBar(
            title = { Text(text = "$model $licencePlate") },
            actions = {
            }
        )
        Scaffold(
            floatingActionButton = {
                MultiFloatingActionButton(
                    multiFloatingState = multiFloatingState,
                    onMultiFabStateChange = {
                        multiFloatingState = it
                    },
                    items = items
                )
            }
        ) {
            Column () {
                CardConsumption(viewModel)
                CardTotalCosts(viewModel, it)
            }
        }

    }
}

@Composable
private fun MultiFloatingActionButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    items: List<MinFabItem>
) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.Expanded) 315f else 0f
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {

        if (transition.currentState == MultiFloatingState.Expanded){
            items.forEach{ it ->
                MinFab(
                    item = it,
                    onMinFabItemClick = {
                        it.identifier
                    })
            }
        }
        FloatingActionButton(onClick = {
            onMultiFabStateChange(
                if (transition.currentState == MultiFloatingState.Expanded) {
                    MultiFloatingState.Collapsed
                } else {
                    MultiFloatingState.Expanded
                }
            )
        }
        ) {
            Icon(Default.Add, null, Modifier.rotate(rotate))
        }
    }
}

@Composable
private fun MinFab(
    item: MinFabItem,
    onMinFabItemClick: (MinFabItem) -> Unit
) {
    FloatingActionButton(onClick = { Log.d("ret", item.identifier ) },
        Modifier
            .size(50.dp)
            .padding(5.dp)) {
        Icon(item.icon, contentDescription = item.label)
    }
}

data class MinFabItem(
    val icon: ImageVector,
    val label: String,
    val identifier: String
)

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
private fun CardTotalCosts(viewModel: CarDetailViewModel, paddingValues: PaddingValues) {

    val dataset = viewModel.getCosts()
    Card(
        modifier = Modifier
            .padding(5.dp)
            .shadow(15.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        Column {


            Row (Modifier.padding(bottom = 10.dp)) {
                Text(
                    text = stringResource(id = R.string.total_cost_footer),
                    Modifier.padding(top = 10.dp)
                )
                Text(
                    text = viewModel.totalCost.toString(),
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                    ,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold
                )
            }
            Row {


                LazyColumn (contentPadding = paddingValues) {
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
        }
    }
}