package team21.pylonconstructor;

import android.app.Activity;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

/**
 * This class handles displaying the users mood history.
 *
 * The user can navigate back to their mood feed from here.
 *
 * @see Profile
 * @see ElasticSearch
 *
 * @version 1.0
 */

public class MoodHistoryActivity extends AppCompatActivity {

    FloatingActionButton fab_plus, fab_updateMood, fab_search, fab_filter, fab_goToMap;
    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateCounterClockwise;
    private MoodAdapter adapter;
    private List<Mood> moodList;
    Button clearFilterButton;
    TextView filteredByText;
    private Toast toast;

    //Controller controller = Controller.getInstance();
    //ElasticSearch elasticSearch;
    Profile profile;

    boolean isOpen = false;
    Context context = this;
    static final int REQUEST_FILTER = 1;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_feed);
        /**
         * Getting users' login info from first time log in
         */
        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        //elasticSearch = new ElasticSearch();
        profile = Controller.getInstance().getProfile();
        moodList = Controller.getInstance().getAllMoods();
        adapter = new MoodAdapter(this, moodList);

        Log.d("ACTIV ST IS", "OnCreate");


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        /* Set Custom App bar title, centered */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.mood_history_layout);

        clearFilterButton = (Button) findViewById(R.id.clearfilter);
        filteredByText = (TextView) findViewById(R.id.filtered_by_label);
        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab_updateMood = (FloatingActionButton) findViewById(R.id.fab_updateMood);
        fab_search = (FloatingActionButton) findViewById(R.id.fab_search);
        fab_filter = (FloatingActionButton) findViewById(R.id.fab_filter);
        fab_goToMap = (FloatingActionButton) findViewById(R.id.fab_map);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRotateClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        FabRotateCounterClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_counterclockwise);

        changeClearFilterVisibility();

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

        fab_updateMood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MoodHistoryActivity.this, UpdateMoodActivity.class);
                intent.putExtra("username", profile.getUserName());
                startActivity(intent);
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

                if (Controller.getInstance().getFilterOption() != 0) {
                    context = getApplicationContext();
                    CharSequence text = "Already in filtered feed. Tap 'Clear Filter' before" +
                            " filtering again.";
                    int duration = Toast.LENGTH_SHORT;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    Intent intent = new Intent(MoodHistoryActivity.this, FilterActivity.class);
                    intent.putExtra("username", profile.getUserName());
                    startActivityForResult(intent, REQUEST_FILTER);
                }
            }
        });

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Controller.getInstance().addFilters(null, 0);
                filteredByText.setText(getResources().getString(R.string.filtered_by));

                moodList = Controller.getInstance().getAllMoods();
                adapter = new MoodAdapter(context, moodList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                changeClearFilterVisibility();
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
        /**
         * Action performed on clicking logout button
         */
        if (id == R.id.logoutButton) {
            SharedPreferences sharedPreferences = getSharedPreferences("userinfo",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", "");
            editor.apply();
            Intent intent = new Intent(MoodHistoryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if(id == R.id.flipButton){
            finish();
            Intent intent = new Intent(MoodHistoryActivity.this, MoodFeedActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
        Log.d("ACTIV ST IS", "onStart");

        changeClearFilterVisibility();

        if (Controller.getInstance().getFilterOption() != 0) {
            moodList = Controller.getInstance().getAllMoods();
        }

        adapter = new MoodAdapter(this, moodList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        Log.d("ACTIV ST IS", "onResume");

        changeClearFilterVisibility();
        //moodList.clear();
        //moodList.addAll(Controller.getInstance().getAllMoods());
        //moodList = Controller.getInstance().getAllMoods();
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ACTIV ST IS", "onActRes");

        switch(requestCode) {
            case (REQUEST_FILTER) : {
                if (resultCode == Activity.RESULT_OK) {

                    Bundle extras = data.getExtras();
                    int filterOption = extras.getInt("filter_option");

                    if (filterOption == 1) {
                        String filterTerm = extras.getString("mood_filter");
                        Controller.getInstance().addFilters(filterTerm, filterOption);
                        filteredByText.append(" Mood - ");
                        String byMood = Controller.getInstance().getFilterTerm();
                        filteredByText.append(byMood);
                        filteredByText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_action_go_to_map,0,0,0);

                    }
                    if (filterOption == 2) {
                        String filterTerm = extras.getString("mood_filter");
                        Controller.getInstance().addFilters(filterTerm, filterOption);
                        filteredByText.append(" Trigger word - ");
                        String byTrigger = Controller.getInstance().getFilterTerm();
                        filteredByText.append(byTrigger);
                    }

                    if (filterOption == 3) {
                        Date filterDate =  (Date) extras.getSerializable("mood_filter");
                        Controller.getInstance().addDateFilter(filterDate, filterOption);
                        //TODO: implement week search here
                        filteredByText.append("Past week only");
                    }

                }
                break;
            }
        }
    }

    private void changeClearFilterVisibility() {
        int filterOp = Controller.getInstance().getFilterOption();

        if (filterOp == 0) {
            clearFilterButton.setVisibility(View.GONE); //To set invisible
            filteredByText.setVisibility(View.GONE);

        }
        else {
            clearFilterButton.setVisibility(View.VISIBLE); //To set visible
            filteredByText.setVisibility(View.VISIBLE);

        }
    }




}