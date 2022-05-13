package br.dev.mavpf.autocontrol.ui.gasdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.FuelTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GasDetailViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel() {
    fun getGas(): LiveData<List<FuelTypes>> {
        return databaseDao.selectGas()
    }
}