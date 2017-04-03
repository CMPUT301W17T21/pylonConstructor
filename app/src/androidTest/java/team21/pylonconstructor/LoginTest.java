package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

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
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    private TestHelper testHelper = new TestHelper();
    private String userName = "LoginTest";

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
         //Check if logged in already, if so logout otherwise do nothing
         try {
             onView(withId(R.id.login_button)).check(matches(isDisplayed()));
         } catch (Exception NoMatchingViewException) {
             testHelper.logout();
         }

         //Find an unregisterd/ new user
         testHelper.newUser(userName);
         userName = testHelper.getUsername();

         //Attempt to login
         onView(withId(R.id.userinp)).check(matches(isDisplayed()));
         onView(withId(R.id.userinp)).perform(typeText(userName),
                closeSoftKeyboard());
         onView(withId(R.id.login_button)).check(matches(isDisplayed()));
         onView(withId(R.id.login_button)).perform(click());

         //Ensure that nothing has changed
         onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    /**
     * Login with an existing user.
     * Logout after logging in.
     */
    @Test
    public void checkLoginExistingUser() {
        //Check if logged in already, if so logout otherwise do nothing
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            testHelper.logout();
        }

        //Find an unregisterd/ new user
        testHelper.newUser(userName);
        userName = testHelper.getUsername();

        //Attempt to log in with user
        onView(withId(R.id.userinp)).check(matches(isDisplayed()));
        onView(withId(R.id.userinp)).perform(typeText(userName), closeSoftKeyboard());
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.login_button)).perform(click());

        //Ensure on mood history or feed activity
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));

        //Logout, to continue tests
        testHelper.logout();
    }

    /**
     * Register with an existing user.
     * No changes, except for prompt telling you that user exists
     */
    @Test
    public void registerWithExisting() {
        userName = "William";
        testHelper.setUserName(userName);
        testHelper.logUserIn(); //Will register user if not registered.
        testHelper.logout();

        onView(withId(R.id.userinp)).check(matches(isDisplayed()));
        onView(withId(R.id.userinp)).perform(typeText(userName), closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).check(matches(isDisplayed()));
        onView(withId(R.id.register_user_button)).perform(click());

        //Ensure that the view/ activity stays the same
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    /**
     * Register a new user.
     * Delete new user.
     */
    //@Test
    public void registerNewUser() {
        //logout();
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
