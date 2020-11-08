package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.CommonJobDataViewHolder
import kotlinx.android.synthetic.main.element_job_detail.view.*

/**
 * [RecyclerView.Adapter] implementation.
 */
class HistoryAdapter(val userId: Int):
    RecyclerView.Adapter<CommonJobDataViewHolder>() {

    /**
     * This property stores the JobData instances, that are the basis of the represented data.
     */
    private var historyList = emptyList<JobData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonJobDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_job_detail, parent,
            false)
        return CommonJobDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommonJobDataViewHolder, position: Int) {
        val history = historyList[position]

        holder.ivSize.setImageResource(R.mipmap.ic_launcher)
        holder.tvHistoryDetail.text = history.jobName
        holder.cardView.setOnClickListener{view ->
            HistoryContainerFragmentDirections.actionHistoryContainerShowJobDetails(
                jobId = historyList[position].jobId, userId = userId
            )
                .let{action -> view!!.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    /**
     * This function sets the list of data, which is represented by the adapter instance's
     * RecyclerView.
     * @param histories contains JobData instances in a List, and this List will be the new basis
     * of the represented data by the RecyclerView.
     */
    fun setHistories(histories: List<JobData>){
        this.historyList = histories
        notifyDataSetChanged()
    }

}