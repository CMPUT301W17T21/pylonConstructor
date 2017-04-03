package team21.pylonconstructor;

import android.util.Log;

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
 *
 * @see ElasticSearch
 *
 * @version 1.0
 */

class Profile {
    String userName;
    ArrayList<String> requests;
    ArrayList<String> followers;
    ArrayList<String> following;

    @JestId
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Profile () {
        requests = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
    }

    public Profile (String userName) {
        requests = new ArrayList<>();
        followers = new ArrayList<>();
        following = new ArrayList<>();
        this.userName = userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }


    public void addRequests(String name) {
        requests.add(name);
    }
    public void removeRequests(String name) {
        requests.remove(name);
        Log.i("DELETED REQ", name);
    }
    public ArrayList<String> getRequests() {
        return requests;
    }

    public void addFollowers(String follow) {
        followers.add(follow);
    }
    public ArrayList<String> getFollowers() {
        return followers;
    }
    public void removeFollower(String name) {
        followers.remove(name);
        Log.i("DELETED USER FOLLOWING", name);
    }

    public void addFollowing(String follow) {
        following.add(follow);
    }
    public ArrayList<String> getFollowing() {
        return following;
    }
    public void removeFollowing(String name) {
        following.remove(name);
        Log.i("DELETED USER FOLLOWING", name);
    }

}
