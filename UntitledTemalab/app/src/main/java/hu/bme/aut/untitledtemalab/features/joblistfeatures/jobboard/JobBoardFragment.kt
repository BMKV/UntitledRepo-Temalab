package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.CommonJobDataAdapter
import kotlinx.android.synthetic.main.fragment_job_board.*
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [JobBoardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JobBoardFragment : Fragment() {

    private lateinit var jobsAdapter: CommonJobDataAdapter

    private lateinit var viewModel: JobBoardFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_board, container, false)
    }

    //TODO it's not clean
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            JobBoardFragmentViewModelFactory(
                requireActivity().application,
                when (requireArguments().getString(
                    JOB_TYPE_KEY
                )) {
                    RepresentedJobType.SmallSize.name -> JobBoardFragmentViewModel.AvailableJobType.Small
                    RepresentedJobType.MediumSize.name -> JobBoardFragmentViewModel.AvailableJobType.Medium
                    RepresentedJobType.LargeSize.name -> JobBoardFragmentViewModel.AvailableJobType.Large
                    else -> throw IllegalStateException("Invalid size category value was passed to JobBoardFragment!")
                }
            )
        ).get(JobBoardFragmentViewModel::class.java)

        jobsAdapter = CommonJobDataAdapter { jobId ->
            JobBoardContainerFragmentDirections.actionJobBoardShowJobDetails(
                jobId = jobId, userId = requireArguments().getLong(
                    USER_ID_KEY
                )
            ).let { action ->
                findNavController().navigate(action)
            }
        }

        rvAvailableJobs.adapter = jobsAdapter
        rvAvailableJobs.layoutManager = LinearLayoutManager(requireContext())

        viewModel.availableJobs.observe(viewLifecycleOwner) { jobDataResponse ->
            when {
                jobDataResponse.error is Exception -> handleError(jobDataResponse.error)
                jobDataResponse.jobData !is List<JobData> -> handleError(IllegalStateException("Both received data and error is null!"))
                else -> jobsAdapter.setJobData(jobDataResponse.jobData)
            }
        }
    }

    private fun handleError(error: Exception) {
        Log.i("Freelancer", error.localizedMessage ?: "Unexpected error happened!")
        Snackbar.make(
            this.requireView(), error.localizedMessage ?: "Unexpected error happened!",
            Snackbar.LENGTH_SHORT
        ).show()
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
        fun newInstance(jobType: RepresentedJobType, userId: Long) =
            JobBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(JOB_TYPE_KEY, jobType.name)
                    putLong(USER_ID_KEY, userId)
                }
            }
    }

    enum class RepresentedJobType {
        SmallSize, MediumSize, LargeSize
    }
}