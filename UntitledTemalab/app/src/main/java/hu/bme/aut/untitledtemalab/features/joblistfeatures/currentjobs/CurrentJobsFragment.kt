package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.untitledtemalab.R

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentJobsFragment.newInstance] factory method to
 * create an instance of this fragment.
 * TODO this will be rewritten
 */
class CurrentJobsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO creating the ViewModel
    }

    companion object {

        private const val JOB_TYPE_KEY = "HISTORY_TYPE_KEY"

        private const val USER_ID_KEY = "USER_ID_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CurrentJobsFragment.
         */
        @JvmStatic
        fun newInstance(jobType: RepresentedJobType, userId: Int) = CurrentJobsFragment().apply {
            arguments = Bundle().apply {
                putString(JOB_TYPE_KEY, when(jobType){
                    RepresentedJobType.AcceptedJob -> RepresentedJobType.AcceptedJob.name
                    RepresentedJobType.AnnouncedJob -> RepresentedJobType.AnnouncedJob.name
                })
                putInt(USER_ID_KEY, userId)
            }
        }
    }

    enum class RepresentedJobType {
        AnnouncedJob, AcceptedJob
    }
}