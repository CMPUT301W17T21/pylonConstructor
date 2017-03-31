package team21.pylonconstructor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;


import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * This is the main object in use.
 *
 * All mood Entries are encapsulated in Mood objects.
 *
 * The mood class wil verify internally that any given data is valid, and will throw appropriate
 * errors if invalid data is presented.
 *
 * @version 1.0
 *
 */

class Mood {
    private String emoji, situation;
    private String trigger;
    private Date date;
    private Profile user;
    private String image;
    private double latitude;
    private double longitude;

    @JestId
    private String id;

    public Mood(Profile profile) {
        this.user = profile;
        this.emoji = null;
        this.trigger = null;
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mood() {
        this.emoji = null;
        this.trigger = null;
        this.date = new Date();
        this.image = null;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setTrigger(String trigger) throws ReasonTooLongException{
        /**This snippet was taken from
        * http://stackoverflow.com/questions/8924599/how-to-count-the-exact-number-of-words-in-a-string-that-has-empty-spaces-between
        * on 10th March, 2017 **/
        String trimmed = trigger.trim();
        int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
        if(trigger.length() > 20 || words > 3){
            throw new ReasonTooLongException();
        }
        this.trigger = trigger;
    }
    public String getTrigger() {
        return this.trigger;
    }

    public String toString() {
        return this.trigger;
    }


    public void setSituation(String situation) {
        //TODO: check for valid input
        this.situation = situation;
    }
    public String getSituation() {
        return situation;
    }


    public void setUser(Profile user) {
        this.user = user;
    }

    public Profile getUser() {
        return user;
    }

    public void setImage(Bitmap bmp)  throws ImageTooLargeException{

        if (bmp == null) {
            this.image = null;
            return;
        }

        String encoded = encodeToBase64(bmp, Bitmap.CompressFormat.JPEG, 100);

        int bytecount = encoded.getBytes().length;
        Log.d("STATE", Integer.toString(bytecount));
        if (bytecount > 66636) {
            throw new ImageTooLargeException();
        } else {
            this.image = encoded;
        }

    }

    /**
     * returns the image decoded back to a bitmap.
     * @return bitmap, or null if stored image is null.
     */
    public Bitmap getImage() {
        if (this.image != null) {
            Bitmap bp = decodeBase64(this.image);
            return bp;
        }
        else return null;


    }

    public void setLocation(Double lat, Double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
/*
    public LatLng getLatLng() {
        LatLng newLatLng = new LatLng(0, 0);
        if (location != null) {
            newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return newLatLng;
    }
*/

    //http://stackoverflow.com/questions/185937/overriding-the-java-equals-method-quirk
    //March 17th 2017, Joshua did this.
    //Don't think we'l' have issues but this looks really safe in case.
    @Override
    public boolean equals(Object other) {
        if (this.id == null) return false;
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Mood)) return false;
        if (((Mood) other).getId() == null) return false;
        if (this.getId().equals(((Mood) other).getId())) return true;
        return false;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}



