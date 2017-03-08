package team21.pylonconstructor;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase;

import org.junit.Test;
import com.robotium.solo.Solo;

/**
 * Created by Willi_000 on 2017-03-07.
 */

public class UITest extends ActivityInstrumentationTestCase2< /* TODO: Activity */ > {
    private Solo solo;

    public UITest() {
        super(/* TODO: Add activity here */);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void test() {

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
