package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class FilterTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<MoodFeedActivity> rule = new ActivityTestRule<>(MoodFeedActivity.class);

    /**
     * Test cases for mood filter
     */
    @Test
    public void moodFilterTest() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());
        onView(withId(R.id.filter_mood_radio_button)).perform((click()));
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.filter)).perform(click());

        //TODO: Check final output, and other moods
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for trigger filter
     */
    @Test
    public void triggerFilterTest() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());
        onView(withId(R.id.filter_trigger_radio_button)).perform(testHelper.customClick());
        onView(withId(R.id.message)).perform(typeText("Hello"),
                closeSoftKeyboard());
        onView(withId(R.id.filter)).perform(click());

        //TODO: Check final output, and other inputs
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for most recent week filter
     */
    @Test
    public void weekFilterTest() {
        //Login first
        testHelper.logUserIn();

        //Check recent week filter
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());
        onView(withId(R.id.filter_week_radio_button)).perform(click());
        onView(withId(R.id.filter)).perform(click());

        //TODO: Check final output
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for pressing cancel
     */
    @Test
    public void cancel() {
        //Login first
        testHelper.logUserIn();
        
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());
        onView(withId(R.id.filter_mood_radio_button)).perform(click());
        onView(withId(R.id.cancel)).perform(click());

        //TODO: Check final output
    }
}
