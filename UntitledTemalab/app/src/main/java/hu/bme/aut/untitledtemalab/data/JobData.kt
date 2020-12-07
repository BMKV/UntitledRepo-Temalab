package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

//Mark as "data class"?
class JobData(
    @SerializedName("job-id") val jobId: Long,
    @SerializedName("name") val jobName: String,
    @SerializedName("size") val size: PackageSize,
    @SerializedName("payment") val payment: Int,
    @SerializedName("jobIssuedDate") val jobIssuedDate: String,
    @SerializedName("deadline") val deadline: String,
    @SerializedName("sender-id") val ownerID: Long,
    @SerializedName("status") var status: JobStatus
) {
    //Tagváltozók
    @SerializedName("senderRating") var senderRating: Long? = null
    @SerializedName("deliveryDate") var deliveryDate: String? = null
    @SerializedName("deliveryRoute") var deliveryRoute: RouteData? = null

    //@SerializedName("")
    var listingExpirationDate: String? = null

    //Tagfüggvények
}