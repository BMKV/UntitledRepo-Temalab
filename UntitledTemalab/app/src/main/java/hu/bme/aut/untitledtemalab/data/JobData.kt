package hu.bme.aut.untitledtemalab.data

//Mark as "data class"?
class JobData (val jobId: Int,
               val packageSize: PackageSize,
               val payment: Int,
               val jobIssuedDate: String,
               val deadline: String){
    //Tagváltozók
    var status: JobStatus = JobStatus.pending
    var senderRating: Int? = null
    var deliveryDate: String? = null
    var deliveryRoute: RouteData? = null

    //Tagfüggvények
}