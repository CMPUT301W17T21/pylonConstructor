//ryan p: DONE: EXPANDING FLOATING ACTION BUTTON, addmood FAB already redirects to a mood
// editor.
//TODO: implement other FAB, CREATE MOODSLIST LISTVIEW.

package team21.pylonconstructor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

/**
 * This is the main view activity. All mood entries for a logged in user are displayed as a list
 * here.
 *
 * From this activity the user can create, edit and delete moods.
 * The user can also search for other users, filter the history, and eventually navigate to other
 * views.
 *
 * @see Profile
 * @see ElasticSearch
 * @see MoodAdapter
 *
 * @version 1.0
 */
public class MoodFeedActivity extends AppCompatActivity {

    FloatingActionButton fab_plus, fab_updateMood, fab_search, fab_filter, fab_goToMap;
    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateCounterClockwise;
    boolean isOpen = false;
    private MoodAdapter adapter;
    Context context = this;
    private List<Mood> moodList;

    ElasticSearch elasticSearch;
    Profile profile;

    private RecyclerView recyclerView;

    @Override
    //* Called when the activity is first created. */
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Getting users' login info from first time log in
         */
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        elasticSearch = new ElasticSearch();
        this.profile = elasticSearch.getProfile(username);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_feed);
        moodList = elasticSearch.getmymoods(this.profile);
        adapter = new MoodAdapter(this, moodList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        /* Set Custom App bar title, centered */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mood_feed_layout);


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

                }

            }
        });

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * <a href="https://www.mkyong.com/android/android-prompt-user-input-dialog-example/">
                 * Source site</a>
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

        fab_updateMood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MoodFeedActivity.this, UpdateMoodActivity.class);
                intent.putExtra("Username", profile.getUserName());
                startActivity(intent);
            }
        });

        fab_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MoodFeedActivity.this, FilterActivity.class);
                intent.putExtra("Username", profile.getUserName());
                startActivity(intent);
            }
        });
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.flipButton) {
            finish();
        }
        if (id == R.id.logoutButton) {
            SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", "");
            editor.apply();
            Intent intent = new Intent(MoodFeedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
        moodList = elasticSearch.getmymoods(this.profile);
        adapter = new MoodAdapter(this, moodList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        moodList = elasticSearch.getmymoods(this.profile);
        adapter.notifyDataSetChanged();
    }
}