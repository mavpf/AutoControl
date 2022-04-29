package br.dev.mavpf.autocontrol.ui.cardetail

import androidx.lifecycle.ViewModel
import br.dev.mavpf.autocontrol.data.room.CarDatabaseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    private val databaseDao: CarDatabaseDao
): ViewModel(){

}