package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static team21.pylonconstructor.TestHelper.customClick;

/**
 * Tests map
 *
 * User Stories Tested: 06.01.01, 06.02.01, 06.03.01
 * - Users can optionally add their locations to moods
 * - Users can view moods from their mood histories on a map
 * - Users can view moods from their mood feeds on a map
 *
 * @author jeffreyroutledge
 */
@RunWith(AndroidJUnit4.class)
public class MapTest {
    TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Test adding a mood with a location
     */
    @Test
    public void checkLocation() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.checkBox3)).check(matches(isNotChecked())).perform(scrollTo(), click());
        onView(withId(R.id.add_mood_event)).perform(click());
    }

    /**
     * Test Map from mood history
     */
    @Test
    public void checkMap() {
        //TODO Check Map
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_map)).perform(customClick());
    }

    /**
     * Test Map from mood feed
     */
    @Test
    public void checkMapFromFeed() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.mood_history_title_tview)).perform(click());
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_map)).perform(customClick());
    }
}