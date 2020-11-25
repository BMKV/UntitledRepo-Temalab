package hu.bme.aut.untitledtemalab.features.jobdetails


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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JobDetailsFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var shownUser: UserData
    lateinit var shownJob: JobData
    lateinit var loggedInUser: UserData
    val uiUpdateHandler = Handler(Looper.getMainLooper())
    val args: JobDetailsFragmentArgs by navArgs()


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

        var jobId = args.jobId
        var userId = args.userId

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

        GlobalScope.launch { downloadData(jobId, userId) }

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
        GlobalScope.launch { NetworkManager.acceptJobById(shownJob.jobId, shownUser.userId) }
    }

    fun cancelJob() {
        shownJob.status = JobStatus.Pending
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        GlobalScope.launch { NetworkManager.cancelJobById(shownJob.jobId, shownUser.userId) }
    }

    fun pickUpPackage() {
        shownJob.status = JobStatus.PickedUp
        tvIsItAcceptedStatusText.text = shownJob.status.name
        GlobalScope.launch { NetworkManager.pickUpJobById(shownJob.jobId, shownUser.userId) }
    }

    fun completeJob() {
        shownJob.status = JobStatus.Delivered
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        GlobalScope.launch { NetworkManager.deliverJobById(shownJob.jobId, shownUser.userId) }
    }

    fun getJobShown(): JobData {
        return shownJob
    }

    fun getUserShown() :UserData {
        return shownUser
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

    suspend fun downloadData(jobId: Long, userId: Long) {
        //TODO: logged in user, mert most statikusan kérünk le teszt usert
        shownJob = NetworkManager.getJobById(jobId)
        shownUser = NetworkManager.getUserProfileById(shownJob.ownerID)
        loggedInUser = NetworkManager.getUserProfileById(3547612601)
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

}
