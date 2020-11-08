package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

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
import kotlinx.android.synthetic.main.fragment_current_jobs.*

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentJobsFragment.newInstance] factory method to
 * create an instance of this fragment.
 * TODO this will be rewritten
 */
class CurrentJobsFragment : Fragment() {

    private lateinit var viewModel: CurrentJobsViewModel

    private lateinit var adapter: CommonJobDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_jobs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO this is not clean, it will be refactored
        requireArguments().getInt(USER_ID_KEY).let { userId ->
            viewModel = ViewModelProvider(
                this, CurrentJobsViewModelFactory(
                    requireActivity().application, userId,
                    when (requireArguments().getString(JOB_TYPE_KEY)) {
                        RepresentedJobType.AnnouncedJob.name -> CurrentJobsViewModel.CurrentJobsViewModelUseType.Announced
                        RepresentedJobType.AcceptedJob.name -> CurrentJobsViewModel.CurrentJobsViewModelUseType.Accepted
                        else -> {
                            val errorMsg =
                                "Invalid use-type argument was given to CurrentJobsFragment instance!"
                            Log.e("Freelancer", errorMsg)
                            throw IllegalArgumentException(errorMsg)
                        }
                    },
                )
            ).get(CurrentJobsViewModel::class.java)

            adapter = CommonJobDataAdapter { jobId ->
                CurrentJobsContainerFragmentDirections.actionCurrentJobsShowJobDetails(
                    jobId = jobId,
                    userId = userId
                ).let { action ->
                    findNavController().navigate(action)
                }
            }
        }

        rvCurrentJobs.adapter = adapter
        rvCurrentJobs.layoutManager = LinearLayoutManager(requireContext())

        viewModel.currentJobsDataResponse.observe(viewLifecycleOwner) { currentJobsDataResponse ->
            when {
                currentJobsDataResponse.error is Exception -> handleError(currentJobsDataResponse.error)
                currentJobsDataResponse.jobData !is List<JobData> -> handleError(
                    IllegalStateException("Both received data and error is null!")
                )
                else -> adapter.setJobData(currentJobsDataResponse.jobData)
            }
        }

        //TODO parameter of this action may change in the future
        fab.setOnClickListener{
            CurrentJobsContainerFragmentDirections.actionCurrentJobsOpenPostJob().let{ action ->
                findNavController().navigate(action)
            }
        }
    }

    /**
     * TODO exception handling will be rewritten
     */
    private fun handleError(error: Exception) {
        Log.i("Freelancer", error.localizedMessage ?: "Unexpected error happened!")
        Snackbar.make(
            this.requireView(), error.localizedMessage ?: "Unexpected error happened!",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {

        private const val JOB_TYPE_KEY = "JOB_TYPE_KEY"

        private const val USER_ID_KEY = "USER_ID_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment CurrentJobsFragment.
         */
        @JvmStatic
        fun newInstance(jobType: RepresentedJobType, userId: Int) = CurrentJobsFragment().apply {
            arguments = Bundle().apply {
                putString(
                    JOB_TYPE_KEY, when (jobType) {
                        RepresentedJobType.AcceptedJob -> RepresentedJobType.AcceptedJob.name
                        RepresentedJobType.AnnouncedJob -> RepresentedJobType.AnnouncedJob.name
                    }
                )
                putInt(USER_ID_KEY, userId)
            }
        }
    }

    enum class RepresentedJobType {
        AnnouncedJob, AcceptedJob
    }
}