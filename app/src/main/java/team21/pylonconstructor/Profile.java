package team21.pylonconstructor;

import java.util.ArrayList;

/**
 * Created by joshuarobertson on 2017-03-03.
 */

class Profile {
    String userName;
    ArrayList<Profile> followers;
    ArrayList<Profile> following;

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
