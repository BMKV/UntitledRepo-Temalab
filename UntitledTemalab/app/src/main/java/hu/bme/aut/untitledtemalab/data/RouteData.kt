package hu.bme.aut.untitledtemalab.data

//Mark as "data class"?
class RouteData (val startLocation: String,
                 val destination: String) {
    //Tagváltozók
    var optimalCompletionTime: Int? = null //Calculated when the user accepts the job
    var actualCompletionTime: Int? = null //Calculated when the user delivered the package

    //Tagfüggvények
    fun calculateCompletionTime(timeOfAccept: String) {
        //Fügvény: a "Deliver" gomb megnoymásakor fut, lekéri az aktuális időt, ebből számítja az actualCompletionTime-t
    }
}