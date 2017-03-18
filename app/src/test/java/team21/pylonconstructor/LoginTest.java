package team21.pylonconstructor;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import org.junit.Test;
import com.robotium.solo.Solo;

import static team21.pylonconstructor.R.id.fab_plus;
import static team21.pylonconstructor.R.id.fab_updateMood;

/**
 * Created by jeffreyroutledge on 2017-03-13.
 */

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testLogin() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

        solo.enterText((EditText) solo.getView(R.id.userinp), "testUser");
        solo.clickOnButton("Register");
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}