package hu.bme.aut.untitledtemalab.features.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_main_menu.*

/**
 * A simple [Fragment] subclass.
 * TODO documentation
 */
class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //My Profile button
        //TODO userId will be passed in the action
        btnMyProfile.setOnClickListener{ btnMyProfile ->
            MainMenuFragmentDirections.actionMainMenuOpenProfile().let{
                    action -> btnMyProfile!!.findNavController().navigate(action)
            }
        }

        //History Button
        //TODO userId will be passed in the action
        btnHistory.setOnClickListener{ btnHistory ->
            MainMenuFragmentDirections.actionMainMenuOpenHistory().let{
                action -> btnHistory!!.findNavController().navigate(action)
            }
        }

        //Accepted Jobs Button
        //TODO userId will be passed in the action
        btnCurrentJobs.setOnClickListener{
            MainMenuFragmentDirections.actionMainMenuOpenCurrentJobs().let{
                action -> findNavController().navigate(action)
            }

        }

        //Post New Job Button
        btnPostNewJob.setOnClickListener { btnPostNewJob ->
            MainMenuFragmentDirections.actionMainMenuOpenPostJob().let {
                action -> btnPostNewJob!!.findNavController().navigate(action)
            }
        }

        //Show Best Route Button
        //TODO: ATTENTION - teszteléshez/Demohoz ez most a JobDetails-re visz --> REMOVE IT LATER
        btnBestRoute.setOnClickListener { btnBestRoute ->
            MainMenuFragmentDirections.actionTESTMMenuToJobDetails().let {
                    action -> btnBestRoute!!.findNavController().navigate(action)
            }
        }
    }
}