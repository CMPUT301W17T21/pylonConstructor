package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is the activity for updating and creating moods.
 * It implements the "update_mood_layout.xml" layout.
 */
public class UpdateMoodActivity extends AppCompatActivity {
    ImageView mImageView;
    Button happyButton;
    Button sadButton;
    Button angryButton;
    Button confusedButton;
    Button disgustedButton;
    Button scaredButton;
    Button surpriseButton;
    Button shamefulButton;
    Bitmap imageBitmap;
    Mood mood = new Mood();
    private TextView selectedMoodTextView;
    private EditText triggerEditText;
    ImageButton goToCameraButton;

    // should we leave this out for now??
    ImageButton goToGalleryButton;

    Toast toast;
    Context context;


    ImageButton socialSituationButton;

    CheckBox locationCheckBox;
    Button cancelButton;
    Button addMoodButton;


    @Override
    /** Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);
        selectedMoodTextView = (TextView) findViewById(R.id.selected_mood);
        triggerEditText = (EditText) findViewById(R.id.message);




        //TODO: IMPLEMENT THE MOOD OPTIONS & BUTTONS HERE that are laid out in activity_update_mood.xml

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

        goToGalleryButton = (ImageButton) findViewById(R.id.browse_device_for_image_button);
        goToGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: goto gallery

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

                if (mood.getEmoji()== null) {
                    validMood = false;
                    context = getApplicationContext();
                    CharSequence text = "No mood selected. Please select a mood";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }


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
                    CharSequence text = "Image is too large..";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



                if (validMood) {
                    //TODO: JOSH, at this point mood is correct, so controller.add(mood)
                    finish();
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
            mImageView = (ImageView) findViewById(R.id.selected_photo);
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }
    }
}