package br.dev.mavpf.autocontrol.data.types

data class FuelConsumption(
    val fuelType: String,
    val fuelAverage: Double
)

data class CostType(
    val costName: String,
    val costValue: Double
)

