package br.dev.mavpf.autocontrol.ui.caradd

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.dev.mavpf.autocontrol.R
import br.dev.mavpf.autocontrol.data.room.Cars
import kotlinx.coroutines.launch

@Composable
fun carAddView(): MutableState<Boolean> {

    val carAddViewModel = hiltViewModel<CarAddViewModel>()

    val context = LocalContext.current
    val errorMessage = stringResource(id = R.string.save_error)
    val coroutineScope = rememberCoroutineScope()
    
    val buttonReturn = remember { mutableStateOf(true) }

    var textMaker by remember { mutableStateOf("") }
    var textModel by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf("") }
    var textYear by remember { mutableStateOf("") }
    var textMileage by remember { mutableStateOf("") }
    var textLicenceplate by remember { mutableStateOf("") }


    fun checkValues(): Boolean{
        return when {
            textColor.isBlank() -> {
                false
            }
            textMaker.isBlank() -> {
                false
            }
            textLicenceplate.isBlank() -> {
                false
            }
            textModel.isBlank() -> {
                false
            }
            textMileage.isBlank() || textMileage.toIntOrNull()?.let { false } ?: true -> {
                false
            }
            textYear.isBlank() || textYear.toIntOrNull()?.let{false} ?: true -> {
                false
            }
            else -> {true}
        }
    }

    suspend fun addCarValues() {

        if (checkValues()) {

            val dataset = Cars(
                textLicenceplate,
                textMaker,
                textModel,
                textColor,
                textYear.toInt(),
                textMileage.toInt()
            )

            if (carAddViewModel.insertCar(dataset)) {
                buttonReturn.value = false
            } else {
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_LONG
                ).show()
                buttonReturn.value = true
            }
        } else {
            Toast.makeText(context, R.string.field_error, Toast.LENGTH_SHORT).show()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight(.7f)
    ) {


        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {


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
                    text = stringResource(id = R.string.add_header),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                OutlinedTextField(
                    value = textMaker,
                    onValueChange = {
                        textMaker = it
                    },
                    label = { Text(stringResource(R.string.maker_field)) },
                    modifier = Modifier
                        .weight(1f)
                )

                OutlinedTextField(
                    value = textModel,
                    onValueChange = {
                        textModel = it
                    },
                    label = { Text(stringResource(R.string.model_field)) },
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                OutlinedTextField(
                    value = textColor,
                    onValueChange = {
                        textColor = it
                    },
                    label = { Text(stringResource(R.string.color_field)) },
                    modifier = Modifier
                        .weight(1f)
                )
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    value = textYear,
                    onValueChange = {
                        textYear = when (it.toIntOrNull()){
                            null -> textYear
                            else -> it
                        }
                    },
                    label = { Text(stringResource(R.string.year_field)) },
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    value = textMileage,
                    onValueChange = {
                        textMileage = when(it.toIntOrNull()){
                            null -> textMileage
                            else -> it
                        }
                    },
                    label = { Text(stringResource(R.string.mileage_field)) },
                    modifier = Modifier
                        .weight(1f)
                )
                OutlinedTextField(
                    value = textLicenceplate,
                    onValueChange = {
                        textLicenceplate = it
                    },
                    label = { Text(stringResource(R.string.licenplace_field)) },
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .padding(15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Button(
                    onClick = { buttonReturn.value = false },
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
                            addCarValues()
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


