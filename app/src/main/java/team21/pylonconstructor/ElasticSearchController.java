package team21.pylonconstructor;
import android.os.AsyncTask;
import android.text.BoringLayout;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.nio.channels.ConnectionPendingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
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
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Mood... moods) {
            verifySettings();

            Index index = new Index.Builder(moods[0]).index("g21").type("Mood").build();
            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    moods[0].setId(result.getId());
                    Log.i("Success", "Added your mood!");
                    return true;
                } else {
                    Log.i("Error", "Elastic search was not able to add mood!");
                    return false;
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the moods");
                return false;
            }
        }
    }


    /**
     * Removes a mood from the DB
     */
    public static class DeleteMoodTask extends AsyncTask<Mood, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Mood... moods) {
            verifySettings();
            // Delete the mood
            Delete delete = new Delete.Builder(moods[0].getId()).index("g21").type("Mood").build();
            try {
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    Log.i("Success", "Deleted your mood!");
                    return true;
                }
                else {
                    Log.i("Error", "Elasticsearch failed to delete mood");
                    return false;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Elasticsearch failed to delete mood");
                return false;
            }
        }
    }

    /**
     *  A function which edits moods stored on elastic search
     */
    public static class EditMoodsTask extends AsyncTask<Mood, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Mood... moods) {
            verifySettings();
            //Edit the mood
            Index index = new Index.Builder(moods[0]).index("g21").type("Mood").id(moods[0].getId()).build();

            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    Log.i("Success", "Edited your mood!");
                    return true;
                }
                else{
                    Log.i("Error", "Elastic search was not able to edit mood!");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and edit the mood");
            }
            return false;
        }
    }

    /**
     * A function which gets moods from elastic search
     */
    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = null;

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Mood").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundmoods = result.getSourceAsObjectList(Mood.class);
                    moods = new ArrayList<>();
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
     * A function which gets moods from elastic search
     */
    public static class CheckMoodExistence extends AsyncTask<Mood, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Mood... moods) {

            verifySettings();
            //Get the requested mood
            Get get = new Get.Builder("g21",moods[0].getId()).type("Mood").build();

            try {
                JestResult result = client.execute(get);
                if(result.isSucceeded()){
                    Mood foundmood = result.getSourceAsObject(Mood.class);
                    if(foundmood != null){
                        Log.i("Found", "mood matched: " + foundmood.getUser().getUserName());
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    Log.i("Error", "Search query failed to find any moods that matched!");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return false;
        }
    }

    /**
     *  A function which filters moods from the most recent week from elastic search
     */
    public static class FilterRecentWeekMoods extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            // Most recent week moods arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"filtered\":{\"query\":{\"multi_match\":{\"query\":\""+ search_parameters[0]+"\",\"fields\":[\"user.userName\"]}},\"filter\":{\"range\":{\"date\":{\"gte\":\"now-7d/d\"}}}}}}";

            Search search = new Search.Builder(query).addIndex("g21").addType("Mood").build();

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
                    .addIndex("g21").addType("Mood").build();

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
                    .addIndex("g21").addType("Mood").build();

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
     *  A function which adds profiles to elastic search
     */
    public static class AddProfileTask extends AsyncTask<Profile, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Profile... profiles) {
            verifySettings();

            Index index = new Index.Builder(profiles[0]).index("g21").type("Profile").build();
            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    profiles[0].setId(result.getId());
                    Log.i("Success", "Added your profile!");
                    return true;
                } else {
                    Log.i("Error", "Elastic search was not able to add profile!");
                    return false;
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add profile");
                return false;
            }
        }
    }

    /**
     * Removes a Profile from the DB
     */
    public static class DeleteProfileTask extends AsyncTask<Profile, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Profile... profiles) {
            verifySettings();
            // Delete the Profile
            Delete delete = new Delete.Builder(profiles[0].getId()).index("g21").type("Profile").build();
            try {
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    Log.i("Success", "Deleted your Profile!");
                    return true;
                }
                else {
                    Log.i("Error", "Elasticsearch failed to delete Profile");
                    return false;
                }
            }
            catch (Exception e) {
                Log.i("Error", "Elasticsearch failed to delete Profile");
                return false;
            }
        }
    }

    /**
     *  A function which edits profiles stored on elastic search
     */
    public static class UpdateProfileTask extends AsyncTask<Profile, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Profile... profiles) {
            verifySettings();
            //Edit the profile
            Index index = new Index.Builder(profiles[0]).index("g21").type("Profile").id(profiles[0].getId()).build();

            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    Log.i("Success", "Updated your profile!");
                    return true;
                }
                else{
                    Log.i("Error", "Elastic search was not able to update profile!");
                    return false;
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and update the profile");
                return false;
            }
        }
    }
    /**
     * A function which gets a profile from elastic search
     */
    public static class GetProfileTask extends AsyncTask<String, Void, Profile> {
        @Override
        protected Profile doInBackground(String... search_parameters) {
            verifySettings();
            Profile profile = null;
            // Search for given Username
            String query = "{\"query\":{\"query_string\" :{\"fields\" : [\"userName\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Profile").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    profile = result.getSourceAsObject(Profile.class);
                }
                else{
                    Log.i("Error", "Search query failed to find any Profiles that matched!");
                }
            }
            catch (Exception e) {
                profile = null;
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                //throw new ConnectionPendingException();
            }
            return profile;
        }
    }


    /**
     * A function which gets the most recent mood from a followed user
     */
    public static class GetFollowingMoodsTask extends AsyncTask<String, Void, Mood> {
        @Override
        protected Mood doInBackground(String... search_parameters) {
            verifySettings();

            Mood moods = new Mood();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\"],\"query\" :\""+ search_parameters[0]+"\"}},\"size\": \"1\"}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Mood").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moods = result.getSourceAsObject(Mood.class);
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
     *  A function which filters moods from the most recent week from a followed user
     */
    public static class FilterFeedRecentWeekMoods extends AsyncTask<String, Void, Mood> {
        @Override
        protected Mood doInBackground(String... search_parameters) {
            verifySettings();

            Mood moods = new Mood();

            // Most recent week moods arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\": {\"query_string\": {\"query\": \""+search_parameters[0]+"\",\"fields\": [\"user.userName\"]}},\"filter\": {\"range\" : {\"date\": {\"gte\": \"now-7d/d\"}}},\"size\" : \"1\"}}}";
            Search search = new Search.Builder(query).addIndex("g21").addType("Mood").build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moods = result.getSourceAsObject(Mood.class);
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
     *  A function which filters moods from a followed user from elastic search based on given emotional state
     */
    public static class FilterFeedEmotionalState extends AsyncTask<String, Void, Mood> {
        @Override
        protected Mood doInBackground(String... search_parameters) {
            verifySettings();

            Mood moods = new Mood();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\", \"emoji\"],\"query\" :\""+ search_parameters[0]+" AND " +search_parameters[1]+"\"}},\"size\": \"1\"}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Mood").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moods = result.getSourceAsObject(Mood.class);
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
    public static class FilterFeedTrigger extends AsyncTask<String, Void, Mood> {
        @Override
        protected Mood doInBackground(String... search_parameters) {
            verifySettings();

            Mood moods = new Mood();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"user.userName\", \"trigger\"],\"query\" :\""+ search_parameters[0]+" AND " +search_parameters[1]+"\"}},\"size\": \"1\"}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Mood").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moods = result.getSourceAsObject(Mood.class);
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
     *  A function which filters moods from a followed user from elastic search based on given emotional state
     */
    public static class FilterMoodFromId extends AsyncTask<String, Void, Mood> {
        @Override
        protected Mood doInBackground(String... search_parameters) {
            verifySettings();

            Mood moods = new Mood();

            // Arranged in reverse Chronological order
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\":{\"query_string\" :{\"fields\" : [\"id\"],\"query\" :\""+ search_parameters[0]+"\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Mood").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    moods = result.getSourceAsObject(Mood.class);
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
     *  A function which adds a new notification to elastic search
     */
    public static class AddNotificationTask extends AsyncTask<Notification, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Notification... notifications) {
            verifySettings();

            Index index = new Index.Builder(notifications[0]).index("g21").type("Notification").build();
            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    notifications[0].setId(result.getId());
                    Log.i("Success", "Added your profile!");
                    return true;
                } else {
                    Log.i("Error", "Elastic search was not able to add profile!");
                    return false;
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add profile");
                return false;
            }
        }
    }

    /**
     *  A function which updates notifications stored on elastic search
     */
    public static class UpdateNotificationTask extends AsyncTask<Notification, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Notification... notifications) {
            verifySettings();
            //Edit the profile
            Index index = new Index.Builder(notifications[0]).index("g21").type("Notification").id(notifications[0].getId()).build();

            try {
                // Execute the query
                DocumentResult result = client.execute(index);
                if(result.isSucceeded()){
                    Log.i("Success", "Updated your profile!");
                    return true;
                }
                else{
                    Log.i("Error", "Elastic search was not able to update profile!");
                    return false;
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and update the profile");
                return false;
            }
        }
    }

    /**
     * A function which gets the latest Notification from elastic search
     */
    public static class GetNotificationTask extends AsyncTask<String, Void, ArrayList<Notification>> {
        @Override
        protected ArrayList<Notification> doInBackground(String... search_parameters) {
            verifySettings();
            ArrayList<Notification> notification = new ArrayList<Notification>();
            // Search for given Username
            String query = "{\"sort\" : [{\"date\" : {\"order\" : \"desc\"}}],\"query\": {\"query_string\": {\"query\": \""+search_parameters[0]+"\",\"fields\": [\"user\"]}},\"filter\": {\"range\" : {\"date\": {\"gte\": \"now-1d/d\"}}},\"size\" : \"5\"}}}";
            Search search = new Search.Builder(query)
                    .addIndex("g21").addType("Notification").build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<Notification> ntf = result.getSourceAsObjectList(Notification.class);
                    notification.addAll(ntf);
                }
                else{
                    Log.i("Error", "Search query failed to find any Profiles that matched!");
                }
            }
            catch (Exception e) {
                notification = null;
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                //throw new ConnectionPendingException();
            }
            return notification;
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
