package team21.pylonconstructor;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by joshuarobertson on 2017-03-03.
 */

/**
 * This class is the profile object for users.  <br>
 * It tracks their followers and who they follow in arraylists.
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
