package team21.pylonconstructor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


//TODO: logic of how to check if user is logged in...

/**
 * This is the login activity.
 *
 * The app always starts here. There is very simple control flow:
 * -If the user attempts to login with an invalid username, an error message will be displayed.
 * -If the user attempts to login with a valid username, they will progress to MoodFeedActivity.
 * -If the user attempts to register an existing username an error message will be displayed.
 * -If the user creates a new valid username (appropriate characters, etc.), they will progress
 * to the MoodFeedActivity.
 *
 * @see Mood
 * @see MoodFeedActivity
 * @see ElasticSearch
 * @see Profile
 *
 * @version 1.0
 *
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ElasticSearch elasticSearch = new ElasticSearch();

        Button register = (Button) findViewById(R.id.register_user_button);
        Button login = (Button) findViewById(R.id.login_button);

        //TODO: ensure the username doesn't exist.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userinp);
                String username = editText.getText().toString();

                if (username.matches("[a-zA-Z]+")) {
                    if (elasticSearch.addProfile(new Profile(username))) {
                        Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                        intent.putExtra("Username", username);
                        startActivity(intent);
                    } else {
                        //TODO: Display errors :/
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userinp);
                String username = editText.getText().toString();
                Profile profile = elasticSearch.getProfile(username);

                if (profile != null) {
                    Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                    intent.putExtra("Username", username);
                    startActivity(intent);
                } else  {
                    //TODO: Display errors :/
                }
            }
        });
    }
}
