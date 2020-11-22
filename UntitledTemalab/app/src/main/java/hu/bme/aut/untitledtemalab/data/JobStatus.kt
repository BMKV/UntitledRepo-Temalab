package hu.bme.aut.untitledtemalab.data

import com.google.gson.annotations.SerializedName

enum class JobStatus {
    @SerializedName("pending")
    Pending {
        override fun getBackendValueName(): String {
            return "pending"
        }
    },
    @SerializedName("accepted")
    Accepted {
        override fun getBackendValueName(): String {
            return "accepted"
        }
    },
    @SerializedName("pickedUp")
    PickedUp {
        override fun getBackendValueName(): String {
            return "pickedUp"
        }
    },
    @SerializedName("delivered")
    Delivered {
        override fun getBackendValueName(): String {
            return "delivered"
        }
    },
    @SerializedName("expired")
    Expired {
        override fun getBackendValueName(): String {
            TODO("Ez nincs benne az API leírásban:)")
        }
    };



    companion object{
        fun getActiveStatuses(): List<JobStatus>{
            return mutableListOf<JobStatus>().apply{
                add(Pending)
                add(Accepted)
                add(PickedUp)
            }
        }
    }

    abstract fun getBackendValueName(): String
}