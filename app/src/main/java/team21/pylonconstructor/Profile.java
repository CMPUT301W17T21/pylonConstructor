package team21.pylonconstructor;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * This class is used to represent profiles. User profiles are identified by a username,
 * and contain a list of followers and following.
 *
 * profiles must be username unique, and each username must be valid under to be specified
 * constraints.
 *
 * Profiles are stored on ElasticSearch.
 */
class Profile {
    String userName;
    ArrayList<Profile> followers;
    ArrayList<Profile> following;

    @JestId
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Profile () {
        followers = new ArrayList<>();
        following = new ArrayList<>();
    }

    public Profile (String userName) {
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }


    public void addFollowers(Profile follower) {
        followers.add(follower);
    }
    public ArrayList<Profile> getFollowers() {
        return followers;
    }

    public void addFollowing(Profile follow) {
        following.add(follow);
    }
    public ArrayList<Profile> getFollowing() {
        return following;
    }

}
