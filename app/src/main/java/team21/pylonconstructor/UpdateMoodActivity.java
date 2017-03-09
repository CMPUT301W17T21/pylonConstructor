package team21.pylonconstructor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateMoodActivity extends AppCompatActivity {
    Button happyButton;

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
    }
}
