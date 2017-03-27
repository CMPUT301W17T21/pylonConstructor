package team21.pylonconstructor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Logs the user in
 *  -If no account, register first
 *  -If already logged in, do nothing
 *
 * Tests:
 *  -This is NOT a test class, but rather a helper class
 *
 * Assumptions:
 *  -UI works as expected
 *
 * @author William
 */

public class Login {
    private String username = "William";

    public Login() {}

    /**
     * Attempts to log in user
     */
    public void logUserIn() {
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
            onView(withId(R.id.userinp)).perform(typeText(username),
                    closeSoftKeyboard());
            onView(withId(R.id.login_button)).perform(click());

            //Check if logged in
            ensureLoggedIn();
        } catch (Exception NoMatchingViewException) {
            //Logged in, so do nothing
            return;
        }
    }

    /**
     *  Ensure the user has logged in.
     *  If not, register user and log in
     */
    private void ensureLoggedIn() {
        try {
            onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
            //Logged in. Do nothing
            return;
        } catch (Exception NoMatchingViewException){
            //Register as new user
            onView(withId(R.id.userinp)).perform(typeText(username),
                    closeSoftKeyboard());
            onView(withId(R.id.register_user_button)).perform(click());

            //Check login again
            ensureLoggedIn();
        }
    }
}
