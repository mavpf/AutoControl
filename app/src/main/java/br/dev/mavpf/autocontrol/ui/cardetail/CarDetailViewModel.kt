package br.dev.mavpf.autocontrol.ui.cardetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.types.CostType
import br.dev.mavpf.autocontrol.data.types.FuelConsumption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel(){

    var totalCost = 0.00

    fun selectCar(licencePlate: String): LiveData<String> {
        return  databaseDao.selectCar(licencePlate)
    }

    val datasetConsump = listOf(
        FuelConsumption("Geral", 10.0),
        FuelConsumption("Gasolina", 10.0),
        FuelConsumption("Alcool", 12.0),
        FuelConsumption("Diesel", 85.0)
    )
    val datasetCost = listOf(
        CostType("Combustível", 700.00),
        CostType("Seguro", 2300.00),
        CostType("Serviços", 2000.00)
    )

    fun getConsumptionAverages(): List<FuelConsumption> {
        return datasetConsump
    }

    fun getCosts(): List<CostType>{
        for (cost in datasetCost) {
            totalCost = totalCost.plus(cost.costValue)
        }
        return datasetCost
    }

}