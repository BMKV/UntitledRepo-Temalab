package hu.bme.aut.untitledtemalab.jobhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_history.*

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment: Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
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

        rvHistory.adapter = HistoryAdapter()

        historyViewModel = ViewModelProvider(this, HistoryViewModelFactory(
            requireActivity().application, requireArguments().getString(HISTORY_TYPE_KEY)!!))
            .get(HistoryViewModel::class.java)
        historyViewModel.historyElements.observe(viewLifecycleOwner){ histories ->
            histories.let{ historyAdapter.setHistories(it) }
        }
    }

    companion object {

        private const val HISTORY_TYPE_KEY = "HISTORY_TYPE_KEY"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param historyType with this parameter it can be given, that which type of history
         * will this fragment represent.
         * @return A new instance of fragment HistoryFragment.
         */
        @JvmStatic
        fun newInstance(historyType: HistoryType) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(HISTORY_TYPE_KEY,
                        when(historyType){
                            HistoryType.SentHistory -> HistoryType.SentHistory.name
                            HistoryType.TransportedHistory -> HistoryType.TransportedHistory.name
                        }
                    )
                }
            }
    }

    enum class HistoryType{
        SentHistory, TransportedHistory
    }
}