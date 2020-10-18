package hu.bme.aut.untitledtemalab.jobhistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.element_job_detail.view.*

class HistoryAdapter:
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var historyList = emptyList<JobHistoryDummyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_job_detail, parent,
            false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]

        //TODO dummy data is used, setting the viewHolder's view's content will change
        holder.ivSize.setImageResource(R.mipmap.ic_launcher)
        holder.tvHistoryDetail.text = history.exampleProperty
        holder.cardView.setOnClickListener{
            TODO("Starting the proper Activity. This should be made with Navigation Drawer.")
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(historyView: View) : RecyclerView.ViewHolder(historyView){
        val cardView: CardView = historyView.cardView
        val ivSize: ImageView = historyView.ivSize
        val tvHistoryDetail: TextView = historyView.tvHistoryDetail
    }

    fun setHistories(histories: List<JobHistoryDummyModel>){
        this.historyList = histories
        notifyDataSetChanged()
    }

}