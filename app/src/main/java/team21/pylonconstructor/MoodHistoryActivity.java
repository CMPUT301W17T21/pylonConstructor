package team21.pylonconstructor;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MoodHistoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab_plus, fab_updateMood, fab_search, fab_filter, fab_goToMap;
    Animation FabOpen, FabClose, FabRotateClockwise, FabRotateCounterClockwise;
    private MoodHistoryAdapter adapter;
    private List<Mood> moodList;
    Button clearFilterButton;
    TextView filteredByText, userNameHeader;
    Toast toast;
    View bgDimmer, cardDimmer;

    //Controller controller = Controller.getInstance();
    ElasticSearch elasticSearch;
    Profile profile;

    boolean isOpen = false;
    Context context = this;
    static final int REQUEST_FILTER = 1;

    Animation animFadeOut;
    Animation animFadeIn;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        Intent notification_intent = new Intent(MoodHistoryActivity.this, NotificationService.class);
        startService(notification_intent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bgDimmer = findViewById(R.id.background_dimmer);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        userNameHeader = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name_nav_header);
        userNameHeader.setText(username);

        elasticSearch = new ElasticSearch();
        profile = Controller.getInstance().getProfile();
        moodList = Controller.getInstance().getAllMoods();
        adapter = new MoodHistoryAdapter(this, moodList);

        Log.d("ACTIV ST IS", "OnCreate");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //elasticSearch.addNotification(new Notification("Qq","Shivansh","AVsOSHJOBp_VgXF_udJU"));

        /**
         * The following sets a clickable app title, which toggles between mood history and mood feed.
         * adapted from http://stackoverflow.com/questions/24838155/set-onclick-listener-on-action-bar-title-in-android
         * accessed on 03-19-2017 by rperez
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = getLayoutInflater().inflate(R.layout.mood_history_appbar_title_layout, null);
            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.mood_history_title_tview);

            ActionBar.LayoutParams params = new
                    ActionBar.LayoutParams(Gravity.CENTER);

            // Set the on click listener for the title
            customTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("MainActivity", "ActionBar's title clicked.");
                    finish();
                    Intent intent = new Intent(MoodHistoryActivity.this, MoodFeedActivity.class);
                    overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                    startActivity(intent);
                }
            });
            // Apply the custom view
            actionBar.setCustomView(customView, params);
        }




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
                    collapseFAB();
                }

                else {
                    expandFAB();
                }

            }
        });

        fab_updateMood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(MoodHistoryActivity.this, UpdateMoodActivity.class);
                intent.putExtra("username", profile.getUserName());
                collapseFAB();
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

                collapseFAB();
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
                        .setPositiveButton("Request",
                                new DialogInterface.OnClickListener() {
                                    Profile usr;
                                    CharSequence text;
                                    Context ctxt = getApplicationContext();
                                    int duration = Toast.LENGTH_SHORT;


                                    public void onClick(DialogInterface dialog,int id) {
                                        String result = userInput.getText().toString();
                                        try {
                                            usr = elasticSearch.getProfile(result);
                                        } catch (Exception e) {
                                            Log.i("Error", "Failed to get user result");
                                        }

                                        if (usr == null) {
                                            text = "User not found!";
                                            toast = Toast.makeText(ctxt, text, duration);
                                            toast.show();
                                            Log.i("Result:", "User not found!");
                                        }

                                        else {
                                            Log.i("Result:", "User is found!");
                                            ArrayList<String> following = profile.getFollowing();

                                            if(result.equals(profile.getUserName())){
                                                text = "You cannot follow ".concat(usr.getUserName().concat("!"));
                                                toast = Toast.makeText(ctxt, text, duration);
                                                toast.show();
                                            }
                                            else if (following.contains(result)) {
                                                text = "You are already following ".concat(usr.getUserName());
                                                toast = Toast.makeText(ctxt, text, duration);
                                                toast.show();
                                            }
                                            else {
                                                text = "You have sent a follow request to ".concat(usr.getUserName());
                                                elasticSearch.sendRequests(profile.getUserName(), usr.getUserName());
                                                toast = Toast.makeText(ctxt, text, duration);
                                                toast.show();
                                            }
                                        }

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
                    collapseFAB();
                    startActivityForResult(intent, REQUEST_FILTER);
                }
            }
        });

        fab_goToMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MoodHistoryActivity.this, MapsActivity.class);
                intent.putExtra("key", "history");
                intent.putExtra("mapEntranceKey", "history");
                collapseFAB();
                startActivity(intent);
            }
        });

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                Controller.getInstance().addFilters(null, 0);
                filteredByText.setText(getResources().getString(R.string.filtered_by));

                moodList = Controller.getInstance().getAllMoods();
                adapter = new MoodHistoryAdapter(context, moodList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                changeClearFilterVisibility();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.notificationButton) {
            Intent intent = new Intent(MoodHistoryActivity.this, NotificationsActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.view_followers) {
            Intent intent = new Intent(MoodHistoryActivity.this, ViewFollowersActivity.class);
            startActivity(intent);
        } else if (id == R.id.view_following) {
            Intent intent = new Intent(MoodHistoryActivity.this, ViewFollowingActivity.class);
            startActivity(intent);

        } else if (id == R.id.account_settings) {
            Intent intent = new Intent(MoodHistoryActivity.this, UserSettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.follow_requests) {
            Intent intent = new Intent(MoodHistoryActivity.this, ViewRequestsActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    protected void onStart() {
        super.onStart();
        Log.d("ACTIV ST IS", "onStart");

        changeClearFilterVisibility();

        if (Controller.getInstance().getFilterOption() != 0) {
            moodList = Controller.getInstance().getAllMoods();
        }

        adapter = new MoodHistoryAdapter(this, moodList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        Log.d("ACTIV ST IS", "onResume");
        changeClearFilterVisibility();
        //moodList.clear();
        //moodList  = Controller.getInstance().getAllMoods();
        Collections.sort(moodList, Collections.<Mood>reverseOrder());
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
                        filteredByText.append("Past week only");
                    }

                }
                break;
            }
        }
    }

    public void changeClearFilterVisibility() {
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

    public void expandFAB() {
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

        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        bgDimmer.setAnimation(animFadeIn);
        bgDimmer.setVisibility(View.VISIBLE);
    }

    public void collapseFAB() {
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

        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        bgDimmer.setAnimation(animFadeOut);
        bgDimmer.setVisibility(View.INVISIBLE);

    }
}
