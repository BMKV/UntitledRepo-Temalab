package hu.bme.aut.untitledtemalab.features.profile

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.UserData
import kotlinx.android.synthetic.main.fragment_profile_screen.*
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 * TODO documentation
 */
class ProfileScreenFragment : Fragment() {

    private var userId by Delegates.notNull<Long>()

    private lateinit var viewModel: ProfileScreenViewModel

    companion object {
        private const val ERROR_EMPTY_MESSAGE = "Both received data and error is null!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navArgs: ProfileScreenFragmentArgs by navArgs()
        userId = navArgs.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        observeViewModelData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshUserData()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this,
            ProfileScreenViewModelFactory(requireActivity().application, userId)
        ).get(ProfileScreenViewModel::class.java)
    }

    private fun observeViewModelData() {
        viewModel.userDataResponse.observe(viewLifecycleOwner) { userDataResponse ->
            when {
                userDataResponse.error is Exception -> handleError(userDataResponse.error)
                userDataResponse.userData !is UserData -> handleError(
                    IllegalStateException(ERROR_EMPTY_MESSAGE)
                )
                else -> setContent(userDataResponse.userData)
            }
        }
    }

    private fun setContent(receivedUserData: UserData) {
        tvNameTextOnProfile.text = receivedUserData.userName
        tvEmailTextOnProfile.text = receivedUserData.email
        tvUserRatingTextOnProfile.text = receivedUserData.rating.toString()
    }

    private fun handleError(error: Exception) {
        if (error.message == ERROR_EMPTY_MESSAGE)
            Snackbar.make(
                requireView(),
                getString(R.string.server_error),
                Snackbar.LENGTH_SHORT
            ).show()
        else Snackbar.make(
            this.requireView(), getString(R.string.network_error),
            Snackbar.LENGTH_SHORT
        )
            .setAction(getString(R.string.open_wifi_settings)) {
                startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
            }.show()
    }

}