package hu.bme.aut.untitledtemalab.data

import androidx.navigation.ActivityNavigator
import com.google.gson.annotations.SerializedName

class JobRegistrationData (
    @SerializedName("size") val size: PackageSize,
    @SerializedName("name") val jobName: String,
    @SerializedName("payment") val payment: Int,
    @SerializedName("jobIssuedDate") val jobIssuedDate: String,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("startLocation") val startLocation: String,
    @SerializedName("destination") val destination: String
     ) {

}