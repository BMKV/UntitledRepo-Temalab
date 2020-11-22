package hu.bme.aut.untitledtemalab.features.joblistfeatures.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.element_job_detail.view.*

class CommonJobDataViewHolder(jobDataView: View) : RecyclerView.ViewHolder(jobDataView) {
    val cardView: CardView = jobDataView.cardView
    val ivSize: ImageView = jobDataView.ivSize
    val tvJobName: TextView = jobDataView.cardView.tvJobName
    val tvJobStatus: TextView = jobDataView.cardView.tvJobStatus
    val tvJobDeadline: TextView = jobDataView.cardView.tvJobDeadline
}