package team21.pylonconstructor;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

public class NewMoodCommand extends Command {
    Mood mood;
    ElasticSearch elasticSearch;

    NewMoodCommand(Mood mood) {
        this.mood = mood;
        elasticSearch = new ElasticSearch();
    }

    @Override
    public Boolean execute() {
        return elasticSearch.addMood(mood);
    }

    @Override
    public Boolean unexecute() {
        return elasticSearch.deleteMood(mood);
    }

    @Override
    public Boolean isReversable() {
        return true;
    }
}
