package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

class CargoData(
    @SerializedName("cargoFreeSize") val freeSize: Int,
    @SerializedName("cargoMaxSize") val maxSize: Int
)