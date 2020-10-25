package hu.bme.aut.untitledtemalab.features.jobdetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.PackageSize
import hu.bme.aut.untitledtemalab.data.UserData
import kotlinx.android.synthetic.main.fragment_job_details.*

class JobDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get the content from somewhere to be set
        //most: placeholder JobData, UserData
        val placeholderJobData = JobData(1, "Deliver 10 tons of Beer in the name of Democracy!", PackageSize.large, 8500, "3308. 10. 28.", "3308. 10. 31.")
        val placehoolderUserData = UserData(100000001, "Teszt Elek", "teszt@fejleszto.com", 5.9)
        //--------------------------
        setContent(placeholderJobData, placehoolderUserData)
    }

    fun setContent(receivedJobData: JobData, receivedUserData: UserData) {
        //set User data cuccok
        tvNameTextOnJob.text = receivedUserData.userName
        tvEmailTextOnJob.text = receivedUserData.email
        tvUserRatingTextOnJob.text = receivedUserData.rating as String

        //set Job Data cuccok
        tvJobTitle.text = receivedJobData.jobName
        //tvDestinationText.text =
        //tvPickUpPointText.text =
        tvPackageSizeText.text = receivedJobData.packageSize.name
        tvPaymentText.text = receivedJobData.payment as String
        tvDeadlineText.text = receivedJobData.deliveryDate
        tvIsItAcceptedStatusText.text = receivedJobData.status.name
    }
}