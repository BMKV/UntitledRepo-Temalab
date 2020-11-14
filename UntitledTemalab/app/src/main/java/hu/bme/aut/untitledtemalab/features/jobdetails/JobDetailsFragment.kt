package hu.bme.aut.untitledtemalab.features.jobdetails


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.JobStatus
import hu.bme.aut.untitledtemalab.data.PackageSize
import hu.bme.aut.untitledtemalab.data.UserData
import hu.bme.aut.untitledtemalab.demostuff.DemoData
import kotlinx.android.synthetic.main.fragment_job_details.*

class JobDetailsFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var shownUser: UserData
    lateinit var shownJob: JobData
    lateinit var loggedInUser: UserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //TODO: Itt majd valahonnan get-elni a kapott id alapján a cuccot
        //De most: DemoData listájából
        shownJob = DemoData.demoJobList.last()
        shownUser = DemoData.demoUserList.last()
        loggedInUser = DemoData.loggedInUser

        //Setting contents
        setContent(shownJob, shownUser)

        //Set Button Fragment
        if (shownJob.ownerID == loggedInUser.userId) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else if (shownJob.status == JobStatus.pending) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        }
        else if (shownJob.status == JobStatus.delivered) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else if (shownJob.status == JobStatus.expired) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
        }
    }

    fun setContent(receivedJobData: JobData, receivedUserData: UserData) {
        //set User data cuccok
        tvNameTextOnJob.text = receivedUserData.userName
        tvEmailTextOnJob.text = receivedUserData.email
        tvUserRatingTextOnJob.text = receivedUserData.rating.toString()

        //set Job Data cuccok
        tvJobTitle.text = receivedJobData.jobName
        tvDestinationText.text = receivedJobData.deliveryRoute?.destination
        tvPickUpPointText.text = receivedJobData.deliveryRoute?.startLocation
        tvPackageSizeText.text = receivedJobData.packageSize.name
        tvPaymentText.text = receivedJobData.payment.toString()
        tvDeadlineText.text = receivedJobData.deadline
        tvIsItAcceptedStatusText.text = receivedJobData.status.name
        tvExpirationDateText.text = receivedJobData.listingExpirationDate ?: ""
        tvIsItAcceptedStatusText.text = receivedJobData.status.name
    }

    fun acceptJob() {
        shownJob.status = JobStatus.accepted
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
        syncData()
    }

    fun cancelJob() {
        shownJob.status = JobStatus.pending
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        syncData()
    }

    fun pickUpPackage() {
        shownJob.status = JobStatus.pickedUp
        tvIsItAcceptedStatusText.text = shownJob.status.name
        syncData()
    }

    fun completeJob() {
        shownJob.status = JobStatus.delivered
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        syncData()
    }

    fun getJobShown(): JobData {
        return shownJob
    }

    fun getUserShown() :UserData {
        return shownUser
    }

    fun syncData() {
        DemoData.demoJobList[DemoData.demoJobList.size-1] = shownJob
        DemoData.demoUserList[DemoData.demoUserList.size-1] = shownUser
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        //TODO: ez a !! biztos jó?
        theMap = googleMap!!

        theMap.isTrafficEnabled = true
        theMap.uiSettings.isZoomControlsEnabled = true

        val hungary = LatLng(47.0, 19.0)
        theMap.addMarker(MarkerOptions().position(hungary).title("Marker in Hungary"))
        theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hungary, 6f))
    }
}