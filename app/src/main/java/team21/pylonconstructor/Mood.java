package team21.pylonconstructor;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.util.Base64;
import android.util.Log;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.SystemUtils;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    private int imageSize;
    private String image;
    private Location location;

    private GoogleMap mMap;
    Toast toast;
    Context context;

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

    // RYAN added null default values
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

    /*public void setDate(String date) {
        DateFormat dateformat = new SimpleDateFormat("DD MM YYYY");  //TODO: decide on date format.

        try {
            this.date = dateformat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace(); //TODO: Actually handle this.
        }
    }*/

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


    public void setTrigger(String trigger) throws ReasonTooLongException {
        /**This snippet was taken from
         * http://stackoverflow.com/questions/8924599/how-to-count-the-exact-number-of-words-in-a-string-that-has-empty-spaces-between
         * on 10th March, 2017 **/
        String trimmed = trigger.trim();
        int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
        if (trigger.length() > 20 || words > 3) {
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


    //TODO: USER PROFILE
    public void setUser(Profile user) {
        this.user = user;
    }

    public Profile getUser() {
        return user;
    }

    //TODO: IMAGES
    public void setImage(Bitmap bmp) throws ImageTooLargeException {


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
     *     returns the image decoded back to a bitmap.
     */
    public Bitmap getImage() {
        if (this.image != null) {
            Bitmap bp = decodeBase64(this.image);
            return bp;
        } else return null;


    }

    //TODO: LOCATION
    public void setLocation(Context context) {
        /**
         * from: https://developers.google.com/maps/documentation/android-api/location
         * accessed on 20/03/2017
         */
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            int duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, "Location Enabled!", duration);
            toast.show();
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.i("Error", "Halpppp");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i("Error", "panic/cry");
                }
            };
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
                     locationListener, Looper.getMainLooper());
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            this.location = location;
        } else {
            // Show rationale and request permission.
            int duration = Toast.LENGTH_SHORT;
            toast = Toast.makeText(context, "Location not Enabled!", duration);
            toast.show();
        }

    }


    public LatLng getLocation(Location location) {
        LatLng newLatLng = new LatLng(0, 0);
        if (location != null) {
            newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        return newLatLng;
    }

    public int getImageSize() {
        return 0;
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





