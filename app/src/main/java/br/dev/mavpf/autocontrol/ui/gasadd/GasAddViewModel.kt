package br.dev.mavpf.autocontrol.ui.gasadd

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import br.dev.mavpf.autocontrol.data.room.GasTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GasAddViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel() {
    suspend fun insertGas(dataset: GasTypes): Boolean{
        return try {
                databaseDao.insertGas(dataset)
                true
        } catch (e: SQLiteException) {
            false
        }
    }
}