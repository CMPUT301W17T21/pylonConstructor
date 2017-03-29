package team21.pylonconstructor;

import android.util.Log;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

public class EditMoodCommand extends Command {
    Mood mood;
    ElasticSearch elasticSearch;

    EditMoodCommand(Mood mood) {
        this.mood = mood;
        elasticSearch = new ElasticSearch();
    }

    @Override
    public Boolean execute() {
        //TODO: below
        //return elasticSearch.editMood(mood);
        elasticSearch.editMood(mood);
        Log.d(mood.getTrigger(), "NewMood: Trigger = ");
        return true;
    }

    @Override
    public Boolean unexecute() {
        return false;
    }

    @Override
    public Boolean isReversable() {
        return false;
    }
}