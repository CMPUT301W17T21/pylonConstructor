package team21.pylonconstructor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jeffreyroutledge on 2017-02-26.
 */

// ActivityInstrumentationTestCase2 only needed if running on app
public class SychronizerTest {
    public SychronizerTest() {
        super();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSync() throws Exception {
        Profile u = new Profile();

        /**
         * check that the synchronizer is returning a state
         */
        //Synchronizer s = new Synchronizer();
        //assertNotNull(s.sync());

        /**
         * make sure the other syncs don't return null
         */
        //SocialSync ss = new SocialSync();
        //assertNotNull(ss.sync());
        //FeedSync fs = new FeedSync();
        //assertNotNull(fs.sync());
        //UserSync us = new UserSync();
        //assertNotNull(us.sync());

        //TODO: Commented out so that I could run the DataTests

        /**
         * check that social manager stuff doesn't return null when it
         * shouldn't
         */
//        SocialManager sm = new SocialManager();
//        assertNotNull(sm.newRequest("test"));
//        assertNotNull(sm.checkRequest("test"));
//        assertNotNull(sm.checkAllRequests());
//        assertNotNull(sm.viewRequests());

        /**
         * check that get and remove followers actually do that
         */
//        assertTrue(u.getFollowers.contains("test"));
//        sm.removeRequest("test");
//        assertFalse(u.getFollowers.contains("test"));

        /**
         * check that feed manager doesn't return null
         */
//        FeedManager fm = new FeedManager();
//        assertNotNull(fm.updateAllMoods());
//        assertNotNull(fm.updateFollowedMood("test"));
//        assertNotNull(fm.viewFeed());

        /**
         * check that user manager doesn't return null
         */
//        UserManager um = new UserManager();
//        assertNotNull(um.viewHistory());


    }
}
