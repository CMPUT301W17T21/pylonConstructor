package team21.pylonconstructor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffreyroutledge on 2017-02-26.
 */

public class TestSychronizer extends ActivityInstrumentationTestCase2 {
    public TestSychronizer(String pkg, Class activityClass) {
        super(pkg, activityClass);
    }

    public void testSync(){
        synchronizer s = new Synchronizer();
        assertNotNull(s.sync());
    }
}
