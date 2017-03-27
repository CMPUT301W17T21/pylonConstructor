package team21.pylonconstructor;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Tests the filter activity
 *
 * Assumes moods already exist with the needed values
 */
@RunWith(AndroidJUnit4.class)
public class FilterTest {

    @Rule
    public ActivityTestRule<MoodFeedActivity> rule = new ActivityTestRule<>(MoodFeedActivity.class);

    /**
     *  Custom click function for clicking invisible buttons
     */
    public static ViewAction customClick() {
        return actionWithAssertions(
                new CustomClick(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER));
    }

    /**
     * Test cases for mood filter
     */
    @Test
    public void moodFilterTest() {
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(customClick());
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
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(customClick());
        onView(withId(R.id.filter_trigger_radio_button)).perform(customClick());
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
        //Check recent week filter
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(customClick());
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
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(customClick());
        onView(withId(R.id.filter_mood_radio_button)).perform(click());
        onView(withId(R.id.cancel)).perform(click());

        //TODO: Check final output
    }
}
