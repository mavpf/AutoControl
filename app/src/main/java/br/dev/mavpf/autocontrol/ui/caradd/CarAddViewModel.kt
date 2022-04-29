package br.dev.mavpf.autocontrol.ui.caradd

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.Cars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarAddViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
) : ViewModel() {
    suspend fun insertCar(dataset: Cars): Boolean {


        return try {
            databaseDao.insertCar(dataset)
            true
        } catch (e: SQLiteException) {
            false
        }
    }

}