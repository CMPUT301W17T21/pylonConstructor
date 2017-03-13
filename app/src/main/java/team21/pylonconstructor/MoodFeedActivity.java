//ryan p: DONE: EXPANDING FLOATING ACTION BUTTON, addmood FAB already redirects to a mood
// editor.
//TODO: implement other FAB, CREATE MOODSLIST LISTVIEW.

package team21.pylonconstructor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoodFeedActivity extends AppCompatActivity {

    FloatingActionButton fab_plus, fab_updateMood, fab_search, fab_filter, fab_goToMap;
    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateCounterClockwise;
    boolean isOpen = false;
    private RecyclerView oldMoodsList;
    private MoodAdapter adapter;
    Context context = this;
    private List<Mood> moodList;

    //TODO: JOSH, send an instance of moodList to this instance
    private RecyclerView recyclerView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_feed);
        oldMoodsList = (RecyclerView) findViewById(R.id.recycler_view);
        moodList = new ArrayList<>();
        adapter = new MoodAdapter(this, moodList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        //TEST
        Mood mood = new Mood();
        Profile user = new Profile();
        user.setUserName("ejk");
        mood.setEmoji("HAPPY");
        try {
            mood.setTrigger("HOTDAWG");
        } catch (ReasonTooLongException e) {

        }
        mood.setSituation("1 other person");
        mood.setUser(user);
        moodList.add(mood);

        //TEST
        Mood mood2 = new Mood();
        Profile user2 = new Profile();
        user2.setUserName("dsa");
        mood2.setEmoji("HAPPY");
        try {
            mood2.setTrigger("HOTDAWG");
        } catch (ReasonTooLongException e) {

        }
        mood2.setSituation("1 other person");
        mood2.setUser(user2);
        moodList.add(mood2);





        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        try {
            Glide.with(this).load(R.drawable.ic_action_close).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                 * accessed 03/11/2017
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
                                        //TODO: JOSH, filter users using result
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
        adapter = new MoodAdapter(this, moodList);
        oldMoodsList.setAdapter(adapter);
    }

}
