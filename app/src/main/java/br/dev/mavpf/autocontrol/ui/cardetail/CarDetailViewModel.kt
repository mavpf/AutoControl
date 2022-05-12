package br.dev.mavpf.autocontrol.ui.cardetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel(){

    var totalCost = 0.00

    fun selectCar(licencePlate: String): LiveData<String> {
        return  databaseDao.selectCar(licencePlate)
    }


}