package team21.pylonconstructor;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by Willi_000 on 2017-03-13.
 */
@RunWith(AndroidJUnit4.class)
public class UpdateMoodTest {
    private TestHelper testHelper = new TestHelper();

    @Rule
    public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Test Adding new mood
     */
    @Test
    public void checkMood() {
        //Login first
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());

        //TODO Check output, and test other mood
    }

    /**
     * Test Adding new mood with trigger
     */
    @Test
    public void withTrigger() {
        //Login first
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Add message
        onView(withId(R.id.message)).check(matches(isDisplayed()));
        onView(withId(R.id.message)).perform(typeText("UpdateTest"),
                closeSoftKeyboard());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
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
    @Test
    public void changeDate() {
        //Login first
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Change date
        //Code adapted from: http://stackoverflow.com/questions/30597680/android-epresso-datepicker-click-on-ok-add-a-year-instead-of-validate
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(scrollTo(), PickerActions.setDate(2017, 04, 03));
        onView(withId(R.id.datePicker)).perform(click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());
    }

    //TODO Check Change Time
    //@Test
    public void changeTime() {
        //Login first
        testHelper.logUserIn();

        //Click on the plus button
        onView(withId(R.id.fab_plus)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_plus)).perform(click());

        //Click on update mood button
        onView(withId(R.id.fab_updateMood)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab_updateMood)).perform(testHelper.customClick());

        //Select mood
        onView(withId(R.id.happy_button)).check(matches(isDisplayed()));
        onView(withId(R.id.happy_button)).perform(click());

        //Change time
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(scrollTo(), PickerActions.setTime(10, 00));
        onView(withId(R.id.datePicker)).perform(click());

        //Click on add mood
        onView(withId(R.id.add_mood_event)).check(matches(isDisplayed()));
        onView(withId(R.id.add_mood_event)).perform(click());
    }
}
