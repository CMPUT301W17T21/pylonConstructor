package team21.pylonconstructor;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        String key = getIntent().getStringExtra("key");
        float emoji = BitmapDescriptorFactory.HUE_MAGENTA;

        ArrayList<Mood> moodArrayList = new ArrayList<>();
        if (key.equals("history")) {
            moodArrayList = Controller.getInstance().getAllMoods();
        } else if (key.equals("feed")) {
            moodArrayList = Controller.getInstance().getAllMoodsFeed();
        }
        //TODO @jeff this should probably be in a try/catch block (also maybe check the colors)
        for (Mood mood : moodArrayList) {
            switch (mood.getEmoji()) {
                case ("HAPPY") : {
                    emoji = BitmapDescriptorFactory.HUE_YELLOW;
                    break;
                }
                case ("SAD") : {
                    emoji = BitmapDescriptorFactory.HUE_BLUE;
                    break;
                }
                case ("ANGRY") : {
                    emoji = BitmapDescriptorFactory.HUE_RED;
                    break;
                }
                case ("CONFUSED") : {
                    emoji = BitmapDescriptorFactory.HUE_ROSE;
                    break;
                }
                case ("DISGUSTED") : {
                    emoji = BitmapDescriptorFactory.HUE_GREEN;
                    break;
                }
                case ("SCARED") : {
                    emoji = BitmapDescriptorFactory.HUE_VIOLET;
                    break;
                }
                case ("SURPRISE") : {
                    emoji = BitmapDescriptorFactory.HUE_ORANGE;
                    break;
                }
                case ("SHAMEFUL") : {
                    emoji = BitmapDescriptorFactory.HUE_CYAN;
                    break;
                }
                    //surpriseButton, shamefulButton
            }
            LatLng moodLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());
            mMap.addMarker(new MarkerOptions()
                    .position(moodLatLng)
                    .title(mood.getTrigger())
                    .icon(BitmapDescriptorFactory.defaultMarker(emoji)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(moodLatLng));
        }
    }
}
