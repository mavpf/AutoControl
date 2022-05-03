package br.dev.mavpf.autocontrol.ui.gasdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.GasTypes

@Composable
fun GasDetailView(navRoutes: NavHostController){
    
    val viewModel = hiltViewModel<GasDetailViewModel>()
    val rememberListState = rememberLazyListState()
    val lazyPagingItems: List<GasTypes> by viewModel.getGas().observeAsState(listOf())

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
                    Text(text = "nada")
                }
            } else {
                items(items = lazyPagingItems) {
                    Text(text = it.gasname)
                }
            }
        }
    }
}