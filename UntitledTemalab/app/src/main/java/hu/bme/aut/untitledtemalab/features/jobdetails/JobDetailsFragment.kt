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
        setContent(DemoData.demoJobList.last(), DemoData.demoUserList.last())

        //Set Button Fragment
        if (DemoData.demoJobList.last().ownerID == DemoData.loggedInUser.userId) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else if (DemoData.demoJobList.last().status == JobStatus.pending) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        }
        else if (DemoData.demoJobList.last().status == JobStatus.delivered) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else if (DemoData.demoJobList.last().status == JobStatus.expired) {
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
        DemoData.demoJobList.last().status = JobStatus.accepted
        tvIsItAcceptedStatusText.text = DemoData.demoJobList.last().status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
    }

    fun cancelJob() {
        DemoData.demoJobList.last().status = JobStatus.pending
        tvIsItAcceptedStatusText.text = DemoData.demoJobList.last().status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()

    }

    fun pickUpPackage() {
        DemoData.demoJobList.last().status = JobStatus.pickedUp
        tvIsItAcceptedStatusText.text = DemoData.demoJobList.last().status.name
    }

    fun completeJob() {
        DemoData.demoJobList.last().status = JobStatus.delivered
        tvIsItAcceptedStatusText.text = DemoData.demoJobList.last().status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
    }

    fun getJobShown(): JobData {
        return DemoData.demoJobList.last()
    }

    fun getUserShown() :UserData {
        return DemoData.demoUserList.last()
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