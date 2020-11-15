package hu.bme.aut.untitledtemalab.data

enum class JobStatus {
    Pending {
        override fun getBackendValueName(): String {
            return "pending"
        }
    }, Accepted {
        override fun getBackendValueName(): String {
            return "accepted"
        }
    }, PickedUp {
        override fun getBackendValueName(): String {
            return "pickedUp"
        }
    }, Delivered {
        override fun getBackendValueName(): String {
            return "delivered"
        }
    }, Expired {
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