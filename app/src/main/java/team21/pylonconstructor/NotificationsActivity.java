package team21.pylonconstructor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
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
         ArrayList<Notification> notificationsList = new ArrayList<>();


        try {
            Notification notification = elasticSearch.getNotification(profile.getUserName());
            notificationsList.add(notification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i("notifications: ", String.valueOf(notificationsList.size()));


        adapter = new NotificationsAdapter(this, notificationsList);
        recyclerView = (RecyclerView) findViewById(R.id.ntf_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
