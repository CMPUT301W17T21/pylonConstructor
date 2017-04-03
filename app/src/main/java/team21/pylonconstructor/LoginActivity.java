package team21.pylonconstructor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private ElasticSearch elasticSearch = new ElasticSearch();
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Controller.getInstance().reset();

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        final String user = sharedPreferences.getString("username", "");

        /**
         * When the app starts it checks if the user has used the app before. If the user has
         * their username was saved and the app will attempt to login with the existing username.
         *
         * This can fail for any number of reasons, but two are most likely.
         *
         * The user is offline.
         * The profile no longer exists.
         *
         */
        if (!user.equals("")) {
            Log.i("LoginActivity", "Pre-existing profile: " + user);
            Log.i("LoginActivity", "Attempting Login with existing profile...");
            try {
                profile = elasticSearch.getProfile(user);
                Log.i("LoginActivity", "Profile exists!");

                if (profile.getUserName().equals(user)) {

                    Controller.getInstance().setProfile(profile);

                    Log.i("LoginActivity", "Starting App...");
                    Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Log.i("LoginActivity", "The found profile username did not match.");
                }
            } catch (Exception e) {
                Log.i("LoginActivity", "ElasticSearch failed to retrieve the profile.");
                Toast.makeText(LoginActivity.this, "You may be offline!", Toast.LENGTH_SHORT).show();
            }
            Log.i("LoginActivity", "Failed to login with existing username.");
        }
        Log.i("LoginActivity", "Proceeding to login screen...");

        Button register = (Button) findViewById(R.id.register_user_button);
        Button login = (Button) findViewById(R.id.login_button);

        /**
         * Register performs two checks.
         *     The first is for valid user input.
         *     The second that the profile does not exist already.
         *
         * If both pass the profile will be created and the user will be logged in.
         * This could fail if the user is offline.
         *
         * If the first check fails the user will be guided through creating a valid username.
         * If the second fails the user will be told to login.
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userinp);
                String username = editText.getText().toString();
                Profile profile = new Profile();

                if (username.isEmpty()) {
                    Log.i("LoginActivity", "User did not enter a username");
                    Toast.makeText(LoginActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                } else if (username.matches("[a-zA-Z]+")) {

                    try {
                        profile = elasticSearch.getProfile(username);
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "You may be offline!", Toast.LENGTH_SHORT).show();
                        Log.i("LoginRegister", "Offline");
                    }

                    if (profile == null) {
                        boolean result = elasticSearch.addProfile(new Profile(username));
                        if (result) {
                            Log.i("LoginRegister", "Created the profile.");

                            Controller.getInstance().setProfile(new Profile(username));

                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();

                            Log.i("LoginActivity", "Proceeding to login screen...");
                            Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("LoginRegister", "Failed to create profile.");
                            Toast.makeText(LoginActivity.this, "Failed to create a Profile. Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.i("LoginRegister", "Profile already exists.");
                        Toast.makeText(LoginActivity.this, "Profile already exists. Log in!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("LoginActivity", "User used invalid characters.");
                    Toast.makeText(LoginActivity.this, "Username should only contain letters!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Logging in performs two checks.
         *     The first check is for valid user input.
         *     The second check is part of the login process, that the profile exists.
         *
         * If both checks pass the user will be logged into the app.
         *
         * If the first fails the user will be guided through prompts to use proper input.
         * If the second fails the user will be told to create a profile.
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.userinp);
                String username = editText.getText().toString();

                Log.i("LoginActivity", "Attempting to login...");
                if (username.isEmpty()) {
                    Log.i("LoginActivity", "User did not enter a username.");
                    Toast.makeText(LoginActivity.this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                } else if (username.matches("[a-zA-Z]+")) {
                    try {
                        //TODO: Check if this ever returns null instead of raising an exception.
                        Profile profile = elasticSearch.getProfile(username);
                        Log.i("LoginActivity", "Profile exists!");

                        if (profile.getUserName().equals(username)) {
                            Log.i("LoginActivity", "Profile username matches!");

                            Controller.getInstance().setProfile(profile);

                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();

                            Log.i("LoginActivity", "Proceeding to login screen...");
                            Intent intent = new Intent(LoginActivity.this, MoodHistoryActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.i("LoginActivity", "Found profile username doesn't match search.");
                        }
                    } catch (Exception e) {
                        Log.i("LoginActivity", "Error Getting Profile");
                        Toast.makeText(LoginActivity.this, "Offline or profile doesn't exist!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("LoginActivity", "User used invalid characters.");
                    Toast.makeText(LoginActivity.this, "Username should only contain letters!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
