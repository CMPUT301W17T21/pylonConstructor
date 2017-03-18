package team21.pylonconstructor;

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
       elasticSearch.deleteMood(mood);
        if (elasticSearch.checkmood(mood)) return false;
        return  true;
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
