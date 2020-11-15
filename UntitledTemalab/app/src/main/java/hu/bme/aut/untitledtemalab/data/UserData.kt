package hu.bme.aut.untitledtemalab.data

//Mark as "data class"?
class UserData(val userId: Long,
               var userName: String,
               var email: String,
               var rating: Double) {
    //Tagváltozók
    var canDeliver = false
    var cargoFreeSize: Int? = null
    var cargoCapacity: Int? = null

    //Tagfüggvények

}