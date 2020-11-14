package hu.bme.aut.untitledtemalab.features.jobdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import hu.bme.aut.untitledtemalab.data.PackageSize
import hu.bme.aut.untitledtemalab.data.RouteData
import hu.bme.aut.untitledtemalab.data.UserData
import hu.bme.aut.untitledtemalab.demostuff.DemoData
import kotlinx.android.synthetic.main.fragment_post_job.*

class PostJobFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var theSpinner: Spinner
    lateinit var loggedInUser: UserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapViewOnPost) as SupportMapFragment
        mapFragment.getMapAsync(this)

        theSpinner = spinnerPostPacakgeSize

        theSpinner.adapter = ArrayAdapter<PackageSize>(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, PackageSize.values())
        loggedInUser = DemoData.loggedInUser

        btnPostJob.setOnClickListener {
            val atmId = 1000001
            val atmTitle = edtJobTitle.text.toString()
            val atmStartPoint = edtPickUpPoint.text.toString()
            val atmDestination =  edtDestination.text.toString()
            val atmSize = when (theSpinner.selectedItem.toString()) {
                "small" -> PackageSize.small
                "medium" -> PackageSize.medium
                "large" -> PackageSize.large
                else -> PackageSize.large
            }
            val atmPayment = edtPayment.text.toString().toInt()
            val atmIssueDate = "2020.12.12"
            val atmDeadline = edtDeadline.text.toString()
            val atmExpiration = edtExpirationDate.text.toString()
            val atmJobData = JobData(100001, atmTitle, atmSize, atmPayment, atmIssueDate, atmDeadline, loggedInUser.userId )
            val atmRoute = RouteData(atmStartPoint, atmDestination)
            atmJobData.deliveryRoute = atmRoute
            atmJobData.listingExpirationDate = atmExpiration

            uploadJobListing(atmJobData, loggedInUser)

            PostJobFragmentDirections.actionPostJobPostedJob().let {
                action -> findNavController().navigate(action)
            }

        }
    }

    fun uploadJobListing(job: JobData, user: UserData) {
        DemoData.demoJobList.add(job)
        DemoData.demoUserList.add(user)
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