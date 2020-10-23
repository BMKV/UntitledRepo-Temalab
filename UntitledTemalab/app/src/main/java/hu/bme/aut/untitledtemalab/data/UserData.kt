package hu.bme.aut.untitledtemalab.data

class UserData(val userId: Int,
               var email: String,
               var rating: Float) {
    //Tagváltozók
    var canDeliver = false
    var cargoFreeSize: Int? = null
    var cargoCapacity: Int? = null

    //Tagfüggvények

}