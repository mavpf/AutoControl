package br.dev.mavpf.autocontrol.ui.carselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarSelectViewModel @Inject constructor(
    private val provideCarDatabaseDao: CarDatabaseDao
): ViewModel() {
    val teste = "VM SEL TESTE"

    fun getCars(): LiveData<List<Cars>> {
        return provideCarDatabaseDao.selectCars()
    }
}