package br.dev.mavpf.autocontrol.ui.gasadd


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.GasTypes
import kotlinx.coroutines.launch

@Composable
fun gasCRUDView(crud: String, gasValue: GasTypes): Boolean {

    val viewModel = hiltViewModel<GasCRUDViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var gasName by remember { mutableStateOf("") }
    var gasOctanes by remember { mutableStateOf("") }
    var gasObs by remember { mutableStateOf("") }

    var buttonReturn by remember { mutableStateOf(true) }

    var firstButton = Icons.Filled.Cancel
    var firstButtonDesc = stringResource(id = R.string.cancel_button)
    var secondButton = Icons.Filled.Save
    var secondButtonDesc = stringResource(id = R.string.save_button)

    fun checkValues(): Boolean {
        return when {
            gasName.isBlank() -> {
                Toast.makeText(context, R.string.save_gas_error, Toast.LENGTH_SHORT).show()
                false
            }
            gasOctanes.toIntOrNull()?.let { false } ?: true -> {
                Toast.makeText(context, R.string.save_gasoctanes_error, Toast.LENGTH_SHORT).show()
                false
            }
            else -> {
                true
            }
        }
    }

    suspend fun crudValues(crudOp: String) {
        if (checkValues()) {
            val dataset = GasTypes(
                gasName,
                gasOctanes.toInt(),
                gasObs
            )
            when (crudOp) {
                "insert" -> {
                    buttonReturn = if (viewModel.insertGas(dataset)) {
                        false
                    } else {
                        Toast.makeText(
                            context,
                            R.string.gas_name_used,
                            Toast.LENGTH_LONG
                        ).show()
                        true
                    }
                }
                "update" -> {
                    viewModel.updateGas( GasTypes(
                        gasName,
                    gasOctanes.toInt(),
                        gasObs
                    ))
                    buttonReturn = false
                }
                "delete" -> {
                    viewModel.deleteGas(gasValue)
                    buttonReturn = false
                }
            }
        } else {
            Toast.makeText(context, "Erro desconhecido", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectGasDetails() {
        gasName = gasValue.gasname
        gasOctanes = gasValue.octanes.toString()
        gasObs = gasValue.obs
    }

    fun firstButtonFunction() {
        if (crud == "insert") {
            buttonReturn = false
        } else {
            coroutineScope.launch {
                crudValues("update")
            }
        }
    }

    fun secondButtonFunction() {
        if (crud == "insert") {
            coroutineScope.launch {
                crudValues(crud)
            }
        } else {
            coroutineScope.launch {
                crudValues("delete")
            }
        }
    }

    if (crud != "insert") {
        firstButton = Icons.Filled.Update
        firstButtonDesc = stringResource(id = R.string.update_button)
        secondButton = Icons.Filled.Delete
        secondButtonDesc = stringResource(id = R.string.delete_button)
        selectGasDetails()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(.65f)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {

            Row(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(MaterialTheme.colors.primary, MaterialTheme.colors.secondary)
                        )
                    )
                    .fillMaxWidth()
                    .height(35.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.gas_add_header),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            OutlinedTextField(
                value = gasName,
                onValueChange = { gasName = it },
                label = { Text(stringResource(R.string.gas_type)) },
            )

            OutlinedTextField(
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                value = gasOctanes,
                onValueChange = {
                    gasOctanes = when (it.toIntOrNull()) {
                        null -> gasOctanes
                        else -> it
                    }
                },
                label = {
                    Text(stringResource(id = R.string.gas_octanes))
                }
            )

            OutlinedTextField(
                value = gasObs,
                onValueChange = { gasObs = it },
                label = { Text(text = stringResource(id = R.string.gas_obs)) }
            )

            Row(
                modifier = Modifier
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Button(
                    onClick = { firstButtonFunction() },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        firstButton,
                        contentDescription = firstButtonDesc,
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = firstButtonDesc)
                }

                Button(
                    onClick = {
                        secondButtonFunction()
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        secondButton,
                        contentDescription = secondButtonDesc,
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = secondButtonDesc)
                }
            }
        }
    }



    return buttonReturn
}