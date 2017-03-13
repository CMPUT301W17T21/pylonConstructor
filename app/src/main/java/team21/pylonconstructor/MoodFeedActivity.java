//ryan p: DONE: EXPANDING FLOATING ACTION BUTTON, addmood FAB already redirects to a mood
// editor.
//TODO: implement other FAB, CREATE MOODSLIST LISTVIEW.

package team21.pylonconstructor;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MoodFeedActivity extends AppCompatActivity {
    FloatingActionButton fab_plus, fab_updateMood, fab_search, fab_filter, fab_goToMap;
    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateCounterClockwise;
    boolean isOpen = false;

    private ListView oldMoodsList;
    private ArrayAdapter<Mood> adapter;
    Context context = this;

    //TODO: JOSH, send an instance of moodList to this instance
    private MoodList moodList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Set Custom App bar title, centered */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mood_history_layout);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_updateMood = (FloatingActionButton) findViewById(R.id.fab_updateMood);
        fab_search = (FloatingActionButton) findViewById(R.id.fab_search);
        fab_filter = (FloatingActionButton) findViewById(R.id.fab_filter);
        fab_goToMap = (FloatingActionButton) findViewById(R.id.fab_map);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRotateCounterClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_counterclockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen) {
                    fab_goToMap.startAnimation(FabClose);
                    fab_filter.startAnimation(FabClose);
                    fab_search.startAnimation(FabClose);
                    fab_updateMood.startAnimation(FabClose);
                    fab_plus.startAnimation(FabRotateCounterClockwise);
                    fab_goToMap.setClickable(false);
                    fab_filter.setClickable(false);
                    fab_search.setClickable(false);
                    fab_updateMood.setClickable(false);
                    isOpen = false;
                }

                else {
                    fab_goToMap.startAnimation(FabOpen);
                    fab_filter.startAnimation(FabOpen);
                    fab_search.startAnimation(FabOpen);
                    fab_updateMood.startAnimation(FabOpen);
                    fab_plus.startAnimation(FabRotateClockwise);
                    fab_goToMap.setClickable(true);
                    fab_filter.setClickable(true);
                    fab_search.setClickable(true);
                    fab_updateMood.setClickable(true);
                    isOpen = true;


                    fab_updateMood.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            setResult(RESULT_OK);
                            Intent intent = new Intent(MoodFeedActivity.this, UpdateMoodActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
        });

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * https://www.mkyong.com/android/android-prompt-user-input-dialog-example/
                 */
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.promt_search_user, null);



                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        String result = userInput.getText().toString();
                                        //TODO: JOSH, find users using result
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();




            }
        });

        fab_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MoodFeedActivity.this, FilterActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO: JOSH, SEND ME AN UPDATED/REFRESHED/FILTERED MOODLIST control.get(moodList)

        //TODO: HALP, implementation of moodList, 'Cannot resolve constructor"??
        //adapter = new ArrayAdapter<Mood>(this, R.layout.list_item, moodList);
        oldMoodsList.setAdapter(adapter);
    }

}
