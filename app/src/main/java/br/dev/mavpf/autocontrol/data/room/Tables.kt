package br.dev.mavpf.autocontrol.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cars"
)
data class Cars(
    @ColumnInfo(name = "licenceplate") @PrimaryKey val licenceplate: String,
    @ColumnInfo(name = "maker" ) val maker: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "mileage") val mileage: Int
)