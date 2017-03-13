package team21.pylonconstructor;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Willi_000 on 2017-03-13.
 */
@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityTestRule<MoodFeedActivity> rule = new ActivityTestRule<>(MoodFeedActivity.class);
    
    /**
     * Test search
     */
    @Test
    public void checkSearch() {
        onView(withId(R.id.fab_plus)).perform(click());
        onView(withId(R.id.fab_search)).perform(click());
        onView(withId(R.id.editTextDialogUserInput)).perform(typeText("Hello"),
                closeSoftKeyboard());
    }
}
