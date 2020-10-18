package hu.bme.aut.untitledtemalab.jobhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import hu.bme.aut.untitledtemalab.R
import kotlinx.android.synthetic.main.activity_history.*
import java.lang.IllegalStateException

// TODO: If the history is viewed by a simple, non-transporter user, the transported tab shouldn't
//  appear (No tab should appear to be precise). The implementation of this feature could depend on
//  the backend, so it hasn't been implemented yet.

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        vp2ViewPager.adapter = HistoryPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tlTabs, vp2ViewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Sent packages"
                1 -> "Transported packages"
                else -> throw IllegalStateException("Such position doesn't exist: $position")
            }
        }.attach()
    }
}