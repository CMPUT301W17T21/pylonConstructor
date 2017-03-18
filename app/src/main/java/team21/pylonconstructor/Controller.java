package team21.pylonconstructor;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

public class Controller {
    //https://gist.github.com/Akayh/5566992
    //This is what I found for making singletons
    //March 17th 2017 Joshua did this.

    private static Controller mInstance = null;

    private ElasticSearch elasticSearch;
    private ArrayList<Mood> moodList;
    private LinkedList<Command> commands;

    private Profile profile;

    public void setProfile(Profile profile) {
        Log.d("setProfile: Input =", profile.getUserName());
        this.profile = profile;
        Log.d("SetProfile: Output =", this.profile.getUserName());
    }


    public Profile getProfile() {
        Log.d("Controller: Username:", this.profile.getUserName());
        return this.profile;
    }

    private Controller() {
        profile = new Profile();
        profile.setUserName("");
        elasticSearch = new ElasticSearch();
        moodList = new ArrayList<>();
        commands = new LinkedList<>();
    }


    public static Controller getInstance(){
        if(mInstance == null)
        {
            mInstance = new Controller();
        }
        return mInstance;
    }

    void update() {
        for (Command c : commands) {
            c.execute();
        }
    }
    Boolean addMood(Mood mood) {
        Command c = new NewMoodCommand(mood);
        if (c.execute()) {
            this.update();
            Log.i("AddMood: ", "Added mood!");
            return true;
        } else {
            if (!this.commands.contains(c)) {
                this.commands.addLast(c);
                this.moodList.add(mood);
                //Manually sort. or smart insert.
                Log.i("AddMood: ", "Saved mood!");
            }
        }
        return false;
    }

    Boolean editMood(Mood mood) {
        Command c = new EditMoodCommand(mood);
        if (c.execute()) {
            this.update();
            return true;
        } else {
            this.commands.addLast(c);
            //If equals() override works, this should update.
            this.moodList.remove(mood);
            this.moodList.add(mood);
        }
        return false;
    }

    Boolean deleteMood(Mood mood) {
        Command c = new DeleteMoodCommand(mood);
        if (c.execute()) {
            this.update();
            return true;
        } else {
            this.commands.addLast(c);
            this.moodList.remove(mood);
        }
        return false;
    }

    ArrayList<Mood> getAllMoods() {

            try {
                //JOSHUA THIS NEVER FAILS YOU KNEW THAT
                ArrayList<Mood> recieved = this.elasticSearch.getmymoods(this.profile);
                if (recieved != null) {
                    this.moodList = recieved;
                    this.update();
                }
                else {
                    Log.i("Get MoodList", "Error getting.");
                }
                //TODO: Handle exceptions.
            } catch (Exception e) {
                e.printStackTrace();
            }
        for (Mood m : moodList) {
            Log.d("MoodList:Mood:Trigger= ", m.getEmoji());
        }
        Log.i("MoodList", "Returning MoodList");
        return this.moodList;
    }
}
