package team21.pylonconstructor;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Willi_000 on 2017-03-13.
 */
@RunWith(AndroidJUnit4.class)
public class UpdateMoodTest {

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    //To test camera
    //@Rule
   // public IntentsTestRule<UpdateMoodActivity> intentsRule = new IntentsTestRule<>(UpdateMoodActivity.class);


    /**
     *  Custom click function for clicking invisible buttons
     */
    public static ViewAction customClick() {
        return actionWithAssertions(
                new CustomClick(Tap.SINGLE, GeneralLocation.VISIBLE_CENTER, Press.FINGER));
    }

    /**
     * Test Adding new mood
     */
    @Test
    public void checkMood() {
        //Login first
        new Login().logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());

        //TODO Check output, and test other mood
    }

    /**
     * Test Adding new mood with trigger
     */
    @Test
    public void withTrigger() {
        //Login first
        new Login().logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.message)).perform(typeText("UpdateTest"),
                closeSoftKeyboard());
        onView(withId(R.id.add_mood_event)).perform(click());

        //TODO Check output, and test other mood
    }

    //TODO Check WithWho
    public void withWho() {

    }

    //TODO Check Include Current Location
    public void currentLocation() {

    }

    //TODO Check ChangeDate
    public void changeDate() {
        //Login first
        new Login().logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.datePicker)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());
    }

    //TODO Check Change Time
    public void changeTime() {
        //Login first
        new Login().logUserIn();

        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_updateMood)).perform(customClick());
        onView(withId(R.id.happy_button)).perform(click());
        onView(withId(R.id.datePicker)).perform(click());
        onView(withId(R.id.add_mood_event)).perform(click());
    }
}
