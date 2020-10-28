package hu.bme.aut.untitledtemalab.features.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.UserData
import kotlinx.android.synthetic.main.fragment_job_details.*
import kotlinx.android.synthetic.main.fragment_profile_screen.*

/**
 * A simple [Fragment] subclass.
 * TODO documentation
 */
class ProfileScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: TheStorage-ből kikérni az éppen belogolt User adatait
        //Most: placeholder UserData
        val placehoolderUserData = UserData(100000001, "Teszt Elek", "teszt@fejleszto.com", 5.9)
        //-----------------------
        setContent(placehoolderUserData)
    }

    fun setContent(receivedUserData: UserData) {
        tvNameTextOnProfile.text = receivedUserData.userName
        tvEmailTextOnProfile.text = receivedUserData.email
        tvUserRatingTextOnProfile.text = receivedUserData.rating as String
    }

}