package team21.pylonconstructor;

import android.util.Log;

import java.nio.channels.ConnectionPendingException;
import java.util.ArrayList;
import java.util.Collections;
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
    public boolean checkmood(Mood mood) throws Exception {
        ElasticSearchController.CheckMoodExistence checkMoodExistence = new ElasticSearchController.CheckMoodExistence();
        checkMoodExistence.execute(mood);
        boolean result = false;
        try{
            result = checkMoodExistence.get();
        }
        catch (Exception e){
            Log.i("Error", "Failed to search for Moods objects for current user!");
            throw new Exception();
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
            return deleteMoodTask.get();
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Updates a mood in the Elastic Search Database
     * @param mood
     */

    public boolean editMood(Mood mood){
        ElasticSearchController.EditMoodsTask editMoodsTask = new ElasticSearchController.EditMoodsTask();
        editMoodsTask.execute(mood);
        try {
            return editMoodsTask.get();
        }
        catch (Exception e){
            return false;
        }
    }


    /**
     *  Gets Moods from the most recent week from the Elastic Search Database for a given user
     *
     *  Josh updated this to throw exceptions so he could make sure it actually got the list.
     * @param user
     * @return mymoodsList
     */
    public ArrayList<Mood> getrecentweekmoods(Profile user) throws ExecutionException, InterruptedException {
        ElasticSearchController.FilterRecentWeekMoods filterRecentWeekMoods = new ElasticSearchController.FilterRecentWeekMoods();
        filterRecentWeekMoods.execute(user.getUserName());
        mymoodsList = filterRecentWeekMoods.get();
        return mymoodsList;
    }

    /**
     * Filters moods based on a given emotional state
     * @param user
     * @param state
     * @return mymoodsList
     */
    public ArrayList<Mood> emotionalstatefilteredmoods (Profile user, String state) throws ExecutionException, InterruptedException{
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
    public ArrayList<Mood> triggerfilteredmoods(Profile user, String state) throws ExecutionException, InterruptedException{
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
                deleteProfileTask.execute(p);
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

    /**
     * Updates a profile in the Elastic Search Database
     * @param profile
     */

    public boolean updateProfile(Profile profile){
        ElasticSearchController.UpdateProfileTask updateProfileTask = new ElasticSearchController.UpdateProfileTask();
        updateProfileTask.execute(profile);
        try {
            return updateProfileTask.get();
        }
        catch (Exception e) {
            return false;
        }
    }

    /****
     *  Gets the lists of users who have sent a request to follow
     * @param username
     * @return followRequests
     */
    public ArrayList<String> getFollowRequests (String username){
        ArrayList<String> followRequests = new ArrayList<String>();
        try{
            Profile profile = getProfile(username);
            if(profile != null){
                followRequests = profile.getRequests();
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return followRequests;
    }


    /****
     *  Gets the lists of users who follow you
     * @param username
     * @return followers
     */
    public ArrayList<String> getFollowers (String username){
        ArrayList<String> followers = new ArrayList<String>();
        try{
            Profile profile = getProfile(username);
            if(profile != null){
                followers = profile.getFollowers();
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return followers;
    }


    /****
     *  Gets the lists of users who are followed by you
     * @param username
     * @return following
     */
    public ArrayList<String> getFollowing (String username){
        ArrayList<String> following = new ArrayList<String>();
        try{
            Profile profile = getProfile(username);
            if(profile != null){
                following = profile.getFollowing();
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return following;
    }

    /***
     * Send a follow request
     * @param username, requester_name
     * @return true if accepted successfully
     * else otherwise
     */
    public boolean sendRequests (String username, String requested_name){
        try{
            Profile profile = getProfile(requested_name);
            if(profile != null){
                profile.addRequests(username);
                if(updateProfile(profile)){
                    return true;
                }
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return false;
    }

    /***
     * Accept a follow request and add it to following list of the requester and
     * followers list of user
     * @param username, requester_name
     * @return true if accepted successfully
     * else otherwise
     */
    public boolean unfollowUser (String username, String requested_name){
        try{
            Profile userprofile = getProfile(username);
            if(userprofile != null){
                userprofile.removeFollowing(requested_name);
                Profile requestedprofile = getProfile(requested_name);
                requestedprofile.removeFollower(username);
                if(updateProfile(userprofile) && updateProfile(requestedprofile)){
                    return true;
                }
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return false;
    }


    /***
     * Decline a follow request
     * @param username, requester_name
     * @return true if declined successfully,
     * else otherwise
     */
    public boolean declineRequests (String username, String requester_name){
        try{
            Profile profile = getProfile(username);
            if(profile != null){
                profile.removeRequests(requester_name);
                if(updateProfile(profile)){
                    return true;
                }
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return false;
    }

    /***
     * Accept a follow request and add it to following list of the requester and
     * followers list of user
     * @param username, requester_name
     * @return true if accepted successfully
     * else otherwise
     */
    public boolean acceptRequests (String username, String requester_name){
        try{
            Profile userprofile = getProfile(username);
            if(userprofile != null){
                userprofile.removeRequests(requester_name);
                userprofile.addFollowers(requester_name);
                Profile requesterprofile = getProfile(requester_name);
                requesterprofile.addFollowing(username);
                if(updateProfile(userprofile) && updateProfile(requesterprofile)){
                    return true;
                }
            }
        }
        catch (Exception e){
            Log.i("Error", "Cannot get profile");
        }
        return false;
    }

    /**
     *  Gets Moods from the followed users from Elastic Search Database for a given user
     *
     * @param user
     * @return moodsList
     */
    public ArrayList<Mood> getfollowingmoods(Profile user) throws ExecutionException, InterruptedException {
        ArrayList<String> following = user.getFollowing();
        ArrayList<Mood> moodsList = new ArrayList<Mood>();
        for(String username : following){
            ElasticSearchController.GetFollowingMoodsTask getFollowingMoods = new ElasticSearchController.GetFollowingMoodsTask();
            getFollowingMoods.execute(username);
            Mood mood = getFollowingMoods.get();
            if (mood != null) {
                Log.i("Mood", mood.getEmoji());
                moodsList.add(mood);
            }
        }
        Collections.sort(moodsList, Collections.<Mood>reverseOrder());
        return moodsList;
    }


    /**
     *  Gets Moods from the followed users for the most recent week from the Elastic Search Database for a given user
     *
     * @param user
     * @return mymoodsList
     */
    public ArrayList<Mood> getrecentweekmoodsfeed(Profile user) throws ExecutionException, InterruptedException {
        ArrayList<String> following = user.getFollowing();
        ArrayList<Mood> moodsList = new ArrayList<Mood>();
        for(String username : following) {
            ElasticSearchController.FilterFeedRecentWeekMoods filterFeedRecentWeekMoods = new ElasticSearchController.FilterFeedRecentWeekMoods();
            filterFeedRecentWeekMoods.execute(username);
            Mood mood = filterFeedRecentWeekMoods.get();
            if (mood != null) {
                Log.i("Mood", mood.getEmoji());
                moodsList.add(mood);
            }
        }
        Collections.sort(moodsList, Collections.<Mood>reverseOrder());
        return moodsList;
    }

    /**
     * Filters moods based on a given emotional state from the followed users
     * @param user
     * @param state
     * @return mymoodsList
     */
    public ArrayList<Mood> emotionalstatefilteredmoodsfeed (Profile user, String state) throws ExecutionException, InterruptedException{
        ArrayList<String> following = user.getFollowing();
        ArrayList<Mood> moodsList = new ArrayList<Mood>();
        for(String username : following){
            ElasticSearchController.FilterFeedEmotionalState filterFeedEmotionalState = new ElasticSearchController.FilterFeedEmotionalState();
            filterFeedEmotionalState.execute(username, state);
            try{
                Mood mood = filterFeedEmotionalState.get();
                if (mood != null) {
                    Log.i("Mood", mood.getEmoji());
                    moodsList.add(mood);
                }
            }
            catch (Exception e){
                Log.i("Error", "Failed to Filter Moods objects for emotional state!");
            }
        }
        Collections.sort(moodsList,Collections.<Mood>reverseOrder());
        return moodsList;
    }

    /**
     * Filters moods based on a given Trigger/Reason from the followed users
     * @param user
     * @param state
     * @return mymoodsList
     */
    public ArrayList<Mood> triggerfilteredmoodsfeed(Profile user, String state) throws ExecutionException, InterruptedException{
        ArrayList<String> following = user.getFollowing();
        ArrayList<Mood> moodsList = new ArrayList<Mood>();
        for(String username : following){
            ElasticSearchController.FilterFeedTrigger filterFeedTrigger = new ElasticSearchController.FilterFeedTrigger();
            filterFeedTrigger.execute(username, state);
            try{
                Mood mood = filterFeedTrigger.get();
                if (mood != null) {
                    Log.i("Mood", mood.getEmoji());
                    moodsList.add(mood);
                }
            }
            catch (Exception e){
                Log.i("Error", "Failed to Filter Moods objects for emotional state!");
            }
        }
        return moodsList;
    }


    /**
     * Adds a notification to the Elastic Search Database
     * @param notification
     * @return true if the profile was successfully added
     * false if unsuccessful
     */
    public boolean addNotification(Notification notification){
        ElasticSearchController.AddNotificationTask addNotificationTask = new ElasticSearchController.AddNotificationTask();
        addNotificationTask.execute(notification);
        try {
            addNotificationTask.get();
            return true;
        }
        catch (Exception e){
            Log.i("Error", "Failed to add mood");
            return false;
        }
    }

    /**
     *  Gets a notification from the Elastic Search Database from a given username
     * @param username
     * @return Profile object
     */
    public ArrayList<Notification> getNotification(String username) throws ExecutionException, InterruptedException {
        ElasticSearchController.GetNotificationTask getNotificationTask = new ElasticSearchController.GetNotificationTask();
        getNotificationTask.execute(username);
        try{
            ArrayList<Notification> notifications = getNotificationTask.get();
            Collections.sort(notifications, Collections.<Notification>reverseOrder());
            return notifications;
        }
        catch (Exception e){
            Log.i("e", "notifications could not be received");
        }
        return null;
    }

    /**
     *  Gets Mood from the Elastic Search Database with a given mood id
     ** @param mood_id
     * @return mymoodsList
     */
    public Mood getmoodfromid(String mood_id) throws ExecutionException, InterruptedException {
        ElasticSearchController.FilterMoodFromId filterMoodFromId = new ElasticSearchController.FilterMoodFromId();
        filterMoodFromId.execute(mood_id);
        Mood mood = filterMoodFromId.get();
        return mood;
    }

    /**
     * Updates a notification in the Elastic Search Database
     * @param notification
     */

    public boolean updateNotification(Notification notification){
        ElasticSearchController.UpdateNotificationTask updateNotificationTask = new ElasticSearchController.UpdateNotificationTask();
        updateNotificationTask.execute(notification);
        try {
            return updateNotificationTask.get();
        }
        catch (Exception e) {
            return false;
        }
    }

}
