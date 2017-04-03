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

public class GetSocialSituationActivity extends AppCompatActivity {
    private ElasticSearch elasticSearch;
    Profile profile = Controller.getInstance().getProfile();
    ProfileAdapter adapter;
    RecyclerView recyclerView;
    private ArrayList<String> followingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_social_situation);
        elasticSearch = new ElasticSearch();

        followingList = elasticSearch.getFollowing(profile.getUserName());


        Log.i("following: ", followingList.toString());

        adapter = new ProfileAdapter(this, followingList);
        recyclerView = (RecyclerView) findViewById(R.id.prf_recycler_view);
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

