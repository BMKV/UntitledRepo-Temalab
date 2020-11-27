package hu.bme.aut.untitledtemalab.features.adminstatistics

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
 * A simple [Fragment] subclass.
 */
class AdminStatisticsContainerFragment : Fragment() {

    private var userId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: AdminStatisticsContainerFragmentArgs by navArgs()
        userId = navArgs.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_statistics_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp2ViewPager.adapter = AdminStatisticsPagerAdapter(childFragmentManager, lifecycle, userId)
        TabLayoutMediator(tlTabs, vp2ViewPager){ tab, position ->
            tab.text = when(position){
                0 -> "User role stats"
                1 -> "Job status stats"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }

}