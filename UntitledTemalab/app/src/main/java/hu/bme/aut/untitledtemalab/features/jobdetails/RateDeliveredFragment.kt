package hu.bme.aut.untitledtemalab.features.jobdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_rate_delivered.*

class RateDeliveredFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate_delivered, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentJobDetails = parentFragment as JobDetailsFragment

        val btnRateDelivery = btnRateDelivery
        val ratingBar = ratingBarOnRateDelivered

        if (parentJobDetails.getJobShown().senderRating != null) {
            ratingBar.rating = parentJobDetails.getJobShown().senderRating!!.toFloat()
            ratingBar.setIsIndicator(true)
            btnRateDelivery.isEnabled = false
        }

        btnRateDelivery.setOnClickListener {
            parentJobDetails.rateJob(ratingBar.rating.toLong())
        }

    }
}