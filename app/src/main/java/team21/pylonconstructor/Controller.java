package team21.pylonconstructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

public class Controller {
    private ElasticSearch elasticSearch = new ElasticSearch();
    private ArrayList<Mood> moodList = new ArrayList<>();
    LinkedList<Command> commands = new LinkedList<>();

    private Profile profile;

    Controller (Profile profile) {
        this.profile = profile;
    }

    Boolean update() {
        Command c;
        Boolean b = Boolean.TRUE;
        for (int i = 0;i<commands.size();i++) {
            c = commands.getLast();
            if (!c.execute()) {
                this.commands.addFirst(c);
                b = Boolean.FALSE;
            }
        }
        return b;
    }
    Boolean addMood(Mood mood) {
        Command c = new NewMoodCommand(mood);
        if (c.execute()) {
            this.update();
            return true;
        } else {
            this.commands.addFirst(c);
            this.moodList.add(mood);
        }
        return false;
    }

    Boolean editMood(Mood mood) {
        Command c = new EditMoodCommand(mood);
        if (c.execute()) {
            this.update();
            return true;
        } else {
            this.commands.addFirst(c);
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
            this.commands.addFirst(c);
            this.moodList.remove(mood);
        }
        return false;
    }

    ArrayList<Mood> getAllMoods() {
        try {
            this.moodList = this.elasticSearch.getmymoods(this.profile);
            this.update();
            //TODO: Handle exceptions.
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.moodList;
    }
}
