package hu.bme.aut.untitledtemalab.features.joblistfeatures.jobboard

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

class JobBoardContainerFragment : Fragment() {

    private var userId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: JobBoardContainerFragmentArgs by navArgs()
        userId = navArgs.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_job_board_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp2ViewPager.adapter = JobBoardPagerAdapter(childFragmentManager, lifecycle, userId)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Small"
                1 -> "Medium"
                2 -> "Large"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }
}