package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

//Mark as "data class"?
class JobData(
    @SerializedName("job-id")
    val jobId: Long,
    val jobName: String,
    val size: PackageSize,
    val payment: Int,
    val jobIssuedDate: String,
    val deadline: String,
    @SerializedName("sender-id")
    val ownerID: Long,
    var status: JobStatus
) {
    //Tagváltozók
    // var status: JobStatus = JobStatus.Pending
    // TODO: Ezt a senderRating-et miért tettem ide? ez a hozzá tartozó User-ben van
    // var senderRating: Int? = null
    var deliveryDate: String? = null
    var deliveryRoute: RouteData? = null
    var listingExpirationDate: String? = null

    //Tagfüggvények
}