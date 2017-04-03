package team21.pylonconstructor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class NotificationsActivity extends AppCompatActivity {
    private ElasticSearch elasticSearch;
    Profile profile = Controller.getInstance().getProfile();
    private ArrayList<Notification> notificationsList;
    NotificationsAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        elasticSearch = new ElasticSearch();
        ArrayList<Notification> notification_List = new ArrayList<>();


        try{
            ArrayList<Notification> notificationsList = elasticSearch.getNotification(profile.getUserName());
            if(notificationsList != null){
                for(Notification ntf : notificationsList){
                    if(ntf != null ){
                        notification_List.add(ntf);
                    }
                }
                Collections.sort(notification_List,Collections.<Notification>reverseOrder());
            }
        }
        catch (Exception e){
            Log.i("Error","Notification could not be obtained!");
        }

        if(notification_List != null){
            Log.i("notifications: ", String.valueOf(notification_List.size()));
            adapter = new NotificationsAdapter(this, notification_List);
            recyclerView = (RecyclerView) findViewById(R.id.ntf_recycler_view);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}