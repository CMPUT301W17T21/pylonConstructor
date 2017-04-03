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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Tests the filter activity
 *  1. Filter by Mood
 *  2. Filter by Trigger
 *  3. Filter by Most Recent Week
 *  4. Tests the cancel button
 *
 * User Stories Tested: US 04.02.01, US 04.03.01, US 04.04.01
 *  -User can filter mood events according to mood, trigger (word), or by most recent week
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class FilterTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Test cases for mood filter
     */
    @Test
    public void moodFilterTest() {
        //Login first
        testHelper.logUserIn();
        testHelper.ensureMood();
        testHelper.addSimpleHappy();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on the filter button
        onView(withId(R.id.fab_filter)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());

        //Click on mood radio button
        onView(withId(R.id.filter_mood_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.filter_mood_radio_button)).perform((click()));

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Filter
        onView(withId(R.id.filter)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());

        //Check for a mood card, and the correct mood added
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(testHelper.getUsername() + " is feeling HAPPY"))));

        //Ensure that another mood isn't showing up
        onView(withId(R.id.recycler_view)).check(matches(not(hasDescendant
                (withText(testHelper.getUsername() + " is feeling SAD")))));

        //Go back to mood history
        onView(withId(R.id.clearfilter)).check(matches(isDisplayed()));
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for trigger filter
     */
    @Test
    public void triggerFilterTest() {
        String trigger = "Hello";

        //Login first
        testHelper.logUserIn();
        testHelper.ensureMood();
        testHelper.addSimpleHappy();

        //Add mood with filter
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());
        onView(withId(R.id.sad_button)).perform(click());
        onView(withId(R.id.message)).perform(typeText(trigger), closeSoftKeyboard());
        onView(withId(R.id.add_mood_event)).perform(click());


        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on the filter button
        onView(withId(R.id.fab_filter)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());

        //Click on trigger radio button and enter text
        onView(withId(R.id.filter_trigger_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.filter_trigger_radio_button)).perform(testHelper.customClick());
        onView(withId(R.id.message)).perform(typeText(trigger),
                closeSoftKeyboard());

        //Filter
        onView(withId(R.id.filter)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());

        //Check correct mood (Sad was added)
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(testHelper.getUsername() + " is feeling SAD"))));

        //Check for correct trigger
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText(trigger))));

        //Ensure some other mood and trigger don't exists
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(not(hasDescendant
                (withText(testHelper.getUsername() + " is feeling HAPPY")))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(not(withText("Hello")))));

        //Go back to mood history
        onView(withId(R.id.clearfilter)).check(matches(isDisplayed()));
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for most recent week filter
     */
    @Test
    public void weekFilterTest() {
        //Login first
        testHelper.logUserIn();
        testHelper.ensureMood();
        testHelper.addSimpleHappy();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on the filter button
        onView(withId(R.id.fab_filter)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());

        //Click the Week Radio Button
        onView(withId(R.id.filter_week_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.filter_week_radio_button)).perform(click());

        //Filter
        onView(withId(R.id.filter)).check(matches(isDisplayed()));
        onView(withId(R.id.filter)).perform(click());

        //Check correct mood (Happy was added)
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(testHelper.getUsername() + " is feeling HAPPY"))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.dt))));

        //Go back to mood history
        onView(withId(R.id.clearfilter)).check(matches(isDisplayed()));
        onView(withId(R.id.clearfilter)).perform(click());
    }

    /**
     * Test case for pressing cancel
     */
    @Test
    public void cancel() {
        //Login first
        testHelper.logUserIn();
        testHelper.ensureMood();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on the filter button
        onView(withId(R.id.fab_filter)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());

        //Click on the mood filter button
        onView(withId(R.id.filter_mood_radio_button)).check(matches(isDisplayed()));
        onView(withId(R.id.filter_mood_radio_button)).perform(click());

        //Cancel the filter
        onView(withId(R.id.cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.cancel)).perform(click());

        //If plus button exists, on the right activity
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
    }
}
