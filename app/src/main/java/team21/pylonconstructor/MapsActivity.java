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
        final String key = getIntent().getStringExtra("key");
        final String mapEntranceKey = getIntent().getStringExtra("mapEntranceKey");
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
            nearMeButton.setBackgroundColor(getResources().getColor(R.color.shameful_color));
            if (mapEntranceKey.equals("history")) {
                fromFeedHistButton.setText("MOODS FROM MOOD HISTORY");
            } else if (mapEntranceKey.equals("feed")) {
                fromFeedHistButton.setText("MOODS FROM MOOD FEED");

                //TODO: ESEARCH QUERY WITHIN 5 KM HERE
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
                mMap.addMarker(new MarkerOptions().position(moodLatLng).title(moodLabel).icon(icon));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(moodLatLng));
            }
        }
    }
}
