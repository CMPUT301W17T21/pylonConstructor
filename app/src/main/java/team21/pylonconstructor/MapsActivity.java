package team21.pylonconstructor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
        String key = getIntent().getStringExtra("key");
        ArrayList<Mood> moodArrayList = new ArrayList<Mood>();

        if (key.equals("history")) {
            moodArrayList = Controller.getInstance().getAllMoods();
        } else if (key.equals("feed")) {
            moodArrayList = Controller.getInstance().getAllMoodsFeed();
        }

        for (Mood mood : moodArrayList) {
            if (mood.getLatitude() != 0 && mood.getLongitude() != 0) {
                LatLng moodLatLng = new LatLng(mood.getLatitude(), mood.getLongitude());
                String e = mood.getEmoji();
                BitmapDescriptor icon = null;
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
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.confused_1f615);
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
                mMap.addMarker(new MarkerOptions().position(moodLatLng).title(mood.getTrigger()).icon(icon));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(moodLatLng));
            }
        }
    }
}
