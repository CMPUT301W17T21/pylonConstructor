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

public class ViewFollowersActivity extends AppCompatActivity {
    private ElasticSearch elasticSearch;
    Profile profile = Controller.getInstance().getProfile();
    ProfileAdapter adapter;
    RecyclerView recyclerView;

    private ArrayList<String> followersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_followers);
        elasticSearch = new ElasticSearch();

        //elasticSearch.acceptRequests(profile.getUserName(),"aaa");

        followersList = elasticSearch.getFollowers(profile.getUserName());


        Log.i("followers: ", followersList.toString());

        adapter = new ProfileAdapter(this, followersList);
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

    protected void onStart() {
        super.onStart();
        Log.d("ACTIV ST IS", "onStart");

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected  void onResume() {
        super.onResume();
        Log.d("ACTIV ST IS", "onResume");

        //moodList.clear();
        //moodList.addAll(Controller.getInstance().getAllMoods());
        //moodList = Controller.getInstance().getAllMoods();
        adapter.notifyDataSetChanged();
    }
}
