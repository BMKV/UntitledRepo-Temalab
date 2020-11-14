package hu.bme.aut.untitledtemalab.features.jobdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobStatus
import kotlinx.android.synthetic.main.fragment_job_information.*

class JobInformationFragment :Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_job_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvText = tvJobNotAvailableInfo

        if ((parentFragment as JobDetailsFragment).getJobShown().status == JobStatus.delivered) {
            tvText.text = getString(R.string.job_completed_info)
        }
        else if ((parentFragment as JobDetailsFragment).getJobShown().status == JobStatus.expired) {
            tvText.text = getString(R.string.job_expired_info)
        }

    }
}