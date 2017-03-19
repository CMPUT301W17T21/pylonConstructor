package team21.pylonconstructor;
import java.util.ArrayList;
import java.util.Date;
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
    String filterTerm = null;
    int filterOption = 0;
    private Profile profile;
    Date filterDate = null;

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

        if (filterOption == 0) {
            try {
                this.moodList = this.elasticSearch.getmymoods(this.profile);
                this.update();
                //TODO: Handle exceptions.
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (filterOption == 1) {
            this.moodList = this.elasticSearch.emotionalstatefilteredmoods(this.profile, filterTerm);
        }

        if (filterOption == 2) {
            this.moodList = this.elasticSearch.triggerfilteredmoods(this.profile, filterTerm);
        }

        if (filterOption == 3) {
            //TODO: implement tthis weekfilteredmoods()
            //this.moodsList = this.elasticSearch.weekfilteredmoods()
        }

        this.update();
        //TODO: Exception handling for emotionalstatefilteredmoods?
        return this.moodList;
    }

    void addFilters(String filterTerm, int filterOption) {
        this.filterTerm = filterTerm;
        this.filterOption = filterOption;
    }

    void addDateFilter(Date filterDate) {
        this.filterDate = filterDate;
    }
}
