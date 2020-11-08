package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalStateException

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
        return CurrentJobsFragment.newInstance(
            when(position){
                0 -> CurrentJobsFragment.RepresentedJobType.AnnouncedJob
                1 -> CurrentJobsFragment.RepresentedJobType.AcceptedJob
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            },
            userId
        )
    }
}