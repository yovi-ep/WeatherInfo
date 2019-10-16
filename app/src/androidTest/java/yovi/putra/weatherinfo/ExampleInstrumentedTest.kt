package yovi.putra.weatherinfo

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import yovi.putra.weatherinfo.view.MainActivity

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule
    @JvmField var activity = ActivityTestRule(MainActivity::class.java)

    @Test
    fun swipeRefreshResult() {
        onView(withId(R.id.swiperefresh)).perform(swipeDown())

        onView(withId(R.id.rv_hourly)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.rv_daily)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
    }
}
