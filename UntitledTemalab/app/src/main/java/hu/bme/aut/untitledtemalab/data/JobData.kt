package hu.bme.aut.untitledtemalab.data

//Mark as "data class"?
class JobData (val jobId: Int,
               val jobName: String,
               val packageSize: PackageSize,
               val payment: Int,
               val jobIssuedDate: String,
               val deadline: String,
               val ownerID: Int){
    //Tagváltozók
    var status: JobStatus = JobStatus.pending
    var senderRating: Int? = null
    var deliveryDate: String? = null
    var deliveryRoute: RouteData? = null
    var listingExpirationDate: String? = null

    //Tagfüggvények
}