package team21.pylonconstructor;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * This activity is used to create and modify mood entries.
 *
 * Once the mood fields have been sufficiently filled, the user may create a new mood entry.
 * This class will ensure that the mood is valid, while the mood class will ensure that passed data
 * is valid.
 *
 * Currently on creation the mood is saved directly with ElasticSearch. In the future the mood will
 * be passed to the controller, and the controller will synchronize online and offline behavior.
 *
 * @see Mood
 * @see Profile
 *
 * @version 1.0
 */
public class UpdateMoodActivity extends AppCompatActivity  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Button happyButton, sadButton, angryButton, confusedButton, disgustedButton,
            scaredButton, surpriseButton, shamefulButton, cancelButton, addMoodButton;

    ImageButton socialSituationButton, removePhotoButton, goToCameraButton;

    Bitmap imageBitmap;
    DatePicker datePicker;
    ImageView selectedImage;


    private RadioGroup radioGroup;
    private RadioButton specifySocRdg;
    private RadioButton tagUsersRdg;
    private RadioButton rb;



    String username;
    Mood mood;

    private TextView selectedMoodTextView, socialSituationTextView;
    private EditText triggerEditText;

    Toast toast;
    Context context;

    CheckBox locationCheckBox;

    private boolean hasImg;
    private boolean addLocation = false;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final String TAG = UpdateMoodActivity.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private ArrayList<String> socialSitList;
    private boolean hasSocialSit;
    private  int socialSitOp;



    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);
        selectedMoodTextView = (TextView) findViewById(R.id.selected_mood);
        triggerEditText = (EditText) findViewById(R.id.message);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectedImage = (ImageView) findViewById(R.id.selected_photo);
        socialSituationTextView = (TextView) findViewById(R.id.social_situation_list);
        hasImg = false;
        hasSocialSit = false;

        radioGroup = (RadioGroup) findViewById(R.id.rdg);
        specifySocRdg = (RadioButton) findViewById(R.id.specify_soc_rb);
        tagUsersRdg = (RadioButton) findViewById(R.id.tag_user_rb);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Bitmap img;
        final int edt = getIntent().getExtras().getInt("EDIT");

        username = getIntent().getStringExtra("username");
        mood = new Mood(Controller.getInstance().getProfile());
        String id = getIntent().getStringExtra("id");
        if (id!= null){
            mood.setId(id);
        }

        if (edt == 1) {
            String emoj = getIntent().getExtras().getString("emoj");
            mood.setEmoji(getIntent().getExtras().getString("emoj"));
            selectedMoodTextView.setText(emoj);
            String trig = getIntent().getExtras().getString("trig");
            boolean hasTag = getIntent().getExtras().getBoolean("has_tag");

            triggerEditText.setText(trig);

            ArrayList<String> situ = getIntent().getStringArrayListExtra("situ");
            mood.setSituation(situ);

            if (situ != null && hasTag) {
                setSocialSituationUsersList(situ);
            }

            Date dt = new Date();
            dt.setTime(getIntent().getLongExtra("date",-1));
            mood.setDate(dt);
            img = getIntent().getParcelableExtra("image");

            if (img != null) {
                try {
                    mood.setImage(img);
                    imageBitmap = img;
                    selectedImage.setImageBitmap(img);
                } catch (ImageTooLargeException e) {
                    /***
                     * REFACTORING
                     *
                     * Unhandled exception, need toast.
                     */
                }
            }
        }

        removePhotoButton = (ImageButton) findViewById(R.id.remove_photo_button);
        hasImg = hasImage(selectedImage);
        changeRemovePhotoVisibility(hasImg);

        happyButton = (Button) findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.happy_button_label);
                mood.setEmoji(getString(R.string.happy_button_label));

            }
        });

        sadButton = (Button) findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.sad_button_label);

                mood.setEmoji(getString(R.string.sad_button_label));
            }
        });

        angryButton = (Button) findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.angry_button_label);

                mood.setEmoji(getString(R.string.angry_button_label));


            }
        });

        confusedButton = (Button) findViewById(R.id.confused_button);
        confusedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.confused_button_label);

                mood.setEmoji(getString(R.string.confused_button_label));


            }
        });

        disgustedButton = (Button) findViewById(R.id.disgusted_button);
        disgustedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.disgusted_button_label);
                mood.setEmoji(getString(R.string.disgusted_button_label));


            }
        });

        scaredButton = (Button) findViewById(R.id.scared_button);
        scaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.scared_button_label);
                mood.setEmoji(getString(R.string.scared_button_label));

            }
        });

        surpriseButton = (Button) findViewById(R.id.surprised_button);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.surprised_button_label);
                mood.setEmoji(getString(R.string.surprised_button_label));

            }
        });

        shamefulButton = (Button) findViewById(R.id.shameful_button);
        shamefulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMoodTextView.setText(R.string.shameful_button_label);

                mood.setEmoji(getString(R.string.shameful_button_label));

            }
        });

        goToCameraButton = (ImageButton) findViewById(R.id.take_photo_button);
        goToCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }

        });


        removePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage.setImageDrawable(null);
                imageBitmap = null;
                hasImg = hasImage(selectedImage);
                changeRemovePhotoVisibility(hasImg);
            }
        });

        tagUsersRdg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });

        specifySocRdg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });


        socialSituationButton = (ImageButton) findViewById(R.id.add_social_situation);
        socialSituationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                SocialSituationPickerFragment dialogo = new SocialSituationPickerFragment();
                dialogo.show(fragmentManager, "tagSelect");

            }
        });

        locationCheckBox = (CheckBox) findViewById(R.id.checkBox3);
        locationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addLocation = true;
                } else {
                    addLocation = false;
                }
            }}
        );

        cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        addMoodButton = (Button) findViewById(R.id.add_mood_event);
        addMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trigger = triggerEditText.getText().toString();
                boolean validMood = true;

                if (mood.getEmoji() == null) {
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "No mood selected. Please select a mood";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                /**
                 * set date
                 */
                Date date = getDateFromDatePicker(datePicker);
                mood.setDate(date);


                try {
                    mood.setTrigger(trigger);
                } catch (ReasonTooLongException e) {
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "Trigger message is too long..";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                try {
                    mood.setImage(imageBitmap);
                } catch (ImageTooLargeException e) {
                    validMood = false;
                    CharSequence text = "Image is too large..";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                if (socialSitOp == 2) {
                    mood.setHasTag(true);
                    if (hasSocialSit) {
                        mood.setSituation(socialSitList);
                    } else {
                        mood.setSituation(null);
                    }
                }
                if (socialSitOp == 1) {

                    socialSitList = new ArrayList<String>();
                    String socialSpec = spinner.getSelectedItem().toString();
                    socialSitList.add(socialSpec);
                    mood.setSituation(socialSitList);
                    mood.setHasTag(false);
                }
                if (addLocation) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (mLastLocation != null) {
                        double latitude = mLastLocation.getLatitude();
                        double longitude = mLastLocation.getLongitude();
                        mood.setLocation(latitude, longitude);

                    } else {
                        CharSequence text = "Couldn't get the location. Make sure location is enabled on the device";
                        int duration = Toast.LENGTH_LONG;
                        toast = Toast.makeText(UpdateMoodActivity.this, text, duration);
                        toast.show();
                    }
                }
                if (validMood){
                    if( edt == 1){
                        Controller.getInstance().editMood(mood);
                        if(mood.isHasTag()){
                            for(String user : socialSitList){
                                Notification notification = new Notification(user, Controller.getInstance().getProfile().getUserName(),mood.getId());
                                ElasticSearch elasticSearch = new ElasticSearch();
                                elasticSearch.addNotification(notification);
                            }
                        }
                        finish();
                    }
                    else {
                        Controller.getInstance().addMood(mood);
                        if(mood.isHasTag()){
                            for(String user : socialSitList){
                                Notification notification = new Notification(user, Controller.getInstance().getProfile().getUserName(),mood.getId());
                                ElasticSearch elasticSearch = new ElasticSearch();
                                elasticSearch.addNotification(notification);
                            }
                        }
                        finish();
                    }
                }
            }
        });
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void setSocialSituationUsersList(ArrayList<String> userNameList) {
        int size = userNameList.size();
        int i;
        Log.i("UpdateMoodActivity:", "SocialSituation list:"+ userNameList.toString());

        if (!userNameList.isEmpty()) {
            hasSocialSit = true;
            for (i = 0; i < size; i++) {
                this.socialSitList = userNameList;
                if (i == 0) {
                    socialSituationTextView.setText(userNameList.get(i));
                } else {
                    socialSituationTextView.append(", " + userNameList.get(i));
                }
            }
        }
        else {
            socialSituationTextView.setText("");
            hasSocialSit = false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            selectedImage.setImageBitmap(imageBitmap);
            hasImg = hasImage(selectedImage);
            changeRemovePhotoVisibility(hasImg);

        }
    }
    /**
     * Adapted from http://stackoverflow.com/questions/8409043/getdate-from-datepicker-android
     * accessed 03-12-2017 by rperez
     * @param datePicker
     * @return a java.util.Date
     */
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Checks if inputted ImageView contains an image.
     * From http://stackoverflow.com/questions/9113895/how-to-check-if-an-imageview-is-attached-with-image-in-android
     * accessed on 03-26-2017 by rperez
     * @param view
     * @return whether ImageView is empty
     */
    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }

    private void changeRemovePhotoVisibility(boolean hasImg) {
        if (hasImg) {
            removePhotoButton.setVisibility(View.VISIBLE); //To set visible
        }
        else {
            removePhotoButton.setVisibility(View.GONE); //To set gone
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void rbClick(View v) {
        int rbId = radioGroup.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(rbId);

        if (rb.getText() == getResources().getString(R.string.specify_soc_label)) {
            socialSitOp = 1;
        }

        if (rb.getText() == getResources().getString(R.string.tag_users_label)) {
            socialSitOp = 2;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
