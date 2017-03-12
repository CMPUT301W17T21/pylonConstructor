package team21.pylonconstructor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateMoodActivity extends AppCompatActivity {
    Button happyButton;
    Button sadButton;
    Button angryButton;
    Button confusedButton;
    Button disgustedButton;
    Button scaredButton;
    Button surpriseButton;
    Button shamefulButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mood);

        //TODO: IMPLEMENT THE MOOD OPTIONS & BUTTONS HERE that are laid out in activity_update_mood.xml

        happyButton=(Button) findViewById(R.id.happy_button);
        happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        sadButton=(Button) findViewById(R.id.sad_button);
        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        angryButton=(Button) findViewById(R.id.angry_button);
        angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        confusedButton=(Button) findViewById(R.id.confused_button);
        confusedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        disgustedButton=(Button) findViewById(R.id.disgusted_button);
        disgustedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scaredButton=(Button) findViewById(R.id.scared_button);
        scaredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        surpriseButton=(Button) findViewById(R.id.surprised_button);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        shamefulButton=(Button) findViewById(R.id.shameful_button);
        shamefulButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
