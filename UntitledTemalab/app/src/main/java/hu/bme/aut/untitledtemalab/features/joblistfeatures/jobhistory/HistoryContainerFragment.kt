package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.fragment_history_container.*
import java.lang.IllegalStateException

/**
 * This [Fragment] subclass's responsibility to implement the History feature of the application.
 * In the history, the user can check his/her sent packages; and the packages, that were delivered
 * by the user (if the user is able to deliver packages). The fragment contains a ViewPager, which
 * makes convenient to navigate between the two packages histories.
 * // TODO: If the history is viewed by a simple, non-transporter user, the transported tab shouldn't
 *      appear (No tab should appear to be precise). The implementation of this feature could depend on
 *      the backend, so it hasn't been implemented yet.
 */
class HistoryContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp2ViewPager.adapter = HistoryPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Sent packages"
                1 -> "Transported packages"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }
}