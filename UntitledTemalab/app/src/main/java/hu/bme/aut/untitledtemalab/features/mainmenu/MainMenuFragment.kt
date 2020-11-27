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
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_main_menu.*

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private lateinit var viewModel: MainMenuViewModel

    private val userId = 3547612601

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        observeViewModel()
        viewModel.refreshAdminAbility()

        //My Profile button
        //TODO userId will be passed in the action
        btnMyProfile.setOnClickListener { btnMyProfile ->
            MainMenuFragmentDirections.actionMainMenuOpenProfile(3547612601).let { action ->
                btnMyProfile!!.findNavController().navigate(action)
            }
        }

        //History Button
        //TODO userId will be passed in the action
        btnHistory.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenHistory(3547612601).let { action ->
                findNavController().navigate(action)
            }
        }

        //Accepted Jobs Button
        //TODO userId will be passed in the action
        btnCurrentJobs.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenCurrentJobs(3547612601).let { action ->
                findNavController().navigate(action)
            }

        }

        //Show Job Board Button
        //TODO userId will be passed in the action
        btnJobBoard.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuOpenJobBoard(3547612601).let { action ->
                findNavController().navigate(action)
            }
        }

        //Post New Job Button
        btnPostNewJob.setOnClickListener { btnPostNewJob ->
            MainMenuFragmentDirections.actionMainMenuOpenPostJob().let { action ->
                btnPostNewJob!!.findNavController().navigate(action)
            }
        }

        //Show All Locations Button
        btnBestRoute.setOnClickListener {
            MainMenuFragmentDirections.actionMainMenuShowAllLocations().let { action ->
                btnBestRoute!!.findNavController().navigate(action)
            }
        }
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
        viewModel.isAdminResponse.observe(viewLifecycleOwner) { response ->
            if (response.isAdmin is Boolean)
                if (response.isAdmin)
                    showAdminStatisticsOptionalFAB()
                else if (response.error is Exception){
                    Log.i("Freelancer", "Logged in user is not admin, or network error happened!")
                    fab.visibility = View.GONE
                }
        }
    }

    private fun showAdminStatisticsOptionalFAB() {
        fab.setOnClickListener{
            //TODO navigating to the admin stats fragment
        }
        fab.visibility = View.VISIBLE
    }
}