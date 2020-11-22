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
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.CargoData
import kotlinx.android.synthetic.main.fragment_job_board_container.*
import kotlinx.android.synthetic.main.merge_viewpager2_with_tablayout.*
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.properties.Delegates

class JobBoardContainerFragment : Fragment(), JobBoardFragment.JobBoardRefreshListener {

    private var userId by Delegates.notNull<Long>()

    private lateinit var viewModel: JobBoardContainerViewModel

    companion object {
        private const val ERROR_INVALID_CARGO_STATE =
            "Invalid answer from the server! - Maximum capacity is lower than occupied!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: JobBoardContainerFragmentArgs by navArgs()
        userId = navArgs.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_board_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePagerAdapter()
        initializeViewModel()
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshJobsLiveData()
    }

    private fun initializePagerAdapter() {
        vp2ViewPager.adapter = JobBoardPagerAdapter(childFragmentManager, lifecycle, userId)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Small"
                1 -> "Medium"
                2 -> "Large"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this,
            JobBoardContainerViewModelFactory(requireActivity().application, userId)
        ).get(JobBoardContainerViewModel::class.java)
    }

    private fun observeViewModelData() {
        viewModel.cargoOccupancyResponse.observe(viewLifecycleOwner) { currentCargoDataResponse ->
            when {
                currentCargoDataResponse.error is Exception -> handleError(currentCargoDataResponse.error)
                currentCargoDataResponse.cargoData !is CargoData -> handleError(
                    IllegalStateException("Both received data and error is null!")
                )
                else -> refreshCargoData(
                    currentCargoDataResponse.cargoData
                )
            }
        }
    }

    private fun handleError(error: Exception) {
        Log.i("Freelancer", error.localizedMessage ?: "Unexpected error happened!")
        when (error.message) {
            ERROR_INVALID_CARGO_STATE -> Snackbar.make(
                this.requireView(),
                getString(R.string.error_invalid_cargo_data_loaded),
                Snackbar.LENGTH_SHORT
            ).show()
            //Network error
            else -> Snackbar.make(
                this.requireView(),
                R.string.network_error,
                Snackbar.LENGTH_SHORT
            )
                .setAction(getString(R.string.open_wifi_settings)) {
                    startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
                }.show()
        }
    }

    private fun refreshCargoData(cargoData: CargoData) {
        try {
            val cargoCapacityMaximum = cargoData.maxSize
            val freeCargoSpace = cargoData.freeSize
            Log.i("Freelancer", cargoCapacityMaximum.toString())
            Log.i("Freelancer", freeCargoSpace.toString())
            check(cargoCapacityMaximum >= freeCargoSpace) { ERROR_INVALID_CARGO_STATE }
            pbCargoOccupancy.max = cargoCapacityMaximum
            pbCargoOccupancy.progress = cargoCapacityMaximum - freeCargoSpace
            tvOccupancyPercentage.text = getString(
                R.string.cargo_occupancy,
                cargoCapacityMaximum - freeCargoSpace,
                cargoCapacityMaximum
            )
        } catch (exception: Exception) {
            handleError(exception)
        }
    }

    override fun onJobBoardRefresh() {
        viewModel.refreshJobsLiveData()
    }
}