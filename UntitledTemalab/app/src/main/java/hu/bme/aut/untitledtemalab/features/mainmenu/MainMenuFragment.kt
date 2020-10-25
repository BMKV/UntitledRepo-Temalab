package hu.bme.aut.untitledtemalab.features.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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
        btnMyProfile.setOnClickListener{ btnMyProfile ->
            MainMenuFragmentDirections.actionMainMenuFragmentToProfileScreenFragment().let{
                    action -> btnMyProfile!!.findNavController().navigate(action)
            }
        }
        btnHistory.setOnClickListener{ btnHistory ->
            MainMenuFragmentDirections.actionMainMenuFragmentToHistoryContainerFragment().let{
                action -> btnHistory!!.findNavController().navigate(action)
            }
        }
    }
}