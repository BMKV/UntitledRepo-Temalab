package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

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
import hu.bme.aut.untitledtemalab.data.UserData
import kotlinx.android.synthetic.main.fragment_job_board_container.*
import kotlinx.android.synthetic.main.merge_viewpager2_with_tablayout.*
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.properties.Delegates

class JobBoardContainerFragment : Fragment() {

    private var userId by Delegates.notNull<Int>()

    private lateinit var viewModel: JobBoardContainerViewModel

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

        //TODO it is not clean, it should be refactored later

        vp2ViewPager.adapter = JobBoardPagerAdapter(childFragmentManager, lifecycle, userId)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Small"
                1 -> "Medium"
                2 -> "Large"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()

        viewModel = ViewModelProvider(
            this,
            JobBoardContainerViewModelFactory(requireActivity().application, userId)
        ).get(JobBoardContainerViewModel::class.java)

        viewModel.cargoOccupancyResponse.observe(viewLifecycleOwner) { currentUserDataResponse ->
            when {
                currentUserDataResponse.error is Exception -> handleError(currentUserDataResponse.error)
                currentUserDataResponse.userData !is UserData -> handleError(
                    IllegalStateException("Both received data and error is null!")
                )
                else -> refreshCargoData(
                    currentUserDataResponse.userData
                )
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

    private fun refreshCargoData(userData: UserData) {
        try {
            val cargoCapacityMaximum = userData.cargoCapacity
                ?: throw IllegalStateException("Invalid answer from the server! - Undefined cargo max capacity!")
            val freeCargoSpace = userData.cargoFreeSize
                ?: throw IllegalStateException("Invalid answer from the server! - Undefined cargo free space!")
            check(cargoCapacityMaximum >= freeCargoSpace) { "Invalid answer from the server! - Maximum capacity is lower than occupied!" }
            pbCargoOccupancy.max = cargoCapacityMaximum
            pbCargoOccupancy.progress = cargoCapacityMaximum - freeCargoSpace
        } catch (exception: Exception) {
            handleError(exception)
        }
    }
}