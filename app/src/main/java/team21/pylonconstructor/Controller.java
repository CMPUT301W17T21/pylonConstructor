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
    private boolean internetStatus = false;

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
        Command c;
        for (int i = 0; i < commands.size(); i++) {
            c = commands.getFirst();
            if (!c.execute()) {
                commands.addLast(c);
            }
            commands.removeFirst();
            Log.i("CommUpp", "Executing...");
        }
    }
    Boolean addMood(Mood mood) {
        Command c = new NewMoodCommand(mood);
        this.moodList.add(mood);
        //TODO: Local Sort
        if (c.execute()) {
            this.update();
            Log.i("AddMood: ", "Added mood!");
            return true;
        } else if (!this.commands.contains(c)) {
            this.commands.addLast(c);
            //Manually sort. or smart insert.
            Log.i("AddMood: ", "Saved mood!");
        }
        return false;
    }

    Boolean editMood(Mood mood) {
        Command c = new EditMoodCommand(mood);
        this.moodList.remove(mood);
        this.moodList.add(mood);
        //TODO: Local Sort
        if (c.execute()) {
            this.update();
            Log.i("EditMood: ", "Edited mood!");
            return true;
        } else if (!this.commands.contains(c)) {
            this.commands.addLast(c);
            //If equals() override works, this should update.
        }
        return false;
    }

    Boolean deleteMood(Mood mood) {
        Command c = new DeleteMoodCommand(mood);
        this.moodList.remove(mood);
        //TODO: Local Sort
        if (c.execute()) {
            this.update();
            Log.i("DeleteMood: ", "Deleted mood!");
            return true;
        } else if (!this.commands.contains(c)) {
            this.commands.addLast(c);
        }
        return false;
    }

    ArrayList<Mood> getAllMoods() {
        this.update();
            try {
                ArrayList<Mood> recieved = this.elasticSearch.getmymoods(this.profile);
                if (recieved != null) {
                    //Got a mood list
                    this.moodList = recieved;
                } else {
                    Log.i("Get MoodList", "Error getting.");
                }
                //TODO: Handle exceptions.
            } catch (Exception e) {
                Log.i("Get MoodList", "Error connecting.");
                e.printStackTrace();
            }
        Log.i("MoodList", "Returning MoodList");
        return this.moodList;
    }
}
