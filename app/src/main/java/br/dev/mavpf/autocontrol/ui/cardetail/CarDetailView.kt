package br.dev.mavpf.autocontrol.ui.cardetail

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.dev.mavpf.autocontrol.R

@Composable
fun CarDetailView (navRoutes: NavHostController, licencePlate: String) {
    val dataset = listOf(listOf("Geral", 10), listOf("Gasolina", 10), listOf("Alcool", 12), listOf("Diesel",75))
    Column() {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .shadow(15.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            ConstraintLayout() {
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
                            .wrapContentSize()) {
                        items(dataset) { index ->
                            Column(
                                Modifier
                                    .wrapContentWidth()
                                    .padding(5.dp)
                            ) {
                                Row(
                                    Modifier
                                        .align(CenterHorizontally)
                                        .wrapContentWidth()) {
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
}