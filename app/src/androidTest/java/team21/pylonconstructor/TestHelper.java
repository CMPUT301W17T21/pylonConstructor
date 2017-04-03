package team21.pylonconstructor;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Willi_000 on 2017-03-29.
 */
public class TestHelper {
    private String username = "William";

    public TestHelper() {}

    public String getUsername() {
        return this.username;
    }

    public void setUserName(String name) {
        this.username = name;
    }


    /**
     * Attempts to log in user
     */
    public void logUserIn() {
        try {
            onView(withId(R.id.login_button)).check(matches(isDisplayed()));
            onView(withId(R.id.userinp)).perform(clearText());
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
            onView(withId(R.id.userinp)).perform(clearText());
            onView(withId(R.id.userinp)).perform(typeText(username),
                    closeSoftKeyboard());
            onView(withId(R.id.register_user_button)).perform(click());

            //Check login again
            ensureLoggedIn();
        }
    }

    /**
     * Log the user out of system
     */
    public void logout () {
        //The next line of code is a modified version of the code from
        //  http://stackoverflow.com/questions/27527988/how-do-i-test-the-home-button-on-the-action-bar-with-espresso
        onView(withContentDescription(getInstrumentation().getTargetContext().
                getString(R.string.navigation_drawer_open))).perform(click());
        onView(withText("Account Settings")).perform(click());
        onView(withId(R.id.logout_option)).perform(click());
        onView(withText("Logout")).perform(click());

        //Ensure logged out
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
    }

    /**
     * Ensure on the mood history or mood feed activities
     * Ensure not on filter activity
     */
    public void ensureMood() {
        try {
            onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        } catch (Exception NoMatchingViewException) {
            //Go back to mood history
            onView(withId(R.id.clearfilter)).check(matches(isDisplayed()));
            onView(withId(R.id.clearfilter)).perform(click());
        }
    }

    /**
     * Add a plain happy mood
     *  -Plain meaning no trigger, tags, location, etc
     */
    public void addSimpleHappy() {
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());
    }

    /**
     * Custom click function for clicking invisible buttons
     */
    public static ViewAction customClick() {
        return actionWithAssertions(
                new CustomClick(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER));
    }

    /**
     * Stimulates running the camera
     *
     *  Code adapted from https://guides.codepath.com/android/UI-Testing-with-Espresso
     */
    public void camera() {
        // Create a bitmap we can use for our simulated camera image
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher_code);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera")).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.take_photo_button)).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage("com.android.camera"));
    }
}
