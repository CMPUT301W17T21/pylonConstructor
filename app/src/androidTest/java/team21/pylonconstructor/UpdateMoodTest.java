package team21.pylonconstructor;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Tests the update mood activity
 *  1. Add a simple mood
 *  2. Add a mood with trigger
 *  3. Add a mood with a different date
 *  4. Tests the cancel button
 *  5. Add a mood with current location
 *  6. Add a mood with a social situation
 *
 * User Stories Tested: US 01.01.01, US 02.02.01, US 02.04.01, US 06.01.01
 *  -Mood can be added with requirements in US 01.01.01
 *  -Mood events can have triggers
 *  -Mood can have social situations added
 *  -Can add current location to a mood event
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *  3. Location permissions have been turned on
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class UpdateMoodTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Test Adding new mood
     */
    @Test
    public void checkMood() {
        //Login with a new user
        testHelper.logout();
        testHelper.newUser("NewUserIHope");
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //Check for a mood card, and the correct mood added
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(testHelper.getUsername() + " is feeling HAPPY"))));
    }

    /**
     * Test Adding new mood with trigger
     */
    @Test
    public void withTrigger() {
        //Login first
        testHelper.logout();
        String username = testHelper.getUsername();
        testHelper.setUserName(username);
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.sad_button)).check(matches(isDisplayed()));
        onView(withId(R.id.sad_button)).perform(click());

        //Add message
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).perform(typeText("UpdateTest"),
                closeSoftKeyboard());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(username + " is feeling SAD"))));

        //Check for correct trigger
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("UpdateTest"))));
    }

    /**
     * Tests adding a mood with a social situation
     */
    @Test
    public void withWho() {
        //Login first
        testHelper.logout();
        String username = testHelper.getUsername();
        testHelper.setUserName(username);
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.shameful_button)).check(matches(isDisplayed()));
        onView(withId(R.id.shameful_button)).perform(click());

        //Add message
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).perform(typeText("SocialTest"),
                closeSoftKeyboard());

        //Select a social situation
        //Code adapted from:
        //  http://stackoverflow.com/questions/37615658/espresso-why-dont-spinners-close-after-selection/37683354#37683354
        onView(withId(R.id.specify_soc_rb)).perform(scrollTo(), click());
        onView(withId(R.id.planets_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("two people"))).perform(click());
        onView(withId(R.id.planets_spinner)).check(matches(withSpinnerText(containsString("two people"))));

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.title))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(username + " is feeling SHAMEFUL"))));

        //Check for correct trigger
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("SocialTest"))));
    }

    /**
     * Tests including your current location
     */
    @Test
    public void currentLocation() {
        //Login first
        testHelper.logout();
        String username = testHelper.getUsername();
        testHelper.setUserName(username);
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Add message
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).perform(typeText("LocationTest"),
                closeSoftKeyboard());

        //Set location
        onView(withId(R.id.checkBox3)).perform(scrollTo(), click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //Check for correct trigger
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("LocationTest"))));
    }

    /**
     * Tests changing the date
     */
    @Test
    public void changeDate() {
        //Login first
        testHelper.logout();
        String username = testHelper.getUsername();
        testHelper.setUserName(username);
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Add message
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).perform(typeText("DateTest"),
                closeSoftKeyboard());

        //Change date
        //Code adapted from: http://stackoverflow.com/questions/30597680/android-epresso-datepicker-click-on-ok-add-a-year-instead-of-validate
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(scrollTo(), PickerActions.setDate(2017, 04, 03));
        onView(withId(R.id.datePicker)).perform(click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //Check for correct trigger
        //Code adapted from: https://medium.com/@_rpiel/recyclerview-and-espresso-a-complicated-story-3f6f4179652e
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.trigger))));
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("DateTest"))));
    }
}
