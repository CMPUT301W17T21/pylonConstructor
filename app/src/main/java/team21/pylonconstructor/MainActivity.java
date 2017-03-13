package team21.pylonconstructor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * This class is the default main activity.  I don't believe we are using it though.
 */

public class MainActivity extends AppCompatActivity {


    private ArrayList<Mood> mymoods = new ArrayList<Mood>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_feed);
        /*Button saveButton = (Button) findViewById(R.id.fab_plus);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /*Mood mood1 = new Mood();
                mood1.setEmoji("Angry");
                mood1.setSituation("With Two Persons");
                try {
                    mood1.setTrigger("Bear Cat");
                }
                catch (Exception e){
                    Log.i("Error", "Reason exceeds limit");
                }
                mood1.setUser(new Profile("person3"));

                ElasticSearch elasticSearch = new ElasticSearch();
                elasticSearch.addMood(mood1);*/
               /* Profile user = new Profile("Username");
                ElasticSearch elasticSearch = new ElasticSearch();
                mymoods = elasticSearch.getmymoods(user);
                Profile user = new Profile("person3");
                ElasticSearch elasticSearch = new ElasticSearch();
                mymoods = elasticSearch.emotionalstatefilteredmoods(user,"Angry");
                Log.i("mood object",mymoods.get(0).getEmoji()+mymoods.get(0).getTrigger()+mymoods.get(0).getId());

            }
        });*/
    }
}
