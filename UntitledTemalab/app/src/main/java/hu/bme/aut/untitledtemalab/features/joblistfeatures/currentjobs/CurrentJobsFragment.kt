package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.features.joblistfeatures.common.CommonJobDataAdapter
import kotlinx.android.synthetic.main.fragment_current_jobs.*


/**
 * A simple [Fragment] subclass.
 * Use the [CurrentJobsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentJobsFragment : Fragment() {

    private lateinit var viewModel: CurrentJobsViewModel

    private lateinit var adapter: CommonJobDataAdapter

    companion object {
        private const val ERROR_EMPTY_MESSAGE = "Both received data and error is null!"
        private const val ERROR_INVALID_JOB_TYPE =
            "Invalid use-type argument was given to CurrentJobsFragment instance!"
        private const val JOB_TYPE_KEY = "JOB_TYPE_KEY"
        private const val USER_ID_KEY = "USER_ID_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CurrentJobsFragment.
         */
        @JvmStatic
        fun newInstance(jobType: RepresentedJobType, userId: Long) = CurrentJobsFragment().apply {
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
        return inflater.inflate(R.layout.fragment_current_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            requireArguments().getLong(USER_ID_KEY).let { userId ->
                initializeViewModel(userId)
                initializeRecyclerViewAdapter(userId)
            }
            observeViewModelData()
            setFloatingActionButtonBehaviour()
        } catch (exception: Exception) {
            handleError(exception)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshJobsLiveData()
    }

    private fun initializeRecyclerViewAdapter(userId: Long) {
        adapter = CommonJobDataAdapter { jobId ->
            CurrentJobsContainerFragmentDirections.actionCurrentJobsShowJobDetails(
                jobId = jobId,
                userId = userId
            ).let { action ->
                findNavController().navigate(action)
            }
        }
        rvCurrentJobs.adapter = adapter
        rvCurrentJobs.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initializeViewModel(userId: Long) {
        viewModel = ViewModelProvider(
            this, CurrentJobsViewModelFactory(
                requireActivity().application, userId,
                when (requireArguments().getString(JOB_TYPE_KEY)) {
                    RepresentedJobType.AnnouncedJob.name -> CurrentJobsViewModel.CurrentJobsViewModelUseType.Announced
                    RepresentedJobType.AcceptedJob.name -> CurrentJobsViewModel.CurrentJobsViewModelUseType.Accepted
                    else -> {
                        Log.e("Freelancer", ERROR_INVALID_JOB_TYPE)
                        throw IllegalArgumentException(ERROR_INVALID_JOB_TYPE)
                    }
                },
            )
        ).get(CurrentJobsViewModel::class.java)
    }

    private fun observeViewModelData() {
        viewModel.currentJobsDataResponse.observe(viewLifecycleOwner) { currentJobsDataResponse ->
            when {
                currentJobsDataResponse.error is Exception -> handleError(currentJobsDataResponse.error)
                currentJobsDataResponse.jobData !is List<JobData> -> handleError(
                    IllegalStateException(ERROR_EMPTY_MESSAGE)
                )
                else -> adapter.setJobData(currentJobsDataResponse.jobData)
            }
        }
    }

    private fun setFloatingActionButtonBehaviour() {
        when (requireArguments().getString(JOB_TYPE_KEY)) {
            RepresentedJobType.AnnouncedJob.name -> fab.setOnClickListener {
                CurrentJobsContainerFragmentDirections.actionCurrentJobsOpenPostJob()
                    .let { action ->
                        findNavController().navigate(action)
                    }
            }
            RepresentedJobType.AcceptedJob.name -> fab.setOnClickListener {
                CurrentJobsContainerFragmentDirections.actionCurrentJobsFragmentToJobBoardFragment()
                    .let { action ->
                        findNavController().navigate(action)
                    }
            }
            else -> {
                Log.e("Freelancer", ERROR_INVALID_JOB_TYPE)
                throw IllegalArgumentException(ERROR_INVALID_JOB_TYPE)
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
        AnnouncedJob, AcceptedJob
    }
}