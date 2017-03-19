package team21.pylonconstructor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class UserSettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView logoutOp = (TextView) findViewById(R.id.logout_option);
        TextView deleteAccountOp = (TextView) findViewById(R.id.delete_account_option);

        logoutOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(0);
                diaBox.show();

            }
        });

        deleteAccountOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(1);
                diaBox.show();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is a one instance object of an AlertDialog that asks user for confirmation
     * to delete the currently selected record
     * From: http://stackoverflow.com/questions/11740311/android-confirmation-message-for-delete
     * Accessed 02-03-2017
     * @return
     */
    private AlertDialog AskOption(final int optionIndex) {
        String title, positiveOption;
        if (optionIndex == 0) {
            title = "Logout of PylonConstructor?";
            positiveOption = "Logout";
        }

        else {
            title = "Permanently delete account?";
            positiveOption = "Delete";

        }

        AlertDialog myDeletingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle(title)
                .setMessage("You will be sent back to the login screen")

                .setPositiveButton(positiveOption, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (optionIndex == 0) {
                            setResult(RESULT_OK);
                            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", "");
                            editor.apply();
                            Intent intent = new Intent(UserSettingsActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            //TODO: implement delete account here
                        }
                    }

                })

                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myDeletingDialogBox;

    }
}
