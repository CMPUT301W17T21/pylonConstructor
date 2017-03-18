package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


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
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");
        if (!user.equals("")) {
            Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
            startActivity(intent);
            finish();
        } else {
            final ElasticSearch elasticSearch = new ElasticSearch();

            Button register = (Button) findViewById(R.id.register_user_button);
            Button login = (Button) findViewById(R.id.login_button);

            //TODO: ensure the username doesn't exist.
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) findViewById(R.id.userinp);
                    String username = editText.getText().toString();

                    if (username.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                    } else if (username.matches("[a-zA-Z]+")) {
                        if (elasticSearch.getProfile(username) == null) {
                            boolean result = elasticSearch.addProfile(new Profile(username));
                            if (result) {
                                SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", username);
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed to create a Profile. Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Profile already exists. Log in!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Username should only contain characters!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) findViewById(R.id.userinp);
                    String username = editText.getText().toString();
                    Profile profile = elasticSearch.getProfile(username);

                    if (username.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                    } else if (profile != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Profile does not exist! Please Register.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
