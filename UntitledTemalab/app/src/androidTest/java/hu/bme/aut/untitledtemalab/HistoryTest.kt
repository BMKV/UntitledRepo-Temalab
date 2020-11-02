package hu.bme.aut.untitledtemalab

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.navigation.testing.TestNavHostController
import hu.bme.aut.untitledtemalab.features.joblistfeatures.jobhistory.HistoryFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import com.google.common.truth.Truth.assertThat
import androidx.navigation.Navigation

@RunWith(AndroidJUnit4::class)
class HistoryTest {

    @Test
    fun list_item_click_navigates_to_details(){
        val testController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        testController.setGraph(R.navigation.nav_main)

        val historyScenario = launchFragmentInContainer<HistoryFragment>()

        historyScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), testController)
            }

        //TODO testing the click on the RecyclerView's child

        assertThat(testController.currentDestination?.id).isEqualTo(R.id.jobDetailsFragment)
        }
    }