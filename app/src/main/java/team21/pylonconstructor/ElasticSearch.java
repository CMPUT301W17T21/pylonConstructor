package team21.pylonconstructor;

import android.util.Log;

import java.nio.channels.ConnectionPendingException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shivansh on 2017-03-10.
 */

/**
 * This class performs all the Elastic Search operations by making use of the
 * ElasticSearchController class. It is a wrapper class that helps views to
 * easily obtain desired information about moods and users and also helps
 * to easily add/edit moods and user profiles.
 *
 * @see ElasticSearchController
 * @version 1.0
 */
public class ElasticSearch {

    private ArrayList<Mood> mymoodsList = new ArrayList<Mood>();

    public ElasticSearch() {}

    /**
     * Adds Moods to the Elastic Search Database
     * @param mood
     * @return true if mood successfully added
     * false otherwise
     */
    public boolean addMood(Mood mood){
        ElasticSearchController.AddMoodsTask addMoodsTask = new ElasticSearchController.AddMoodsTask();
        addMoodsTask.execute(mood);
        try {
            addMoodsTask.get();
            return true;
        }
        catch (Exception e){
            Log.i("Error", "Failed to add mood");
            return false;
        }
    }

    /**
     *  Gets Moods from the Elastic Search Database from a given user
     *
     *  Josh updated this to throw exceptions so he could make sure it actually got the list.
     * @param user
     * @return mymoodsList
     */
    public ArrayList<Mood> getmymoods(Profile user) throws ExecutionException, InterruptedException {
        ElasticSearchController.GetMoodsTask getmyMoods = new ElasticSearchController.GetMoodsTask();
        getmyMoods.execute(user.getUserName());
        mymoodsList = getmyMoods.get();
        return mymoodsList;
    }

    /**
     *  Check for the existance of a mood in Elastic Search Database
     * @param mood
     * @return true if mood exists
     * false otherwise
     */
    public boolean checkmood(Mood mood){
        ElasticSearchController.CheckMoodExistence checkMoodExistence = new ElasticSearchController.CheckMoodExistence();
        checkMoodExistence.execute(mood);
        boolean result = false;
        try{
            result = checkMoodExistence.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to search for Moods objects for current user!");
        }
        return result;
    }


    /**
     *  Deletes a given Mood from Elastic Search Database
     * @param mood
     * @return true if mood successfully deleted
     * false otherwise
     */
    public boolean deleteMood(Mood mood){
        ElasticSearchController.DeleteMoodTask deleteMoodTask = new ElasticSearchController.DeleteMoodTask();
        deleteMoodTask.execute(mood);
        try {
            deleteMoodTask.get();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Updates a mood in the Elastic Search Database
     * @param mood
     */

    public void editMood(Mood mood){
        ElasticSearchController.EditMoodsTask editMoodsTask = new ElasticSearchController.EditMoodsTask();
        editMoodsTask.execute(mood);
    }

    /**
     * Filters moods based on a given emotional state
     * @param user
     * @param state
     * @return mymoodsList
     */
    public ArrayList<Mood> emotionalstatefilteredmoods(Profile user, String state){
        ElasticSearchController.FilterEmotionalState getmyMoods = new ElasticSearchController.FilterEmotionalState();
        getmyMoods.execute(user.getUserName(), state);
        try{
            mymoodsList = getmyMoods.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to Filter Moods objects for emotional state!");
        }
        return mymoodsList;
    }

    /**
     * Filters moods based on a given Trigger/Reason
     * @param user
     * @param state
     * @return mymoodsList
     */
    public ArrayList<Mood> triggerfilteredmoods(Profile user, String state){
        ElasticSearchController.FilterTrigger getmyMoods = new ElasticSearchController.FilterTrigger();
        getmyMoods.execute(user.getUserName(), state);
        try{
            mymoodsList = getmyMoods.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to Filter Moods objects for the given Reason/Trigger!");
        }
        return mymoodsList;
    }


    /**
     * Adds a Profile to the Elastic Search Database only if it doesn't already exist
     * @param profile
     * @return true if the profile was successfully added
     * false if unsuccessful
     */
    public boolean addProfile(Profile profile){
        ElasticSearchController.AddProfileTask addProfileTask = new ElasticSearchController.AddProfileTask();
        addProfileTask.execute(profile);
        try {
            addProfileTask.get();
            return true;
        }
        catch (Exception e){
            Log.i("Error", "Failed to add mood");
            return false;
        }
    }

    /**
     *  Deletes a given Profile from Elastic Search Database
     * @param profile
     * @return true if the profile was successfully deleted
     * false if profile was not deleted because it didn't exist
     */
    public boolean deleteProfile(Profile profile){
        if(profile != null) {
            Profile p = new Profile();
            try {
                p = getProfile(profile.getUserName());
                ElasticSearchController.DeleteProfileTask deleteProfileTask = new ElasticSearchController.DeleteProfileTask();
                deleteProfileTask.execute(profile);
                try {
                    deleteProfileTask.get();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } catch (Exception e){
                Log.i("Error","No such profile exists in the database");
                return false;
            }
        }
        else{
            Log.i("Error", "Invalid Profile entered");
            return false;
        }
    }

    /**
     *  Gets a profile from the Elastic Search Database from a given username
     * @param username
     * @return Profile object
     */
    public Profile getProfile(String username) throws ExecutionException, InterruptedException {
        ElasticSearchController.GetProfileTask getProfileTask = new ElasticSearchController.GetProfileTask();
        getProfileTask.execute(username);
        return getProfileTask.get();
    }

}
