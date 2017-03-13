package team21.pylonconstructor;
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.Delete;
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

    /**
     *  A function which adds moods to elastic search
     */
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();

            for (Mood mood : moods) {
                Index index = new Index.Builder(mood).index("g21testing").type("Mood").build();

                try {
                    // Execute the query
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


    /**
     * Removes a mood from the DB
     */
    public static class DeleteMoodTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();
            // Delete the mood
            Delete delete = new Delete.Builder(moods[0].getId()).index("g21testing").type("Mood").build();
            try {
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    Log.i("Success", "Deleted your mood!");
                }
                else {
                    Log.i("Error", "Elasticsearch failed to delete mood");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Elasticsearch failed to delete mood");
            }
            return null;
        }
    }

    /**
     *  A function which edits moods stored on elastic search
     */
    public static class EditMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood... moods) {
            verifySettings();
            //Edit the mood
            Index index = new Index.Builder(moods[0]).index("g21testing").type("Mood").id(moods[0].getId()).build();

                try {
                    // Execute the query
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()){
                        Log.i("Success", "Edited your mood!");

                    }
                    else{
                        Log.i("Error", "Elastic search was not able to edit mood!");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and edit the mood");
                }

            return null;
        }
    }

    /**
     * A function which gets moods from elastic search
     */
     public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
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

    /**
     *  A function which filters moods from elastic search based on given emotional state
     */
    public static class FilterEmotionalState extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\", \"emoji\"],\"query\" :\""+ search_parameters[0]+" AND " +search_parameters[1]+"\"}}}";
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

    /**
     *  A function which filters moods from elastic search based on given trigger
     */
    public static class FilterTrigger extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\", \"trigger\"],\"query\" :\""+ search_parameters[0]+" AND " +search_parameters[1]+"\"}}}";
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


    /**
     * A function that sets up the communication with CMPUT 301 server
     */
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
