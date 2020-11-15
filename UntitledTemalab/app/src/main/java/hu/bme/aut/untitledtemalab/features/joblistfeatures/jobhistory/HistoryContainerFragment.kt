package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.merge_viewpager2_with_tablayout.*
import java.lang.IllegalStateException
import kotlin.properties.Delegates

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

    private var userId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: HistoryContainerFragmentArgs by navArgs()
        userId = navArgs.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp2ViewPager.adapter = HistoryPagerAdapter(childFragmentManager, lifecycle, userId)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Sent packages"
                1 -> "Transported packages"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }
}