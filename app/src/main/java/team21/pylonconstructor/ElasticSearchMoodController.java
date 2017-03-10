package team21.pylonconstructor;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Shivansh on 2017-03-09.
 */


/**
 * This class was obtained from LonelyTwitter - lab5 repo
 * on 8th March, 2017 and was initially written during the lab session.
 * This has been modified to suite the needs of our project.
 */
public class ElasticSearchMoodController {

    //static means var is shared amongst class objects
    private static JestDroidClient client;

    // TODO we need a function which adds moods to elastic search
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood).index("G21testing").type("mood").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()){
                        mood.setId(result.getId());
                        Log.i("Success", "Added your mood!");

                    }
                    else{
                        Log.i("Error", "Elastic search was not able to add mood!");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the moods");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets moods from elastic search
    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // TODO Build the query
            //String query = "{\"query\" : { \"term\" : { \"message\" : \"" + search_parameters[0] + "\"}}}";
            String query = "{\n\"query\" : {\n\"term\" : { \"message\" : \""+ search_parameters[0] +"\" }\n}\n}\n";
            Search search = new Search.Builder(query)
                    .addIndex("G21testing").addType("mood").build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundmoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundmoods);
                    Log.i("Found", "mood matched!");
                }
                else{
                    Log.i("Error", "Search query failed to find any moods that matched!");

                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return moods;
        }
    }




    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
