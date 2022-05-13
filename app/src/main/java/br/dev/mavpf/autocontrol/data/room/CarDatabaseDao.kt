package br.dev.mavpf.autocontrol.data.room

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLiteException::class)
    suspend fun insertCar(vararg: Cars)

    @Query("select * from cars")
    fun selectCars(): LiveData<List<Cars>>

    @Query("select* from fueltypes")
    fun selectGas(): LiveData<List<FuelTypes>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    @Throws(SQLiteException::class)
    suspend fun insertGas(vararg: FuelTypes)

    @Delete(entity = FuelTypes::class)
    @Throws(SQLiteException::class)
    suspend fun deleteGasType(vararg: FuelTypes)

    @Update (entity = FuelTypes::class)
    suspend fun updateGasType(vararg: FuelTypes)

    @Query("select model from cars where licenceplate = :licencePlate")
    fun selectCar(licencePlate: String): LiveData<String>
}