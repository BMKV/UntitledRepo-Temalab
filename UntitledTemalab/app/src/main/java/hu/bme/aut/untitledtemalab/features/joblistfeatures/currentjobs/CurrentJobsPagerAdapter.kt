package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CurrentJobsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val userId: Int
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
        /**
         * This variable contains how many tabs are contained by the ViewPager2 instance.
         */
        private const val CURRENT_JOBS_PAGER_TAB_COUNT = 2
    }

    override fun getItemCount(): Int {
        return CURRENT_JOBS_PAGER_TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        //TODO as CurrentJobsFragment changes, so this will be changed
        return CurrentJobsFragment.newInstance()
    }
}