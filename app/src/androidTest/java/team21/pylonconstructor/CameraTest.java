package team21.pylonconstructor;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Tests camera
 *  -Adding an photo/image to mood
 *  -User decides to add a mood without a photo (Remove photo)
 *
 * User Stories Tested: US 02.02.01
 *  - User can add photo to express the reason for the mood event
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *  3. All photos taken do not exceed the limit from US 02.03.01
 *  4. Assume correct images or no images (Really difficult to assert for images)
 *
 * @author William
 */
public class CameraTest {
    private TestHelper testHelper = new TestHelper();

    //Allows intent usage (for camera)
    @Rule
    public IntentsTestRule<LoginActivity> rule = new IntentsTestRule(LoginActivity.class);

    /**
     * Test adding a photo to a mood
     */
    @Test
    public void addMoodWithPhoto() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        onView(withId(R.id.take_photo_button)).check(matches(isDisplayed()));
        testHelper.camera();

        //Remove Photo Button only exists if image does
        onView(withId(R.id.remove_photo_button)).check(matches(isDisplayed()));

        //Add mood
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());

        //Check the added mood (At top of list view)
        onView(withId(R.id.thumbnail)).check(matches(isDisplayed()));
    }

    /**
     * Tests deleting photo after its taken
     */
    @Test
    public void removePhoto() {
        //Login first
        testHelper.logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        onView(withId(R.id.take_photo_button)).check(matches(isDisplayed()));
        testHelper.camera();

        //Remove Photo Button only exists if image does
        onView(withId(R.id.remove_photo_button)).check(matches(isDisplayed()));

        //Remove photo
        onView(withId(R.id.remove_photo_button)).check(matches(isDisplayed()));
        onView(withId(R.id.remove_photo_button)).perform(click());

        //Remove Photo Button only exists if image does
        onView(withId(R.id.remove_photo_button)).check(matches(not(isDisplayed())));

        //Add mood
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());
    }
}
