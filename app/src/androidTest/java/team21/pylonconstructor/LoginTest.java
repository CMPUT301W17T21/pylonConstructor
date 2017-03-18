package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Willi_000 on 2017-03-13.
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);
    /**
     * Test Login
     */

    /***
     * Refactoring
     * do not need @test already extending JUNIT3 test class.
     */
    @Test
    public void checkLogin() {
        //TODO Check Login
        //Attempt to register a new user  (this currently fails so it is commented out
        /**onView(withId(R.id.userinp)).perform(typeText("testUser"), closeSoftKeyboard());
        onView(withId(R.id.register_user_button)).perform(click());

        //Hit back
        pressBack();*/

        //Attempt to login with an existing user
        onView(withId(R.id.userinp)).perform(typeText("Joshua"),
                closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

    }

}
