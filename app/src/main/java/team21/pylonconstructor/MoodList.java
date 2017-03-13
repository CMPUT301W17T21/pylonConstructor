package team21.pylonconstructor;

import java.util.ArrayList;

/**
 * Created by joshuarobertson on 2017-03-03.
 *
 */

/**
 * This class holds a list of moods.
 */
//Maybe change to extends ArrayList. If we start using all of the features of an arraylist.
class MoodList extends ArrayList {
    ArrayList<Mood> moodList;

    MoodList (Mood... moods) {
        moodList = new ArrayList<>();
        for (Mood m : moods)
            this.moodList.add(m);
    }
    MoodList () {
        moodList = new ArrayList<>();
    }

    public void add(Mood mood) {
        moodList.add(mood);
    }

    public Mood getMood(int i) {
        return moodList.get(i);
    }

    public boolean has(Mood mood) {
        return moodList.contains(mood);
    }

    public void remove(Mood mood) {
        moodList.remove(mood);
    }
}
