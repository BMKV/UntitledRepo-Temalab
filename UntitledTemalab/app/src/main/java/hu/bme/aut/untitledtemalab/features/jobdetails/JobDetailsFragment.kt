package hu.bme.aut.untitledtemalab.features.jobdetails


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.JobStatus
import hu.bme.aut.untitledtemalab.data.UserData
import hu.bme.aut.untitledtemalab.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_job_details.*
import kotlinx.android.synthetic.main.fragment_post_job.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class JobDetailsFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var shownUser: UserData
    lateinit var shownJob: JobData
    lateinit var loggedInUser: UserData
    val uiUpdateHandler = Handler(Looper.getMainLooper())
    val args: JobDetailsFragmentArgs by navArgs()
    var jobId: Long = 0
    var userId: Long = 0
    var startLatLng: LatLng? = null
    var endLatLng: LatLng? = null


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
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapViewOnDetails) as SupportMapFragment
        mapFragment.getMapAsync(this)

        jobId = args.jobId
        userId = args.userId

        btnExpandMapOnDetails.setOnClickListener {
            if (btnExpandMapOnDetails.text.toString() == getString(R.string.expand_map)) {
                btnExpandMapOnDetails.text = getString(R.string.collapse_map)
                mlOnDetails.transitionToEnd()
            }
            else if (btnExpandMapOnDetails.text.toString() == getString(R.string.collapse_map)) {
                btnExpandMapOnDetails.text = getString(R.string.expand_map)
                mlOnDetails.transitionToStart()
            }
        }

        GlobalScope.launch {
            downloadData(jobId, userId)
            uiUpdateHandler.post {
                setMap()
            }
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
        tvPackageSizeText.text = receivedJobData.size.name
        tvPaymentText.text = receivedJobData.payment.toString()
        tvDeadlineText.text = receivedJobData.deadline
        tvIsItAcceptedStatusText.text = receivedJobData.status.name
        tvExpirationDateText.text = receivedJobData.listingExpirationDate ?: ""
        tvIsItAcceptedStatusText.text = receivedJobData.status.name

        //Set the buttons
        setButtonFragment()
    }

    fun acceptJob() {
        shownJob.status = JobStatus.Accepted
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
        GlobalScope.launch { NetworkManager.acceptJobById(shownJob.jobId, userId) }
    }

    fun cancelJob() {
        shownJob.status = JobStatus.Pending
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        GlobalScope.launch { NetworkManager.cancelJobById(shownJob.jobId, userId) }
    }

    fun pickUpPackage() {
        shownJob.status = JobStatus.PickedUp
        tvIsItAcceptedStatusText.text = shownJob.status.name
        GlobalScope.launch { NetworkManager.pickUpJobById(shownJob.jobId, userId) }
    }

    fun completeJob() {
        shownJob.status = JobStatus.Delivered
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        GlobalScope.launch { NetworkManager.deliverJobById(shownJob.jobId, userId) }
    }

    fun rateJob(rating: Long) {
        shownJob.senderRating = rating
        GlobalScope.launch { NetworkManager.rateJobById(shownJob.jobId, userId, rating) }
    }

    fun getJobShown(): JobData {
        return shownJob
    }

    fun getUserShown() :UserData {
        return shownUser
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        theMap = googleMap!!

        theMap.isTrafficEnabled = true
        theMap.uiSettings.isZoomControlsEnabled = true
    }

    suspend fun downloadData(jobId: Long, userId: Long) {
        shownJob = NetworkManager.getJobById(jobId)
        shownUser = NetworkManager.getUserProfileById(shownJob.ownerID)
        loggedInUser = NetworkManager.getUserProfileById(userId)
        uiUpdateHandler.post(Runnable { setContent(shownJob, shownUser) })
    }

    private fun setButtonFragment() {
        if (shownJob.ownerID == loggedInUser.userId) {
            if (shownJob.status == JobStatus.Delivered)
            {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, RateDeliveredFragment()).commit()
            }
            else {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
            }
        }
        else
        {
            if (shownJob.status == JobStatus.Pending) {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
            }
            else if (shownJob.status == JobStatus.Delivered) {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
            }
            else if (shownJob.status == JobStatus.Expired) {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
            }
            else {
                childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
            }
        }
    }

    fun getLocationFromSring(address: String): LatLng? {
        val coder: Geocoder = Geocoder(this.requireContext(), Locale.getDefault())
        val addressNew = address.replace(",", " ")
        var result: MutableList<Address> = ArrayList()
        var coord: LatLng? = null

        try {
            result = coder.getFromLocationName(addressNew, 1)
            if (!result.isEmpty()) {
                val location = result[0]
                coord = LatLng(location.latitude, location.longitude)
            }
        }
        catch(e: Exception) {
            e.printStackTrace()
        }
        return coord
    }

    fun setMap() {
        startLatLng = getLocationFromSring(shownJob.deliveryRoute!!.startLocation)
        endLatLng = getLocationFromSring(shownJob.deliveryRoute!!.destination)

        if (endLatLng != null) {
            theMap.addMarker(MarkerOptions().position(endLatLng!!).title("Pickup Point"))
        }
        if (startLatLng != null) {
            theMap.addMarker(MarkerOptions().position(startLatLng!!).title("Destination"))
            theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng!!, 6f))
        }
    }

}
