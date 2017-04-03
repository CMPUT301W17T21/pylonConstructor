package team21.pylonconstructor;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests the mood history activity
 *  1. Check to ensure all parts of a mood event exist
 *
 * User Stories Tested: US 01.01.01, US 01.04.01
 *  -Mood event shows all details of a mood
 *
 * TODO: US 01.03.01, US 01.05.01, US 01.06.01, US 04.01.01
 *  -Cannot figure out how to click a menu inside a imageview
 *      which is in a recycer view (US 01.05.01, US 01.06.01)
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class MoodHistoryTest
{
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Add a mood and ensure everything is in place
     *  -Mood is happy
     */
    @Test
    public void checkMood() {
        testHelper.logUserIn();
        testHelper.addSimpleHappy();

        //Ensures the right mood (happy)
        //Code adapted from:
        // https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(testHelper.getUsername() + " is feeling HAPPY"))));

        //Ensure the emoji is correct (happy)
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.emoji))));

        //Ensure there is a spot for trigger
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));

        //Ensure room for social situation
        onView(withId(R.id.recycler_view)).
                check(matches(hasDescendant(withId(R.id.social_situation_mood_display))));

        //Ensure room for image
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.thumbnail))));

        //Ensure overflow
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.overflow))));

        //Ensure there is a date
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.dt))));
    }

    /**
     * Delete mood, and ensure it is deleted
     */
    @Test
    public void deleteCheck() {
        testHelper.logUserIn();
        testHelper.addSimpleHappy();

        //Press on the overflow button
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.overflow))));

        //Code adapted from:
        //  http://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, CustomViewAction.clickChildViewWithId(R.id.overflow)));
        //onView(withId(R.id.recycler_view)).perform(
        //        RecyclerViewActions.actionOnItemAtPosition(
        //                0, CustomViewAction.clickChildViewWithId(R.id.action_delete_mood)));
        //onView(withId(R.id.action_delete_mood)).check(matches(isDisplayed()));
        //onView(withText("Delete mood")).perform(testHelper.customClick());

    }


    /**
     * Edit mood, and ensure it is changed
     */
    @Test
    public void editCheck() {
        //TODO Check Mood
    }
}
