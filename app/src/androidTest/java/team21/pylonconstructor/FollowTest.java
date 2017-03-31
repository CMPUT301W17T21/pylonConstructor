package team21.pylonconstructor;

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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

//TODO: Cancel the serach
//TODO: What if user is already following
@RunWith(AndroidJUnit4.class)
public class FollowTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void FollowTest() {
        String userName;
        String secondUser = "FollowerTest";

        //Login
        testHelper.logUserIn();
        userName = testHelper.getUsername();

        //Login into second user
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();

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
        //TODO: Check the right request
        //onView(withId(R.id.accept_btn)).check(matches(isDisplayed()));
        //onView(withId(R.id.accept_btn)).perform(click());

        //Assert request is gone
        //TODO: What if other requests exist
        onView(withId(R.id.accept_btn)).check(doesNotExist());

        //Goes back
        //From http://stackoverflow.com/questions/23985181/click-home-icon-with-espresso
        onView(isRoot()).perform(pressBack());

        //Go to my followers activity and check if new follow shows up
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("My Followers")).perform(click());
        //TODO: Check if user is following

        //Check the second user's Following
        onView(isRoot()).perform(pressBack());
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();

        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Following")).perform(click());

        //TODO: Check the following
    }

    @Test
    public void declineFollow() {
        String userName;
        String secondUser = "FollowerTest";

        //Login
        testHelper.logUserIn();
        userName = testHelper.getUsername();

        //Login into second user
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();

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

        //Decline request
        //TODO: Check the right request
        //onView(withId(R.id.decline_btn)).check(matches(isDisplayed()));
        //onView(withId(R.id.decline_btn)).perform(click());

        //Assert request is gone
        //TODO: What if other requests exist
        onView(withId(R.id.accept_btn)).check(doesNotExist());

        //Goes back
        //From http://stackoverflow.com/questions/23985181/click-home-icon-with-espresso
        onView(isRoot()).perform(pressBack());

        //Go to my followers activity and check if new follow does not show up
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("My Followers")).perform(click());
        //TODO: Check if user is not following

        //Check the second user's Following
        onView(isRoot()).perform(pressBack());
        testHelper.logout();
        testHelper.setUserName(secondUser);
        testHelper.logUserIn();

        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Following")).perform(click());

        //TODO: Check the following (No user)
    }

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

        //TODO: Check other user for request
    }
}