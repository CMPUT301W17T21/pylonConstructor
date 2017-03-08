package team21.pylonconstructor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by joshuarobertson on 2017-03-03.
 */

class Mood {
    private String emoji, situation;
    private ArrayList<String> trigger; //TODO: initialize in constructor
    private Date date;
    private Profile user;
    private int imageSize;


    public Mood() {
        trigger = new ArrayList<>();
    }
    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setDate(String date) {
        DateFormat dateformat = new SimpleDateFormat("DD MM YYYY");  //TODO: decide on date format.

        try {
            this.date = dateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace(); //TODO: Actually handle this.
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setTrigger(String... trigger) {
        //TODO: check for valid input
        this.trigger.clear();
        for (String s : trigger) this.trigger.add(s);
    }

    public ArrayList<String> getTrigger() {
        return this.trigger;
    }

    public void setSituation(String situation) {
        //TODO: check for valid input
        this.situation = situation;
    }

    public String getSituation() {
        return situation;
    }

    //TODO: USER PROFILE
    public void setUser(Profile user) {
        this.user = user;
    }
    public Profile getUser() {
        return user;
    }



    //TODO: IMAGES
    public void setImage() {
    }
    public void getImage() {
    }
    //TODO: LOCATION
    public void setLocation() {
    }
    public void getLocation() {
    }

    public int getImageSize() {
        return 0;
    }
}
