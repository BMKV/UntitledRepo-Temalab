package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.untitledtemalab.R

/**
 * A simple [Fragment] subclass.
 * Use the [JobBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JobBoardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_board, container, false)
    }

    companion object {

        private const val USER_ID_KEY = "USER_ID_KEY"

        private const val JOB_TYPE_KEY = "JOB_TYPE_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment JobBoardFragment.
         */
        @JvmStatic
        fun newInstance(jobType: RepresentedJobType, userId: Int) =
            JobBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(JOB_TYPE_KEY, jobType.name)
                }
            }
    }

    enum class RepresentedJobType{
        SmallSize, MediumSize, LargeSize
    }
}