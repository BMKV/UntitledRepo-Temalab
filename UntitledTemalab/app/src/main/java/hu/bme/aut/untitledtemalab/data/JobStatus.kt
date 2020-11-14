package hu.bme.aut.untitledtemalab.data

enum class JobStatus {
    Pending, Accepted, PickedUp, Delivered, Expired;

    companion object{
        fun getActiveStatuses(): List<JobStatus>{
            return mutableListOf<JobStatus>().apply{
                add(Pending)
                add(Accepted)
                add(PickedUp)
            }
        }
    }
}