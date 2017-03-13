package team21.pylonconstructor;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shivansh on 2017-03-10.
 */

/**
 * This class performs all the Elastic Search operations by making use of the
 * ElasticSearchController class. It is a wrapper class that helps views to
 * easily obtain desired information about moods and users and also helps
 * to easily add/edit moods and user profiles.
 *
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
     * @param user
     * @return mymoodsList
     */
    public ArrayList<Mood> getmymoods(Profile user){
        ElasticSearchController.GetMoodsTask getmyMoods = new ElasticSearchController.GetMoodsTask();
        getmyMoods.execute(user.getUserName());
        try{
            mymoodsList = getmyMoods.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to search for Moods objects for current user!");
        }
        return mymoodsList;
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
     * false if profile already exists
     */
    public boolean addProfile(Profile profile){

        Profile p = new Profile();
        p = getProfile(profile.getUserName());
        if(p == null) {
            ElasticSearchController.AddProfileTask addProfileTask = new ElasticSearchController.AddProfileTask();
            addProfileTask.execute(profile);
            try{
                addProfileTask.get();
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        else {
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
            p = getProfile(profile.getUserName());
            if (p != null) {
                ElasticSearchController.DeleteProfileTask deleteProfileTask = new ElasticSearchController.DeleteProfileTask();
                deleteProfileTask.execute(profile);
                try {
                    deleteProfileTask.get();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
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
    public Profile getProfile(String username){
        ElasticSearchController.GetProfileTask getProfileTask = new ElasticSearchController.GetProfileTask();
        getProfileTask.execute(username);
        try{
            return getProfileTask.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to Filter Moods objects for emotional state!");
            return null;
        }

    }

}
