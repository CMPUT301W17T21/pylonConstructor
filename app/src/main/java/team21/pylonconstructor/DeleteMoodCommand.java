package team21.pylonconstructor;

import android.util.Log;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

public class DeleteMoodCommand extends Command {
    Mood mood;
    ElasticSearch elasticSearch;

    DeleteMoodCommand(Mood mood) {
        this.mood = mood;
        elasticSearch = new ElasticSearch();
    }

    @Override
    public Boolean execute() {
       return elasticSearch.deleteMood(mood);
    }

    @Override
    public Boolean unexecute() {
        return elasticSearch.addMood(mood);
    }

    @Override
    public Boolean isReversable() {
        return true;
    }
}
