package team21.pylonconstructor;

import java.util.ArrayList;

/**
 * Created by joshuarobertson on 2017-03-03.
 */

//Maybe change to extends ArrayList. If we start using all of the features of an arraylist.
class MoodList {
    ArrayList<Mood> moodList;

    MoodList (Mood... moods) {
        moodList = new ArrayList<>();
        for (Mood m : moods) this.moodList.add(m);
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
