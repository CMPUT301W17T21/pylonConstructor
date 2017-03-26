package team21.pylonconstructor;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class ViewRequestsActivity extends AppCompatActivity {
    RequestAdapter adapter;
    private ArrayList<String> requestList;
    private Profile profile;
    RecyclerView recyclerView;
    ElasticSearch elasticSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        profile = Controller.getInstance().getProfile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests);

        elasticSearch = new ElasticSearch();
        requestList = elasticSearch.getFollowRequests(profile.getUserName());

        adapter = new RequestAdapter(this, requestList);
        recyclerView = (RecyclerView) findViewById(R.id.req_recycler_view);
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