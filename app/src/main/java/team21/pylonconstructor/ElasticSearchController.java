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
public class ElasticSearchController {

    //static means var is shared amongst class objects
    private static JestDroidClient client;

    // A function which adds moods to elastic search
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood).index("g21testing").type("Mood").build();

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

    // A function which gets moods from elastic search
    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // TODO Arrange in reverse Chronological order
            String query = "{\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21testing").addType("Mood").build();

            try {
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

    // A function which gets moods from elastic search
    public static class FilterEmotionalState extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // TODO Arrange in reverse Chronological order
            String query = "{\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\", \"emoji\"],\"query\" :\""+ search_parameters[0]+" AND " +search_parameters[1]+"\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21testing").addType("Mood").build();

            try {
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
