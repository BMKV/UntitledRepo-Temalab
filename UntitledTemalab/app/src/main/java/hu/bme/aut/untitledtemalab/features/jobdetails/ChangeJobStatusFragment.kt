package hu.bme.aut.untitledtemalab.features.jobdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobStatus
import kotlinx.android.synthetic.main.fragment_change_job_status.*

class ChangeJobStatusFragment :Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_job_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentJobDetails = parentFragment as JobDetailsFragment

        val btnCancel = btnCancel
        val btnPickUp = btnPickUp
        val btnComplete = btnComplete

        btnCancel.setOnClickListener {
            parentJobDetails.cancelJob()
        }

        btnPickUp.setOnClickListener {
            parentJobDetails.pickUpPackage()
            btnCancel.isEnabled = false
            btnPickUp.isEnabled = false
            btnComplete.isEnabled = true
        }
        
        btnComplete.setOnClickListener {
            parentJobDetails.completeJob()
        }

        if (parentJobDetails.getJobShown().status == JobStatus.accepted) {
            btnComplete.isEnabled = false
        }
        else if (parentJobDetails.getJobShown().status == JobStatus.pickedUp) {
            btnCancel.isEnabled = false
            btnPickUp.isEnabled = false
        }
    }
}