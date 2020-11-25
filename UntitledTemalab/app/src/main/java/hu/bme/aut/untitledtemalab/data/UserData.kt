package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

//Mark as "data class"?
class UserData(
    @SerializedName("user-id") val userId: Long,
    var userName: String,
    var email: String,
    var rating: Double) {
    //Tagváltozók
    var canDeliver = false
    var isAdmin = false
    var cargoFreeSize: Int? = null
    var cargoCapacity: Int? = null

    //Tagfüggvények

}