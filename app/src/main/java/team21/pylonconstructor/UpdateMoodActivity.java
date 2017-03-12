package team21.pylonconstructor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateMoodActivity extends AppCompatActivity {
    Button happyButton;
    Button sadButton;
    Button angryButton;
    Button confusedButton;
    Button disgustedButton;
    Button scaredButton;
    Button surprisedButton;
    Button shamefulButton;
    EditText reasonText;
    ImageButton addPhotoButton;
    ImageButton browseDeviceForImageButton;
    Mood myMood = new Mood();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);

        //TODO: IMPLEMENT THE MOOD OPTIONS & BUTTONS HERE that are laid out in activity_update_mood.xml

        happyButton=(Button) findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("happy");
            }
        });

        sadButton=(Button) findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("sad");
            }
        });

        angryButton=(Button) findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("angry");
            }
        });

        confusedButton=(Button) findViewById(R.id.confused_button);
        confusedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("confused");
            }
        });

        disgustedButton=(Button) findViewById(R.id.disgusted_button);
        disgustedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("disgusted");
            }
        });

        scaredButton=(Button) findViewById(R.id.scared_button);
        scaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("scared");
            }
        });

        surprisedButton=(Button) findViewById(R.id.surprised_button);
        surprisedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("surprised");
            }
        });

        shamefulButton=(Button) findViewById(R.id.shameful_button);
        shamefulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMood.setEmoji("shameful");
            }
        });

        reasonText=(EditText) findViewById(R.id.message);
        reasonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addPhotoButton=(ImageButton) findViewById(R.id.take_photo_button);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        browseDeviceForImageButton=(ImageButton) findViewById(R.id.browse_device_for_image_button);
        browseDeviceForImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
