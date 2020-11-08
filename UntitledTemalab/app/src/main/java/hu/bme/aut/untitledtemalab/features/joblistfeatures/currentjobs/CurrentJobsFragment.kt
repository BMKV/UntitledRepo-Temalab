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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO retrieving parameter from args
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_jobs, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CurrentJobsFragment.
         */
        @JvmStatic
        fun newInstance() = CurrentJobsFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}