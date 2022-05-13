package br.dev.mavpf.autocontrol.ui.gasadd

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.FuelTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GasCRUDViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel() {
    suspend fun insertGas(dataset: FuelTypes): Boolean{
        return try {
                databaseDao.insertGas(dataset)
                true
        } catch (e: SQLiteException) {
            false
        }
    }

    suspend fun deleteGas(dataset: FuelTypes): Boolean {
        return try {
            databaseDao.deleteGasType(dataset)
            true
        } catch (e: SQLiteException){
            false
        }

    }

    suspend fun updateGas(dataset: FuelTypes): Boolean{
        return try {
            databaseDao.updateGasType(dataset)
            true
        } catch (e: SQLiteException){
            false
        }
    }
}