package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

class UserRoleStatisticsData(
    @SerializedName("allUsers") val userCount: Int,
    @SerializedName("allSenders") val senderCount: Int,
    @SerializedName("allDeliverers") val delivererCount: Int
)