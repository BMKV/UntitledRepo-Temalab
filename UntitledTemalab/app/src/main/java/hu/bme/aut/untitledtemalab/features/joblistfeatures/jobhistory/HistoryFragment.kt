package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.untitledtemalab.R
import hu.bme.aut.untitledtemalab.data.JobData
import kotlinx.android.synthetic.main.fragment_history.*
import java.lang.IllegalStateException

/**
 * This [Fragment] subclass's responsibility is to show the user a list of job details about the
 * user's history. The Fragment can show the history of the user's sent packages, and the history
 * of the delivered packages by the user.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment: Fragment() {

    /**
     * TODO this comment is not actually right.
     * The instance's ViewModel, which contains the business logic.
     */
    private lateinit var historyViewModel: HistoryViewModel

    /**
     * The Fragment contains a RecyclerView (in which the job history's elements are represented),
     * and this property store this adapter instance's reference.
     */
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel = ViewModelProvider(this, HistoryViewModelFactory(
            requireActivity().application, requireArguments().getString(HISTORY_TYPE_KEY)!!,
            requireArguments().getInt(USER_ID_KEY)))
            .get(HistoryViewModel::class.java)
        historyViewModel.historyDataResponse.observe(viewLifecycleOwner){ historyResponse ->
            when {
                historyResponse.error is Throwable -> handleError(historyResponse.error)
                historyResponse.jobData !is List<JobData> -> handleError(
                    IllegalStateException("Received data is null!"))
                else -> historyAdapter.setHistories(historyResponse.jobData)
            }
        }

        historyAdapter = HistoryAdapter(historyViewModel.userId)
        rvHistory.adapter = historyAdapter

    }

    /**
     * TODO documentation
     */
    private fun handleError(throwable: Throwable){
        Log.i("Freelancer", throwable.localizedMessage ?: "Unexpected error happened!")
        Snackbar.make(this.requireView(), throwable.localizedMessage ?: "Unexpected error happened!",
            Snackbar.LENGTH_SHORT).show()
    }

    companion object {

        /**
         * This variable stores the key value to access the fragment's [HistoryType] using option.
         */
        private const val HISTORY_TYPE_KEY = "HISTORY_TYPE_KEY"

        private const val USER_ID_KEY = "USER_ID_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param historyType with this parameter it can be given, that which type of history
         * will this fragment represent.
         * @return A new instance of fragment HistoryFragment.
         */
        @JvmStatic
        fun newInstance(historyType: HistoryType, userId: Int) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(HISTORY_TYPE_KEY,
                        when(historyType){
                            HistoryType.SentHistory -> HistoryType.SentHistory.name
                            HistoryType.TransportedHistory -> HistoryType.TransportedHistory.name
                        }
                    )
                    putInt(USER_ID_KEY, userId)
                }
            }
    }

    /**
     * This enum class contains the using options of HistoryFragment class.
     */
    enum class HistoryType{
        SentHistory, TransportedHistory
    }
}