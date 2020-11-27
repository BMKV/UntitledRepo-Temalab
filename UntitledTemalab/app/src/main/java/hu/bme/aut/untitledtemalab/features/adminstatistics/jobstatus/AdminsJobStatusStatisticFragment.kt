package hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.untitledtemalab.R

/**
 * A simple [Fragment] subclass.
 * Use the [AdminsJobStatusStatisticFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminsJobStatusStatisticFragment : Fragment() {

    companion object {

        private const val USER_ID_KEY = "USER_ID_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param userId the owner user's ID.
         * @return A new instance of fragment AdminsJobStatusStatisticFragment.
         */
        @JvmStatic
        fun newInstance(userId: Long) =
            AdminsJobStatusStatisticFragment().apply {
                arguments = Bundle().apply {
                    putLong(USER_ID_KEY, userId)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admins_job_status_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}