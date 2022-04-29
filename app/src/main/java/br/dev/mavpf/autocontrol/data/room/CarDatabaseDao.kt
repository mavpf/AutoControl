package br.dev.mavpf.autocontrol.data.room

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.jvm.Throws

@Dao
interface CarDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLiteException::class)
    suspend fun insertCar(vararg: Cars)

    @Query("select * from cars")
    fun selectCars(): LiveData<List<Cars>>
}