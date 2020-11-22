package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import android.content.Intent
import android.net.wifi.WifiManager
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

    companion object {
        private const val ERROR_EMPTY_MESSAGE = "Both received data and error is null!"
        private const val ERROR_INVALID_JOB_TYPE =
            "Invalid use-type argument was given to CurrentJobsFragment instance!"

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            initializeViewModel()
        }
        catch(exception: Exception){
            handleError(exception)
        }
        initializeRecyclerViewAdapter()
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshJobsLiveData()
    }

    private fun initializeRecyclerViewAdapter() {
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
    }

    private fun initializeViewModel() {
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
                    else -> throw IllegalStateException(ERROR_INVALID_JOB_TYPE)
                }
            )
        ).get(JobBoardFragmentViewModel::class.java)
    }

    private fun observeViewModelData() {
        viewModel.availableJobs.observe(viewLifecycleOwner) { jobDataResponse ->
            when {
                jobDataResponse.error is Exception -> handleError(jobDataResponse.error)
                jobDataResponse.jobData !is List<JobData> -> handleError(IllegalStateException(
                    ERROR_EMPTY_MESSAGE))
                else -> jobsAdapter.setJobData(jobDataResponse.jobData)
            }
        }
    }

    private fun handleError(error: Exception) {
        Log.i("Freelancer", error.localizedMessage ?: "Error without message happened!")
        when (error.message) {
            ERROR_INVALID_JOB_TYPE -> Snackbar.make(
                requireView(),
                getString(R.string.application_error),
                Snackbar.LENGTH_SHORT
            ).show()
            ERROR_EMPTY_MESSAGE -> Snackbar.make(
                requireView(),
                getString(R.string.server_error),
                Snackbar.LENGTH_SHORT
            ).show()
            //Network error happened
            else -> Snackbar.make(
                this.requireView(), getString(R.string.network_error),
                Snackbar.LENGTH_SHORT
            )
                .setAction(getString(R.string.open_wifi_settings)) {
                    startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
                }.show()
        }
    }

    enum class RepresentedJobType {
        SmallSize, MediumSize, LargeSize
    }
}