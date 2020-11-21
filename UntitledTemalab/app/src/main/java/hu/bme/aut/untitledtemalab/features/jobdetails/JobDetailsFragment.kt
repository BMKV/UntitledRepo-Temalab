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
import hu.bme.aut.untitledtemalab.demostuff.DemoData
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

        GlobalScope.launch { downloadData(jobId, userId) }


    }

    fun setContent(receivedJobData: JobData, receivedUserData: UserData) {
        //set User data cuccok
        tvNameTextOnJob.text = receivedUserData.userName
        tvEmailTextOnJob.text = receivedUserData.email
        tvUserRatingTextOnJob.text = receivedUserData.rating.toString()

        //set Job Data cuccok
        //TODO: enumokat helyrepofozni
        tvJobTitle.text = receivedJobData.jobName
        tvDestinationText.text = receivedJobData.deliveryRoute?.destination
        tvPickUpPointText.text = receivedJobData.deliveryRoute?.startLocation
        //tvPackageSizeText.text = receivedJobData.size.name
        tvPaymentText.text = receivedJobData.payment.toString()
        tvDeadlineText.text = receivedJobData.deadline
        //tvIsItAcceptedStatusText.text = receivedJobData.status.name
        tvExpirationDateText.text = receivedJobData.listingExpirationDate ?: ""
        //tvIsItAcceptedStatusText.text = receivedJobData.status.name

        //Set the buttons
        setButtonFragment()
    }

    fun acceptJob() {
        shownJob.status = JobStatus.Accepted
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, ChangeJobStatusFragment()).commit()
        syncData()
    }

    fun cancelJob() {
        shownJob.status = JobStatus.Pending
        tvIsItAcceptedStatusText.text = shownJob.status.name
        childFragmentManager.beginTransaction().replace(R.id.fragmentContainerOnDetails, AcceptJobFragment()).commit()
        syncData()
    }

    fun pickUpPackage() {
        shownJob.status = JobStatus.PickedUp
        tvIsItAcceptedStatusText.text = shownJob.status.name
        syncData()
    }

    fun completeJob() {
        shownJob.status = JobStatus.Delivered
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
        //TODO: Actual network-re bekötni
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

    suspend fun downloadData(jobId: Long, userId: Long) {
        //TODO: logged in user
        shownJob = NetworkManager.getJobById(jobId)             //(8091435996)
        shownUser = NetworkManager.getUserProfileById(userId)
        loggedInUser = DemoData.loggedInUser
        uiUpdateHandler.post(Runnable { setContent(shownJob, shownUser) })
    }

    private fun setButtonFragment() {
        if (shownJob.ownerID == loggedInUser.userId) {
            childFragmentManager.beginTransaction().add(R.id.fragmentContainerOnDetails, JobInformationFragment()).commit()
        }
        else if (shownJob.status == JobStatus.Pending) {
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
