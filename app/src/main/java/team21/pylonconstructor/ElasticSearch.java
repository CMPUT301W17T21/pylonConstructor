package team21.pylonconstructor;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shivansh on 2017-03-10.
 */

public class ElasticSearch {

    private ElasticSearchController.GetMoodsTask getmyMoods;
    private ElasticSearchController.AddMoodsTask addMoodsTask;
    private ArrayList<Mood> mymoodsList = new ArrayList<Mood>();

    public ElasticSearch() {

    }

    public void addMood(Mood mood){
        ElasticSearchController.AddMoodsTask addMoodsTask = new ElasticSearchController.AddMoodsTask();
        addMoodsTask.execute(mood);
    }

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

}
