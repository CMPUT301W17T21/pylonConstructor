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

    private String userName = "LoginTest";

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

    private void delete() {
        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());

        //Delete
        onView(withId(R.id.delete_account_option)).perform(click());
        onView(withText("Delete")).perform(click());
    }

    /**
     * Checks if a user has been registered
     *
     * @param userName - A string representing the user profile
     * @return - True if user is registered, False if user is not registered
     */
    private boolean registered(String userName) {
        logout();

        onView(withId(R.id.userinp)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        try {
            //UserName exists
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
            return (true);
        } catch (Exception NoMatchingViewException) {
            //UserName does not exist
            delete();
            logout();
            return (false);
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

        //Delete User if registered
        if(registered(userName)) {
            onView(withId(R.id.userinp)).perform(typeText(userName),
                    closeSoftKeyboard());
            onView(withId(R.id.login_button)).perform(click());

            delete();
            logout();
        }

        //Attempt to login
        onView(withId(R.id.userinp)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        //Ensure that nothing has changed
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    /**
     * Login with an existing user.
     * Logout after logging in.
     */
    //@Test
    public void checkLoginExistingUser() {
        logout();

        //Register a user if the user is not registered
        if(!registered(userName)) {
            onView(withId(R.id.userinp)).perform(typeText(userName),
                    closeSoftKeyboard());
            onView(withId(R.id.register_user_button)).perform(click());

            logout();
        }

        //Attempt to log in with user
        onView(withId(R.id.userinp)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        /*
        //Ensure that MoodHistoryFeed is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            Log.i("CheckLogInExsitingUser:", "Should be in mood activity");

            //Have a non-existing user, so just register a new user
            userName = "RegUser";
            onView(withId(R.id.userinp)).perform(typeText(userName),
                    closeSoftKeyboard());
            onView(withId(R.id.register_user_button)).perform(click());
            checkLoginExistingUser();
        }

*/
        //Logout, to continue tests
        logout();
    }

    /**
     * Register with an existing user.
     * No changes, except for prompt telling you that user exists
     */
    //@Test
    public void registerWithExisting() {
        logout();
        userName = "William";

        onView(withId(R.id.userinp)).perform(typeText(userName),
                closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        //Ensure that the view/ activity stays the same
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            Log.i("RegisterWithExisting:", "Should still be on login activity");

            //Username doesn't exist, and was registered. Retry again.
            registerWithExisting();
        }
    }

    /**
     * Register a new user.
     * Delete new user.
     */
    //@Test
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


        /*
        //Ensure that LoginActivity is loaded by checking for button that exists in that activity
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionError e) {
            Log.i("RegisterNewUser:", "Not on mood activity");
        }
        */
    }
}
