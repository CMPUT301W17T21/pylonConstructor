package team21.pylonconstructor;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jeffreyroutledge on 2017-03-25.
 */

public class MapFragmentActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<Mood> moodList;
    private Location location;
    private LatLng latlng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapFragment mMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        moodList = Controller.getInstance().getAllMoods();

        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));

        for (Mood mood : moodList) {
            location = mood.getLocation();
            latlng = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(mood.getId()));
        }

    }
        /**MarkerOptions markerOptions = new MarkerOptions()
                .position(newLatLng)
                .title(newLatLng.toString());*/
        // Add some markers to the map, and add a data object to each marker.
        /**mPerth = mMap.addMarker(new MarkerOptions()
                .position(PERTH)
                .title("Perth");
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney");
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane");
        mBrisbane.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);*/
    //}

    /** Called when the user clicks a marker. */
    /**@Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }*/

    public void createMarkers(ArrayList<Mood> moodList) {

    }

    public void placeMarkers(ArrayList<Mood> moodList) {

    }


}