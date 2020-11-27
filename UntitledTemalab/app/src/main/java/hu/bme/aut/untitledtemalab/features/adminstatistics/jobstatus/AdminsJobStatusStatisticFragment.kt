package hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobStatusStatisticsData
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [AdminsJobStatusStatisticFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminsJobStatusStatisticFragment : Fragment() {

    private lateinit var viewModel: AdminJobStatusStatisticViewModel

    companion object {

        private const val USER_ID_KEY = "USER_ID_KEY"
        private const val INVALID_RESPONSE_ERROR = "INVALID_RESPONSE"
        private const val NETWORK_ERROR = "NETWORK_ERROR"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param userId the owner user's ID.
         * @return A new instance of fragment AdminsJobStatusStatisticFragment.
         */
        @JvmStatic
        fun newInstance(userId: Long) =
            AdminsJobStatusStatisticFragment().apply {
                arguments = Bundle().apply {
                    putLong(USER_ID_KEY, userId)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admins_job_status_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        observeLiveData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshStatistics()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this, AdminJobStatusStatisticViewModelFactory(
                requireActivity().application, requireArguments().getLong(
                    USER_ID_KEY
                )
            )
        ).get(AdminJobStatusStatisticViewModel::class.java)
    }

    private fun observeLiveData() {
        viewModel.statisticsResponse.observe(viewLifecycleOwner){ response ->
            when {
                response.error is Exception -> handleError(IllegalStateException(
                    NETWORK_ERROR
                ))
                response.statistic !is JobStatusStatisticsData -> handleError(
                    IllegalStateException(
                        INVALID_RESPONSE_ERROR
                    )
                )
                else -> setChart(response.statistic)
            }
        }
    }

    private fun setChart(statisticsData: JobStatusStatisticsData) {

    }

    private fun handleError(exception: Exception) {
        Log.i("Freelancer", exception.localizedMessage ?: "Error without message happened!")
        when (exception.message) {
            INVALID_RESPONSE_ERROR -> Snackbar.make(
                requireView(),
                getString(R.string.application_error),
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
}