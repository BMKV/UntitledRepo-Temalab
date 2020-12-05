package hu.bme.aut.untitledtemalab.features.mainmenu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private lateinit var viewModel: MainMenuViewModel

    private var userId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: MainMenuFragmentArgs by navArgs()
        userId = navArgs.userId

        initializeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        refreshUserAbilities()

        //My Profile button
        btnMyProfile.setOnClickListener { btnMyProfile ->
            MainMenuFragmentDirections.actionMainMenuOpenProfile(userId).let { action ->
                btnMyProfile!!.findNavController().navigate(action)
            }
        }

        //History Button
        btnHistory.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenHistory(userId, viewModel.userCanDeliver).let { action ->
                findNavController().navigate(action)
            }
        }

        //Accepted Jobs Button
        btnCurrentJobs.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenCurrentJobs(userId, viewModel.userCanDeliver).let { action ->
                findNavController().navigate(action)
            }

        }

        //Show Job Board Button
        btnJobBoard.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenJobBoard(userId).let { action ->
                findNavController().navigate(action)
            }
        }

        //Post New Job Button
        btnPostNewJob.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenPostJob(userId).let { action ->
                findNavController().navigate(action)
            }
        }

        //Show All Locations Button
        btnBestRoute.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuShowAllLocations().let { action ->
                btnBestRoute!!.findNavController().navigate(action)
            }
        }
    }

    private fun refreshUserAbilities(){
        viewModel.refreshAdminAbility()
        viewModel.refreshTransportingAbility()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this, MainMenuViewModelFactory(
                requireActivity().application,
                userId
            )
        ).get(MainMenuViewModel::class.java)
    }

    private fun observeViewModel() {
        observeAdminAbilityResponse()
        observeTransportingAbilityResponse()
    }

    private fun observeAdminAbilityResponse(){
        viewModel.isAdminResponse.observe(viewLifecycleOwner) { response ->
            if (response.hasAbility is Boolean)
                if (response.hasAbility)
                    showAdminStatisticsOptionalFAB()
                else if (response.error is Exception) {
                    Log.i("Freelancer", "Logged in user is not admin, or network error happened!")
                    fab.visibility = View.GONE
                }
        }
    }

    private fun showAdminStatisticsOptionalFAB() {
        fab.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuFragmentToAdminStatisticsContainerFragment(
                userId
            ).let { action ->
                findNavController().navigate(action)
            }
        }
        fab.visibility = View.VISIBLE
    }

    private fun observeTransportingAbilityResponse(){
        viewModel.canTransportResponse.observe(viewLifecycleOwner){ response ->
            if(response.hasAbility is Boolean)
                if(response.hasAbility){
                    btnJobBoard.isEnabled = true
                    viewModel.userCanDeliver = true
                }
            else if(response.error is Exception){
                    Log.i("Freelancer", "Logged in user is not transporter, or network error happened!")
                    viewModel.userCanDeliver = false
                }
        }
    }

}