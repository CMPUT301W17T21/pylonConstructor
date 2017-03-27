package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests the login activity
 *  1. User attempts to login with no registered user
 *  2. User attempts to login with an existing user
 *  3. User attempts to register a new (non-existing) user, and login with new user
 *  4. User attempts to register with an existing user
 *
 * User Stories Tested: US 03.01.01
 *  -Each user must be unique to allow login
 *  -Each user must be registered to login
 *
 * Assumptions:
 *  1. Logout function works correctly
 *  2. Delete account works correctly
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);

    /**
     * Ensures the login activity is actually loaded by:
     *  -If logged in already, logout
     *  -If not logged in, do nothing.
     */
    private void logout() {
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            //The next line of code is a modified version of the code from
            //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
            onView(withContentDescription(getInstrumentation().getTargetContext().
                    getString(R.string.navigation_drawer_open))).perform(click());
            onView(withText("Account Settings")).perform(click());
            onView(withId(R.id.logout_option)).perform(click());
            onView(withText("Logout")).perform(click());
        }
    }

    /**
     * Test Login with a non-existing user.
     *
    /***
     * Refactoring
     * do not need @test already extending JUNIT3 test class.
     */
    @Test
    public void checkLoginNoUser() {
        logout();
        onView(withId(R.id.userinp)).perform(typeText("Test"),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        //Ensure that nothing has changed
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            Log.i("CheckLoginNoUser:", "Not on Login screen.");
        }
    }

    /**
     * Login with an existing user.
     * Logout after logging in.
     */
    @Test
    public void checkLoginExistingUser() {
        logout();
        onView(withId(R.id.userinp)).perform(typeText("William"),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        //Ensure that MoodHistoryFeed is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            Log.i("CheckLogInExsitingUser:", "Should be in mood activity");
        }


        //Logout, to continue tests
        logout();
    }

    /**
     * Register with an existing user.
     * No changes, except for prompt telling you that user exists
     */
    @Test
    public void registerWithExisting() {
        logout();
        onView(withId(R.id.userinp)).perform(typeText("William"),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        //Ensure that the view/ activity stays the same
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            Log.i("RegisterWithExisting:", "Should still be on login activity");
        }
    }

    /**
     * Register a new user.
     * Delete new user.
     */
    //TODO Uncomment once delete works @Test
    public void registerNewUser() {
        logout();
        onView(withId(R.id.userinp)).perform(typeText("RegUser"),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        //Ensure that MoodHistoryFeed is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            Log.i("RegisterNewUser:", "Not on mood activity");
        }

        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());

        //Delete
        onView(withId(R.id.delete_account_option)).perform(click());
        onView(withText("Delete")).perform(click());

        //Ensure that LoginActivity is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            Log.i("RegisterNewUser:", "Not on mood activity");
        }
    }
}
