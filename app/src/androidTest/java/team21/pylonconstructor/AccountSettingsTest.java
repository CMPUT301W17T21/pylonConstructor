package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Tests the account settings activity
 *  1. User attempts to logout
 *  2. User attempts to delete account
 *
 * User Stories Tested: None
 *  -No user stories tested
 *
 * Assumptions:
 *  1. TestHelper functions works
 *      @see TestHelper
 *  2. All other functions are working correctly
 *
 * @author William
 */
@RunWith(AndroidJUnit4.class)
public class AccountSettingsTest  {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);

    /**
     * Tests logout.
     *
     * Log user in (if not already logged in), then attempt to logout
     */
    @Test
    public void logoutTest() {
        //Login first
        testHelper.logUserIn();

        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());

        onView(withText("Account Settings")).perform(click());
        onView(withId(R.id.logout_option)).check(matches(isDisplayed()));
        onView(withId(R.id.logout_option)).perform(click());
        onView(withText("Logout")).perform(click());

        //Logged out if the login button exists
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }


    //TODO: Fix delete account.
    /**
     * Delete Account
     *
     * Login with new account, or if logged in already do nothing
     *  Deletes account after.
     */
    //@Test
    public void deleteAccount() {
        //Login first
        testHelper.setUserName("TestDel");
        testHelper.logUserIn();

        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());

        onView(withText("Account Settings")).perform(click());
        onView(withId(R.id.delete_account_option)).check(matches(isDisplayed()));
        onView(withId(R.id.delete_account_option)).perform(click());
        onView(withText("Delete")).perform(click());

        //Deleted if back on the login screen
        //onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }
}
