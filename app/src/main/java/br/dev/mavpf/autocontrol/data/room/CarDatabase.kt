package br.dev.mavpf.autocontrol.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Cars::class],
    version = 1,
    exportSchema = false
)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDatabaseDao(): CarDatabaseDao
}