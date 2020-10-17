package hu.bme.aut.untitledtemalab.jobhistory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HistoryPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
        private const val HISTORY_PAGER_TAB_COUNT = 2
    }

    override fun getItemCount(): Int {
        return HISTORY_PAGER_TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                HistorySentPackagesFragment()
            }
            1 -> {
                HistoryTransportedPackagesFragment()
            }
            else -> throw IllegalStateException("There is no fragment with this position: $position")
        }
    }

}