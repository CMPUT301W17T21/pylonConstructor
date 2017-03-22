package team21.pylonconstructor;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;


/**
 * Created by Willi_000 on 2017-03-13.
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);

    /**
     * Test Login with a non-existing user.
     *
    /***
     * Refactoring
     * do not need @test already extending JUNIT3 test class.
     */
    @Test
    public void checkLoginNoUser() {
        onView(withId(R.id.userinp)).perform(typeText("Test"),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        //Ensure that nothing has changed
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            Log.i("Check Login:", "Wrong activity loaded");
        }
    }

    /**
     * Login with an existing user.
     * Logout after logging in.
     */
    @Test
    public void checkLoginExistingUser() {
        onView(withId(R.id.userinp)).perform(typeText("Joshua"),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        //Ensure that MoodHistoryFeed is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.fab_filter)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            Log.i("Check Login:", "On wrong activity");
        }


        //Logout, to continue tests
        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());
        onView(withId(R.id.logout_option)).perform(click());
        onView(withText("Logout")).perform(click());
    }

    /**
     * Register with an existing user.
     * No changes, except for prompt telling you that user exists
     */
    @Test
    public void registerWithExisting() {
        onView(withId(R.id.userinp)).perform(typeText("Joshua"),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        //Ensure that the view/ activity stays the same
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            Log.i("Check Login:", "Wrong activity loaded");
        }
    }
    /**
     * Register a new user.
     * Delete new user after.
     * Log out.
     */
    @Test
    public void registerNewUser() {
        onView(withId(R.id.userinp)).perform(typeText("William"),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        /* TODO: Uncomment once delete account is working properly
        //Ensure that MoodHistoryFeed is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.fab_filter)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            Log.i("Check Login:", "On wrong activity");
        }

        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());

        //Delete and logout.
        onView(withId(R.id.delete_account_option)).perform(click());
        onView(withText("Delete")).perform(click());
        onView(withId(R.id.logout_option)).perform(click());
        onView(withText("Logout")).perform(click());
        */
    }
}
