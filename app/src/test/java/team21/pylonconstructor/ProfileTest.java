package team21.pylonconstructor;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the profile data class
 *  1. userName
 *  2. requests
 *  3. followers
 *  4. following
 *
 * Tests addition and removal of 2,3 and 4
 *
 * User Stories Tested: None
 *
 * Assumptions:
 *  - None
 *
 * @author William
 */
public class ProfileTest {
    /**
     * Test a new profile
     *
     * @throws Exception
     */
    @Test
    public void createProfile() throws Exception {
        Profile profile = new Profile();
        ArrayList<String> requests = new ArrayList<>();
        ArrayList<String> followers = new ArrayList<>();
        ArrayList<String> following = new ArrayList<>();

        //Username
        profile.setUserName("William");
        assertEquals(profile.getUserName(), "William");

        //Requests
        requests.add(0, "Requests");
        profile.addRequests("Requests");
        assertEquals(profile.getRequests(), requests);

        //Followers
        followers.add("Followers");
        followers.add("Followers2");
        profile.addFollowers("Followers");
        profile.addFollowers("Followers2");
        assertEquals(profile.getFollowers(), followers);

        //Following
        following.add(0, "Following");
        following.add(1, "Following2");
        following.add(2, "Following3");
        profile.addFollowing("Following");
        profile.addFollowing("Following2");
        profile.addFollowing("Following3");
        assertEquals(profile.getFollowing(), following);

        //Remove Request
        requests.remove(0);
        profile.removeRequests("Requests");
        assertEquals(profile.getRequests(), requests);

        //Remove Follower
        followers.remove(0);
        profile.removeFollower("Followers");
        assertEquals(profile.getFollowers(), followers);

        //Removing Following
        following.remove(0);
        profile.removeFollowing("Following");
        assertEquals(profile.getFollowing(), following);
    }
}
