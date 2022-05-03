package br.dev.mavpf.autocontrol.ui.gasadd

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.GasTypes
import kotlinx.coroutines.launch

@Composable
fun gasAddView(): Boolean {

    val viewModel = hiltViewModel<GasAddViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var gasName by remember { mutableStateOf("") }
    var gasOctanes by remember { mutableStateOf("") }
    var gasObs by remember { mutableStateOf("") }

    var buttonReturn by remember {mutableStateOf(true)}

    fun checkValues():Boolean{
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

    suspend fun addValues(){
        if (checkValues()) {
            val dataset = GasTypes(
                gasName,
                gasOctanes.toInt(),
                gasObs
            )

            if (viewModel.insertGas(dataset)) {
                buttonReturn = false
            } else {
                Toast.makeText(
                    context,
                    R.string.gas_name_used,
                    Toast.LENGTH_LONG
                ).show()
                buttonReturn = true
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(.65f)
    ) {
        Column(modifier = Modifier
            .padding(5.dp)) {

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
                label = { Text(stringResource(id = R.string.gas_octanes))
                }
            )

            OutlinedTextField(
                value = gasObs,
                onValueChange = { gasObs = it },
                label = { Text(text = stringResource(id = R.string.gas_obs)) })
            Row(modifier = Modifier
                .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                Button(
                    onClick = { buttonReturn = false },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Filled.Cancel,
                        contentDescription = stringResource(id = R.string.cancel_button),
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.cancel_button))
                }

                Button(
                    onClick = {
                        coroutineScope.launch {
                                  addValues()
                              }
                    },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        Icons.Filled.Save,
                        contentDescription = stringResource(id = R.string.save_button),
                        modifier = Modifier
                            .size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.save_button))
                }
            }
        }

    }
    return buttonReturn
}