package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * This [FragmentStateAdapter] subclass's responsibility to define the ViewPager2 widget's behavior,
 * that contains the user's package history.
 */
class HistoryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
        /**
         * This variable contains how many tabs are contained by the ViewPager2 instance.
         */
        private const val HISTORY_PAGER_TAB_COUNT = 2
    }

    override fun getItemCount(): Int {
        return HISTORY_PAGER_TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return HistoryFragment.newInstance(
            when(position){
                0 -> HistoryFragment.HistoryType.SentHistory
                1 -> HistoryFragment.HistoryType.TransportedHistory
                else ->
                    throw IllegalStateException("There is no fragment with this position: $position")
            }
        )
    }

}