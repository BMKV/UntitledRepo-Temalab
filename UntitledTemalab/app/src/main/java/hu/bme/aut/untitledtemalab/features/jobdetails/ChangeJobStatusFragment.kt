package hu.bme.aut.untitledtemalab.features.jobdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.untitledtemalab.R
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

        val btnCancel = btnCancel
        btnCancel.setOnClickListener {
            (parentFragment as JobDetailsFragment).cancelJob()
        }

        val btnPickUp = btnPickUp
        btnPickUp.setOnClickListener {
            (parentFragment as JobDetailsFragment).pickUpPackage()
            btnCancel.isEnabled = false
            btnPickUp.isEnabled = false
        }

        val btnComplete = btnComplete
        btnComplete.setOnClickListener {
            (parentFragment as JobDetailsFragment).completeJob()
        }
    }
}