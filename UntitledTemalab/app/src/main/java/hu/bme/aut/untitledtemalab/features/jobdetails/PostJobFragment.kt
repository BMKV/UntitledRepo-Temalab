package hu.bme.aut.untitledtemalab.features.jobdetails

import android.app.DatePickerDialog
import android.os.Bundle
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
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class PostJobFragment : Fragment(), OnMapReadyCallback {

    lateinit var theMap: GoogleMap
    lateinit var theSpinner: Spinner
    lateinit var loggedInUser: UserData

    val args: JobDetailsFragmentArgs by navArgs()
    var userId = args.userId

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

        theSpinner.adapter = ArrayAdapter<PackageSize>(this.requireContext(), R.layout.support_simple_spinner_dropdown_item, PackageSize.values())

        //TODO: loggedInUser = az actually logged in user
        GlobalScope.launch { loggedInUser = NetworkManager.getUserProfileById(userId) }

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

                //Todo: Ez nincs a backenden?
                val atmExpiration = edtExpirationDate.text.toString()


                val atmJobData = JobRegistrationData(atmSize, atmTitle, atmPayment, atmIssueDate, atmDeadline, atmStartPoint, atmDestination)

                GlobalScope.launch { NetworkManager.postNewJob(loggedInUser.userId, atmJobData) }

                PostJobFragmentDirections.actionPostJobPostedJob().let { action ->
                    findNavController().navigate(action)
                }
            }

        }

        btnCancelNewJob.setOnClickListener {
            PostJobFragmentDirections.actionPostJobCancelledJob(loggedInUser.userId).let { action ->
                findNavController().navigate(action)
            }
        }

        edtDeadline.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                //TODO: kiszervezni resourceba (vagy nem?)
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
                //TODO: kiszervezni resourceba
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

        val hungary = LatLng(47.0, 19.0)
        theMap.addMarker(MarkerOptions().position(hungary).title("Marker in Hungary"))
        theMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hungary, 6f))
    }

}