package br.dev.mavpf.autocontrol.data.room

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.*
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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLiteException::class)
    suspend fun insertGas(vararg: GasTypes)

    @Query("select* from gastypes")
    fun selectGas(): LiveData<List<GasTypes>>

    @Delete(entity = GasTypes::class)
    suspend fun deleteGasType(vararg: GasTypes)

    @Update (entity = GasTypes::class)
    suspend fun updateGasType(vararg: GasTypes)

    @Query("select model from cars where licenceplate = :licencePlate")
    fun selectCar(licencePlate: String): LiveData<String>
}