package team21.pylonconstructor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * This activity is used to display the moods from the feed and history on Google maps
 * This activity was adopted from https://www.androidtutorialpoint.com/wp-content/uploads/2016/04/MapsActivity.txt
 * and modified as per the requirements of our project.
 * Accessed on 1st April, 2017 by Shivansh
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    private ElasticSearch elasticSearch;
    private Profile profile;
    private Double latitude;
    private Double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final String key = getIntent().getStringExtra("key");
        final String mapEntranceKey = getIntent().getStringExtra("mapEntranceKey");


        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        ArrayList<Mood> moodArrayList = new ArrayList<Mood>();
        String moodLabel = "me";
        Button nearMeButton = (Button) findViewById(R.id.radius_button);
        Button fromFeedHistButton = (Button) findViewById(R.id.from_history_feed);

        if (key.equals("history")) {
            moodArrayList = Controller.getInstance().getAllMoods();
            fromFeedHistButton.setText("MOODS FROM MOOD HISTORY");
            fromFeedHistButton.setBackgroundColor(getResources().getColor(R.color.shameful_color));
        } else if (key.equals("feed")) {
            moodArrayList = Controller.getInstance().getAllMoodsFeed();
            fromFeedHistButton.setText("MOODS FROM MOOD FEED");
            fromFeedHistButton.setBackgroundColor(getResources().getColor(R.color.shameful_color));

        }
        else if (key.equals("nearMe")) {
            latitude = getIntent().getDoubleExtra("lat",0.0);
            longitude = getIntent().getDoubleExtra("lon",0.0);

            nearMeButton.setBackgroundColor(getResources().getColor(R.color.shameful_color));
            if (mapEntranceKey.equals("history")) {
                fromFeedHistButton.setText("MOODS FROM MOOD HISTORY");
            } else if (mapEntranceKey.equals("feed")) {
                fromFeedHistButton.setText("MOODS FROM MOOD FEED");
            }
            try{
                ElasticSearch elasticSearch = new ElasticSearch();
                ArrayList<Mood> Following_Moods = elasticSearch.getfollowingmoods(Controller.getInstance().getProfile());
                for(Mood moods : Following_Moods){
                    Double distance = calculateDistance(moods.getLatitude(),moods.getLongitude());
                    if((distance/1000) < 5){
                        Log.i("I","Mood is found within 5 km");
                        moodArrayList.add(moods);
                    }
                }
            }
            catch (Exception e){
                Log.i("Error", "following moods not found");
            }

        }


        fromFeedHistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                intent.putExtra("key", mapEntranceKey);
                intent.putExtra("mapEntranceKey", mapEntranceKey);
                finish();
                startActivity(intent);
            }
        });

        nearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MapsActivity.class);
                intent.putExtra("key", "nearMe");
                intent.putExtra("mapEntranceKey", mapEntranceKey);
                intent.putExtra("lat", mLastLocation.getLatitude());
                intent.putExtra("lon", mLastLocation.getLongitude());
                finish();
                startActivity(intent);

            }
        });

        for (Mood mood : moodArrayList) {
            if (mood.getLatitude() != 0 && mood.getLongitude() != 0) {
                LatLng moodLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());
                String e = mood.getEmoji();
                BitmapDescriptor icon = null;
                if (key.equals("feed")) {
                    moodLabel = mood.getUser().getUserName();
                }
                if (e.equals("HAPPY")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.happy_263a);
                }
                if (e.equals("SAD")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.sad_2639);
                }
                if (e.equals("ANGRY")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.angry_1f620);
                }
                if (e.equals("CONFUSED")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.confused_1f615);
                }
                if (e.equals("DISGUSTED")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.disgusted_1f616);
                }
                if (e.equals("SCARED")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.scared_1f631);
                }
                if (e.equals("SURPRISED")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.surprised_1f632);
                }
                if (e.equals("SHAMEFUL")){
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.shameful_1f612);
                }
                mMap.addMarker(new MarkerOptions().position(moodLatLng).title(moodLabel).icon(icon));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(moodLatLng));
            }
        }
    }

    public double calculateDistance(Double moodLat, Double moodLong){
        Location destination = new Location("");
        destination.setLatitude(moodLat);
        destination.setLongitude(moodLong);
        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Location start = new Location("");
        start.setLatitude(latitude);
        start.setLongitude(longitude);
        double distance = start.distanceTo(destination);
        Log.d("Distance",distance+"");
        return distance;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}
