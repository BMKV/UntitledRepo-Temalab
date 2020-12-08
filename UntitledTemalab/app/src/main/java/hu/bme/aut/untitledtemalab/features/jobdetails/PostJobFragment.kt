package hu.bme.aut.untitledtemalab.features.jobdetails

import android.app.DatePickerDialog
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.*
import hu.bme.aut.untitledtemalab.network.NetworkManager
import kotlinx.android.synthetic.main.fragment_post_job.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class PostJobFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var theSpinner: Spinner
    lateinit var loggedInUser: UserData
    var startLatLng: LatLng? = null
    var endLatLng: LatLng? = null


    var userId: Long by Delegates.notNull()

    var deadlYear: Int = 0
    var deadlMonth: Int = 0
    var deadlDay: Int = 0

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

        val args: PostJobFragmentArgs by navArgs()
        userId = args.senderUserId

        theSpinner.adapter = ArrayAdapter<PackageSize>(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, PackageSize.values())

        GlobalScope.launch { loggedInUser =  NetworkManager.getUserProfileById(userId) }

        btnExpandMapOnPost.setOnClickListener {
            if (btnExpandMapOnPost.text.toString() == getString(R.string.expand_map)) {
                btnExpandMapOnPost.text = getString(R.string.collapse_map)
                mlOnPost.transitionToEnd()
            }
            else if (btnExpandMapOnPost.text.toString() == getString(R.string.collapse_map)) {
                btnExpandMapOnPost.text = getString(R.string.expand_map)
                mlOnPost.transitionToStart()
            }
        }

        edtPickUpPoint.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus == false) {
                startLatLng = getLocationFromSring(edtPickUpPoint.text.toString())
                if (startLatLng != null) {
                    theMap.addMarker(MarkerOptions().position(startLatLng!!).title("Pickup Point"))
                    theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLng!!, 6f))
                }
            }
        }

        edtDestination.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus == false) {
                endLatLng = getLocationFromSring(edtPickUpPoint.text.toString())
                if (endLatLng != null) {
                    theMap.addMarker(MarkerOptions().position(endLatLng!!).title("Destination"))
                    theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLatLng!!, 6f))
                }
            }
        }

        btnPostJob.setOnClickListener {

            if (edtJobTitle.text.toString() == "" ||
                edtPickUpPoint.text.toString() == "" ||
                edtDestination.text.toString() == "" ||
                edtPayment.text.toString() == "" ||
                edtDeadline.text.toString() == "" ||
                edtExpirationDate.text.toString() == "")
            {
                Snackbar.make(this.requireView(), "You have to fill in all details correctly!", Snackbar.LENGTH_SHORT).show()
            }
            else {
                //Collecting the átmeneti data
                val atmTitle = edtJobTitle.text.toString()
                val atmStartPoint = edtPickUpPoint.text.toString()
                val atmDestination = edtDestination.text.toString()
                val atmSize = when (theSpinner.selectedItem.toString()) {
                    "Small" -> PackageSize.Small
                    "Medium" -> PackageSize.Medium
                    "Large" -> PackageSize.Large
                    else -> PackageSize.Large
                }
                val atmPayment = edtPayment.text.toString().toInt()
                val atmIssueDate = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                val atmDeadline = OffsetDateTime.of(deadlYear, deadlMonth, deadlDay, 23, 59, 0, 0, ZoneOffset.ofHours(1)).format(
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                val atmExpiration = edtExpirationDate.text.toString()


                val atmJobData = JobRegistrationData(atmSize, atmTitle, atmPayment, atmIssueDate, atmDeadline, atmStartPoint, atmDestination)

                GlobalScope.launch { NetworkManager.postNewJob(loggedInUser.userId, atmJobData) }

                PostJobFragmentDirections.actionPostJobPostedJob(loggedInUser.userId ,loggedInUser.canDeliver).let { action ->
                    findNavController().navigate(action)
                }
            }

        }

        btnCancelNewJob.setOnClickListener {
            findNavController().popBackStack()
        }

        edtDeadline.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                val atmString = "$year ${month+1} $dayOfMonth"
                deadlYear = year
                deadlMonth = month + 1
                deadlDay = dayOfMonth
                edtDeadline.setText(atmString)
            }
            datePickerDialog.show()
        }

        edtExpirationDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                val atmString = "$year $month $dayOfMonth"
                edtExpirationDate.setText(atmString)
            }
            datePickerDialog.show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        theMap = googleMap!!

        theMap.isTrafficEnabled = true
        theMap.uiSettings.isZoomControlsEnabled = true
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

}