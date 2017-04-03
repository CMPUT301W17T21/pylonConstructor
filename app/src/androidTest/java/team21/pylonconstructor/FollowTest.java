package team21.pylonconstructor;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static team21.pylonconstructor.TestHelper.customClick;

/**
 * Tests the follwer, following and request activities
 *  1. Followers
 *  2. Following
 *  3. Requests
 *
 * Also tests filters for mood feed
 *  1. Filter by mood
 *  2. Filter by trigger
 *  3. Filter by time
 *
 * User Stories Tested: US 05.01.01, US 05.02.01, US 05.03.01, US 05.04.01, US 05.05.01, US 05.06.01
 *  - Followers see each others moods (US 05.01.01)
 *  - Can accept or decline
 *  - Mood feed can be filtered
 *  - Most recent added mood is displayed
 *
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *  3. Two new users, with no followers, following or pending requests
 *  4. Filter works as expected
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class FollowTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Test follower
     *  -Sending a request
     *  -Accepting a request
     *  -Checking to ensure there is a following
     *  -Check to ensure follower
     */
    @Test
    public void FollowerTest() {
        String userName;
        String secondUser;

        //Login
        testHelper.logout();
        testHelper.newUser("First");
        testHelper.logUserIn();
        userName = testHelper.getUsername();

        //Login into second user
        testHelper.logout();
        testHelper.newUser("Second");
        testHelper.logUserIn();
        secondUser = testHelper.getUsername();

        //Send follow request
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_search)).perform(testHelper.customClick());
        onView(withId(R.id.editTextDialogUserInput)).perform(clearText());
        onView(withId(R.id.editTextDialogUserInput)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withText("Request")).perform(click());

        //Logout and Login in to first user to check if request exists
        testHelper.logout();
        testHelper.setUserName(userName);
        testHelper.logUserIn();

        //Go to request activity
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Follow Requests")).perform(click());

        //Accept request
        onView(withId(R.id.accept_btn)).check(matches(isDisplayed()));
        onView(withId(R.id.req_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(
                0, CustomViewAction.clickChildViewWithId(R.id.accept_btn)));

        //Assert request is gone
        // Should be on new account no nothing else should exists
        onView(withId(R.id.accept_btn)).check(doesNotExist());

        //Goes back
        //From http://stackoverflow.com/questions/23985181/click-home-icon-with-espresso
        onView(isRoot()).perform(pressBack());

        //Go to my followers activity and check if new follow shows up
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("My Followers")).perform(click());
        onView(withId(R.id.prf_recycler_view)).check(matches(hasDescendant(withText(secondUser))));

        //Add two mood events on first user
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.mood_history_title_tview)).perform(click());
        testHelper.addSimpleHappy();
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.shameful_button)).perform(click());
        onView(withId(R.id.message)).perform(typeText("Second"), closeSoftKeyboard());
        onView(withId(R.id.add_mood_event)).perform(click());

        //Check the second user's Following
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Following")).perform(click());
        onView(withId(R.id.prf_recycler_view)).check(matches(hasDescendant(withText(userName))));

        //Check the second user's feed (Should see new mood event)
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.mood_history_title_tview)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withId(R.id.emoji))));

        //Check Filter by mood
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());;
        onView(withId(R.id.filter_mood_radio_button)).perform(testHelper.customClick());
        onView(withId(R.id.happy_button)).perform(click());;
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant
                (withText(userName + " is feeling HAPPY"))));
        onView(withId(R.id.recycler_view)).check(matches(not(hasDescendant(withText("Second")))));
        onView(withId(R.id.clearfilter)).perform(click());

        //Check Filter by trigger
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());;
        onView(withId(R.id.filter_trigger_radio_button)).perform(testHelper.customClick());
        onView(withId(R.id.message)).perform(typeText("Second"), closeSoftKeyboard());
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("Second"))));
        onView(withId(R.id.recycler_view)).check(matches(not(hasDescendant
                (withText(userName + " is feeling HAPPY")))));
        onView(withId(R.id.clearfilter)).perform(click());

        //Check filter by week
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_filter)).perform(testHelper.customClick());;
        onView(withId(R.id.filter_week_radio_button)).perform(customClick());
        onView(withId(R.id.filter)).perform(click());
        onView(withId(R.id.recycler_view)).check(matches(hasDescendant(withText("Second"))));
        onView(withId(R.id.clearfilter)).perform(click());

        testHelper.logout();
    }


    /**
     * Cancel the search.
     *  -No follow request sent
     */
    @Test
    public void cancelRequest() {
        String userName;
        String secondUser = "FollowerTest";

        //Login
        testHelper.logUserIn();
        userName = testHelper.getUsername();

        //Login into second user
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();

        //Cancel follow request
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_search)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_search)).perform(testHelper.customClick());
        onView(withId(R.id.editTextDialogUserInput)).perform(clearText());
        onView(withId(R.id.editTextDialogUserInput)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withText("Cancel")).perform(click());

        //Check if still on mood history activity
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));

        //Ensure no follower
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Following")).perform(click());
        onView(withId(R.id.prf_recycler_view)).check(matches(not(hasDescendant(withText(userName)))));
    }
}