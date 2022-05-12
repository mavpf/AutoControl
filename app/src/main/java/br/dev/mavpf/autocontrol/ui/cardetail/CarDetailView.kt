package br.dev.mavpf.autocontrol.ui.cardetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.ui.carselect.MenuOpen

@Composable
fun CarDetailView(navRoutes: NavHostController, licencePlate: String) {
    val viewModel = hiltViewModel<CarDetailViewModel>()
    val dataset = listOf(
        listOf("Geral", 10),
        listOf("Gasolina", 10),
        listOf("Alcool", 12),
        listOf("Diesel", 75)
    )
    val datasetCost = listOf(
        listOf("Combustível", 700.00),
        listOf("Seguro", 2300.00),
        listOf("Serviços", 2000.00)
    )
    val model: String by viewModel.selectCar(licencePlate).observeAsState(String())

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text(text = "$model $licencePlate") },
            actions = {
                MenuOpen(navRoutes)
            }
        )

        CardConsumption(dataset)
        CardTotalCosts(datasetCost, viewModel)

    }


}

@Composable
fun CardConsumption(consumptionDataset: List<List<Any>>) {
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
                    items(consumptionDataset) { index ->
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
                                Text(text = index[0].toString())
                            }
                            Row(Modifier.align(CenterHorizontally)) {
                                Text(text = index[1].toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardTotalCosts(costsDataset: List<List<Any>>, viewModel: CarDetailViewModel){

    Card(
        modifier = Modifier
            .padding(5.dp)
            .shadow(15.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column() {


            Row() {
                Text(text = stringResource(id = R.string.total_cost_header),
                modifier = Modifier.padding(bottom = 10.dp))
            }
            Row() {


                LazyColumn {
                    var tempCost = 0.00
                    items(costsDataset) { index ->
                        ConstraintLayout(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                        ) {
                            val (text1, text2) = createRefs()

                            Text(text = index[0].toString(),
                                modifier = Modifier
                                    .constrainAs(text1) {
                                        start.linkTo(parent.start)
                                        top.linkTo(parent.top)
                                    })
                            Text(text = index[1].toString(),
                                modifier = Modifier
                                    .constrainAs(text2) {
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                    })
                            tempCost = tempCost.plus(index[1] as Double)
                        }
                        viewModel.totalCost = tempCost
                    }
                }
            }
            Row() {
                Text(text = stringResource(id = R.string.total_cost_footer), Modifier.padding(top = 10.dp))
                Text(text = viewModel.totalCost.toString(),
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.End)
            }

        }
    }
}