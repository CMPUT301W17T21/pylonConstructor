package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
public class UpdateMoodActivity extends AppCompatActivity {
    Button happyButton, sadButton, angryButton, confusedButton, disgustedButton,
            scaredButton, surpriseButton, shamefulButton, cancelButton, addMoodButton;

    ImageButton socialSituationButton, removePhotoButton, goToCameraButton;

    Bitmap imageBitmap;
    DatePicker datePicker;
    ImageView selectedImage;

    String username;
    Mood mood;

    private TextView selectedMoodTextView;
    private EditText triggerEditText;

    Toast toast;
    Context context;

    CheckBox locationCheckBox;

    private boolean hasImg;



    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);
        selectedMoodTextView = (TextView) findViewById(R.id.selected_mood);
        triggerEditText = (EditText) findViewById(R.id.message);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        selectedImage = (ImageView) findViewById(R.id.selected_photo);
        hasImg = false;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

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

            triggerEditText.setText(trig);

            String situ = getIntent().getExtras().getString("situ");
            mood.setSituation(situ);


            Date dt = new Date();
            dt.setTime(getIntent().getLongExtra("date",-1));
            mood.setDate(dt);
            img = getIntent().getParcelableExtra("image");

            if (img != null) {
                try {
                    mood.setImage(img);
                    selectedImage.setImageBitmap(img);
                } catch (ImageTooLargeException e) {
                    /***
                     * REFACTORING
                     *
                     * Unhandled exception, need toast.
                     */
                }
            }
            removePhotoButton = (ImageButton) findViewById(R.id.remove_photo_button);
            hasImg = hasImage(selectedImage);
            changeRemovePhotoVisibility(hasImg);
        }

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

        socialSituationButton = (ImageButton) findViewById(R.id.add_social_situation);
        socialSituationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: goto social user selector

            }
        });

        locationCheckBox = (CheckBox) findViewById(R.id.checkBox3);
        locationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: LOCATION INCLUDE (p5)
            }
        });

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


                if (validMood){
                    if( edt == 1){
                        Controller.getInstance().editMood(mood);
                        finish();
                    }
                    else {
                        Controller.getInstance().addMood(mood);
                        finish();
                    }
                }
            }
        });
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
}
