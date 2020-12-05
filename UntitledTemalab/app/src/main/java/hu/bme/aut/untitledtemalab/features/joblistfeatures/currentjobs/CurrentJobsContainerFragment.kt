package hu.bme.aut.untitledtemalab.features.joblistfeatures.currentjobs

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

class CurrentJobsContainerFragment : Fragment() {

    private var userId by Delegates.notNull<Long>()

    private var deliverAbility by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navArgs: CurrentJobsContainerFragmentArgs by navArgs()
        userId = navArgs.userId
        deliverAbility = navArgs.canDeliver
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_jobs_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp2ViewPager.adapter = CurrentJobsPagerAdapter(childFragmentManager, lifecycle, userId, deliverAbility)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            if(deliverAbility){
                tab.text = when (position) {
                    0 -> "Announced packages"
                    1 -> "Accepted deliveries"
                    else -> throw IllegalStateException("Such position doesn't exist: $position")
                }
            }else{
                tab.text = when(position) {
                    0 -> "Announced packages"
                    else -> throw IllegalStateException("Such position doesn't exist: $position")
                }
            }
        }.attach()
    }
}