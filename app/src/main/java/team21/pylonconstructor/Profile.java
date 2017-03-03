package team21.pylonconstructor;

/**
 * Created by joshuarobertson on 2017-03-03.
 */

class Profile {
    String userName;

    public Profile () {
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
}
