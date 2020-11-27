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
import java.util.*

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

        //TODO: loggedInUser = az actually logged in user
        GlobalScope.launch { loggedInUser = NetworkManager.getUserProfileById(3547612601) }

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
                    "small" -> PackageSize.Small
                    "medium" -> PackageSize.Medium
                    "large" -> PackageSize.Large
                    else -> PackageSize.Large
                }
                val atmPayment = edtPayment.text.toString().toInt()
                //TODO: Actual today's date
                val atmIssueDate = "2020.12.12"
                val atmDeadline = edtDeadline.text.toString()

                //Todo: Ez nincs a backenden?
                val atmExpiration = edtExpirationDate.text.toString()


                val atmJobData = JobRegistrationData(atmSize, atmTitle, atmPayment, atmIssueDate, atmDeadline, atmStartPoint, atmDestination)

                NetworkManager.postNewJob(loggedInUser.userId, atmJobData)

                PostJobFragmentDirections.actionPostJobPostedJob().let { action ->
                    findNavController().navigate(action)
                }
            }

        }

        btnCancelNewJob.setOnClickListener {
            PostJobFragmentDirections.actionPostJobCancelledJob().let { action ->
                findNavController().navigate(action)
            }
        }

        edtDeadline.setOnClickListener {
            val cal = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this.requireContext())
            datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
                //TODO: kiszervezni resourceba
                val atmString = "$year $month $dayOfMonth"
                edtDeadline.setText(atmString)
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