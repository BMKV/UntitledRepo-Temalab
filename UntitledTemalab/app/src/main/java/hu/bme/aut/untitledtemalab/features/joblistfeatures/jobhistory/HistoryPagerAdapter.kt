package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * This [FragmentStateAdapter] subclass's responsibility to define the ViewPager2 widget's behavior,
 * that contains the user's package history.
 */
class HistoryPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val userId: Long,
    private val deliverAbility: Boolean
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        /**
         * This variable contains how many tabs are contained by the ViewPager2 instance, if the inspected user can deliver.
         */
        private const val HISTORY_PAGER_TRANSPORTER_TAB_COUNT = 2

        private const val HISTORY_PAGER_SENDER_TAB_COUNT = 1
    }

    override fun getItemCount(): Int {
        return if (deliverAbility) HISTORY_PAGER_TRANSPORTER_TAB_COUNT
        else HISTORY_PAGER_SENDER_TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return HistoryFragment.newInstance(
            if (deliverAbility) {
                when (position) {
                    0 -> HistoryFragment.HistoryType.SentHistory
                    1 -> HistoryFragment.HistoryType.TransportedHistory
                    else ->
                        throw IllegalStateException("There is no fragment with this position: $position")
                }
            } else {
                when (position) {
                    0 -> HistoryFragment.HistoryType.SentHistory
                    else -> throw IllegalStateException("There is no fragment with this position: $position")
                }
            }, userId
        )
    }

}