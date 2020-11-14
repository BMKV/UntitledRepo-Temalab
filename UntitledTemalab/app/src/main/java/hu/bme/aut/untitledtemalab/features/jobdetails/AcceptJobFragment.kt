package hu.bme.aut.untitledtemalab.features.jobdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_accept_job.*

class AcceptJobFragment :Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_accept_job, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAccept = btnAccept
        btnAccept.setOnClickListener {
            (parentFragment as JobDetailsFragment).acceptJob()
        }
    }
}