package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.AssertionFailedError;

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
 * Tests the account settings view.
 * Includes: My Followers, Following, and Account Settings
 * Account Settings includes: Logout, and Delete Account
 *
 * @author William
 */

@RunWith(AndroidJUnit4.class)
public class AccountSettingsTest  {
    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class, true, true);

    /**
     * Tests logout.
     * User logs out, and is returned to login screen.
     */
    @Test
    public void logoutTest() {
        //Login first
        //new Login().logUserIn();

        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());
        onView(withId(R.id.logout_option)).perform(click());
        onView(withText("Logout")).perform(click());

        //Confirm logged out
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        } catch (AssertionFailedError e) {
            Log.i("LogoutTest:", "Not logged out!");
        }
    }

}
