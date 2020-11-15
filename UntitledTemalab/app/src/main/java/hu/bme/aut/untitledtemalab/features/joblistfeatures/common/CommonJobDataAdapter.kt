package hu.bme.aut.untitledtemalab.features.joblistfeatures.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData

class CommonJobDataAdapter(private val onItemClick: (jobId: Long) -> Unit) :
    RecyclerView.Adapter<CommonJobDataViewHolder>() {

    private var jobDataList = emptyList<JobData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonJobDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.element_job_detail, parent,
            false
        )
        return CommonJobDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonJobDataViewHolder, position: Int) {
        val jobData = jobDataList[position]

        holder.ivSize.setImageResource(R.mipmap.ic_launcher)
        holder.tvHistoryDetail.text = jobData.jobName
        holder.cardView.setOnClickListener {
            onItemClick(jobDataList[position].jobId)
        }
    }

    override fun getItemCount(): Int {
        return jobDataList.size
    }

    /**
     * TODO this comment is from previous concrete implementation, just copied here for reference.
     * This function sets the list of data, which is represented by the adapter instance's
     * RecyclerView.
     * @param freshDataList contains JobData instances in a List, and this List will be the new basis
     * of the represented data by the RecyclerView.
     */
    fun setJobData(freshDataList: List<JobData>){
        jobDataList = freshDataList
        notifyDataSetChanged()
    }

}