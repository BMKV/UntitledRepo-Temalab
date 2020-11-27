package hu.bme.aut.untitledtemalab.features.adminstatistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.bme.aut.untitledtemalab.features.adminstatistics.jobstatus.AdminsJobStatusStatisticFragment
import hu.bme.aut.untitledtemalab.features.adminstatistics.userrole.AdminsUserRoleStatisticFragment
import java.lang.IllegalStateException

class AdminStatisticsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val userId: Long) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object{
        private const val ADMIN_STATISTICS_PAGE_COUNT = 2
    }

    override fun getItemCount(): Int {
        return ADMIN_STATISTICS_PAGE_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> AdminsUserRoleStatisticFragment.newInstance(userId)
            1 -> AdminsJobStatusStatisticFragment.newInstance(userId)
            else -> throw IllegalStateException("Such position doesn't exist: $position!")
        }
    }
}