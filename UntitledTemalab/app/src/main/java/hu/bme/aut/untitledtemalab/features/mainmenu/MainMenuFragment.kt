package hu.bme.aut.untitledtemalab.features.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard.JobBoardFragmentDirections
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
        btnMyProfile.setOnClickListener{ btnMyProfile ->
            MainMenuFragmentDirections.actionMainMenuOpenProfile().let{
                    action -> btnMyProfile!!.findNavController().navigate(action)
            }
        }

        //History Button
        btnHistory.setOnClickListener{ btnHistory ->
            MainMenuFragmentDirections.actionMainMenuOpenHistory().let{
                action -> btnHistory!!.findNavController().navigate(action)
            }
        }

        //Post New Job Button
        //TODO: ATTENTION - teszteléshez ez most a JobDetails-re visz --> REMOVE IT LATER
        btnPostNewJob.setOnClickListener { btnPostNewJob ->
            MainMenuFragmentDirections.actionTESTMMenuToJobDetails().let {
                action -> btnPostNewJob!!.findNavController().navigate(action)
            }
        }
    }
}