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

import java.util.NoSuchElementException;


//TODO: logic of how to check if user is logged in...

/**
 * This is the login activity.
 * <p>
 * The app always starts here. There is very simple control flow:
 * -If the user attempts to login with an invalid username, an error message will be displayed.
 * -If the user attempts to login with a valid username, they will progress to MoodFeedActivity.
 * -If the user attempts to register an existing username an error message will be displayed.
 * -If the user creates a new valid username (appropriate characters, etc.), they will progress
 * to the MoodFeedActivity.
 *
 * @version 1.0
 * @see Mood
 * @see MoodFeedActivity
 * @see ElasticSearch
 * @see Profile
 */
public class LoginActivity extends AppCompatActivity {
    ElasticSearch elasticSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        elasticSearch = new ElasticSearch();
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        final String user = sharedPreferences.getString("username", "");

        if (!user.equals("")) {
            Log.i("Controller", "set a profile: " + user);
            Log.i("Skip", "Attempting...");
            try {
                Profile profile = elasticSearch.getProfile(user);
                Log.i("Skip", "Profile exists!");
                if (profile.getUserName().equals(user)) {
                    Log.i("Skip", "launching...");
                    Controller.getInstance().setProfile(profile);
                    Log.d(Controller.getInstance().getProfile().getUserName(), "Controller: Username");
                    Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                Log.i("Skip", "Failed Try!");
                Toast.makeText(LoginActivity.this, "You may be offline!", Toast.LENGTH_SHORT).show();
            }
        }
        Log.i("LoginActivity", "Proceeding to buttons...");
        final ElasticSearch elasticSearch = new ElasticSearch();

        Button register = (Button) findViewById(R.id.register_user_button);
        Button login = (Button) findViewById(R.id.login_button);

        //TODO: ensure the username doesn't exist.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userinp);
                String username = editText.getText().toString();
                Profile profile = new Profile();

                if (username.isEmpty()) {
                    Log.i("LoginRegister", "Empty Username");
                    Toast.makeText(LoginActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (username.matches("[a-zA-Z]+")) {
                    try {
                        profile = elasticSearch.getProfile(username);
                        Log.i("Login", "Found something!");
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "You may be offline!", Toast.LENGTH_SHORT).show();
                        Log.i("LoginRegister", "Offline");
                    }
                    if (!profile.getUserName().equals(username)) {
                        boolean result = elasticSearch.addProfile(new Profile(username));
                        if (result) {
                            Log.i("LoginRegister", "Created the profile");
                            Log.i("Controller", "set a profile: " + username);
                            Controller.getInstance().setProfile(new Profile(user));
                            Log.d("Controller: Username = ", Controller.getInstance().getProfile().getUserName());
                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("LoginRegister", "Failed to create");
                            Toast.makeText(LoginActivity.this, "Failed to create a Profile. Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                    Log.i("LoginRegister", "Profile Exists");
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
                Log.i("Login", "Attempting...");
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                } else if (username.matches("[a-zA-Z]+")) {
                    try {
                        Profile profile = elasticSearch.getProfile(username);
                        Log.i("Login", "Profile found: "+profile.getUserName());
                        if (profile.getUserName().equals(username)) {
                            Log.i("Login", "Profile exists!");
                            Log.i("Controller", "set a profile: " + profile.getUserName());
                            Controller.getInstance().setProfile(profile);
                            Log.d("Controller: Username = ", Controller.getInstance().getProfile().getUserName());

                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("Login", "Profile doesn't exist!");
                            Toast.makeText(LoginActivity.this, "Doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.i("Login", "Error Getting Profile");
                        Toast.makeText(LoginActivity.this, "Profile does not exist! Please Register.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.i("Login", "Must be only Characters");
                    Toast.makeText(LoginActivity.this, "Username should only contain characters!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
