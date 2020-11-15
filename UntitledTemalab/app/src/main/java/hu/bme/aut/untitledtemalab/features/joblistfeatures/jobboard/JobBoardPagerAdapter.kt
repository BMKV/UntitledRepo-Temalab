package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class JobBoardPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val userId: Long
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
        /**
         * This variable contains how many tabs are contained by the ViewPager2 instance.
         */
        private const val CURRENT_JOB_TYPE_PAGER_TAB_COUNT = 3
    }

    override fun getItemCount(): Int {
        return CURRENT_JOB_TYPE_PAGER_TAB_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> JobBoardFragment.newInstance(JobBoardFragment.RepresentedJobType.SmallSize, userId)
            1 -> JobBoardFragment.newInstance(JobBoardFragment.RepresentedJobType.MediumSize, userId)
            2 -> JobBoardFragment.newInstance(JobBoardFragment.RepresentedJobType.LargeSize, userId)
            else -> throw IllegalStateException("Such position doesn't exist: $position")
        }
    }
}