package team21.pylonconstructor;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

import org.junit.Test;
import com.robotium.solo.Solo;

/**
 * Created by Willi_000 on 2017-03-07.
 */

public class UITest extends ActivityInstrumentationTestCase2<MoodFeedActivity> {
    private Solo solo;

    public UITest() {
        super(MoodFeedActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testMood() {
        solo.assertCurrentActivity("Wrong Activity", MoodFeedActivity.class);
        /** http://stackoverflow.com/questions/33125017/how-to-access-floatingactionmenu-and-floating-action-button-in-robotium
         * accessed on 2017-03-17
         */
        View fab_plus = getActivity().findViewById(R.id.fab_plus);
        solo.clickOnView(fab_plus);

        View fab_updateMood = getActivity().findViewById(R.id.fab_updateMood);
        solo.clickOnView(fab_updateMood);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
