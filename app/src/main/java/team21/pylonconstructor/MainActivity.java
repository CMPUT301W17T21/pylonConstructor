package team21.pylonconstructor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ArrayList<Mood> mymoods = new ArrayList<Mood>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                /*Mood mood1 = new Mood();
                mood1.setEmoji("sleepy");
                mood1.setSituation("With Two Persons");
                try {
                    mood1.setTrigger("Bear garden");
                }
                catch (Exception e){
                    Log.i("Error", "Reason exceeds limit");
                }
                mood1.setUser(new Profile("Username"));

                ElasticSearch elasticSearch = new ElasticSearch();
                elasticSearch.addMood(mood1);*/
                Profile user = new Profile("person3");
                Profile user2 = new Profile("Username2");
                Profile user3 = new Profile("Username3");
                ElasticSearch elasticSearch = new ElasticSearch();
                //mymoods = elasticSearch.emotionalstatefilteredmoods(user,"Happy");
                elasticSearch.addProfile(user);
                elasticSearch.addProfile(user2);
                elasticSearch.addProfile(user3);
                //Profile user4 = elasticSearch.getProfile("person3");
                elasticSearch.deleteProfile(elasticSearch.getProfile("person3"));
                //mymoods = elasticSearch.triggerfilteredmoods(user,"Bear");
                //mymoods = elasticSearch.getmymoods(user);
                /*for (Mood mood: mymoods){
                    Log.i("Mood Date", mood.getDate().toString());
                }*/
                /*Profile user = new Profile("Username");
                ElasticSearch elasticSearch = new ElasticSearch();*/
                /*mymoods = elasticSearch.emotionalstatefilteredmoods(user,"Angry");
                //mymoods = elasticSearch.triggerfilteredmoods(user,"Bear");
                Log.i("mood object",mymoods.get(0).getEmoji()+mymoods.get(0).getTrigger()+mymoods.get(0).getId());
                elasticSearch.deleteMood(mymoods.get(0));
                /*mymoods.get(0).setEmoji("Confused");
                mymoods.get(0).setUser(new Profile("user1"));
                elasticSearch.editMood(mymoods.get(0));
                Log.i("mood object",mymoods.get(0).getEmoji()+mymoods.get(0).getTrigger()+mymoods.get(0).getId());*/

            }
        });
    }
}
