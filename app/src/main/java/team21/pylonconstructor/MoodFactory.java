package team21.pylonconstructor;

import java.util.Random;

/**
 * Created by joshuarobertson on 2017-03-09.
 */

public class MoodFactory {
    Random rand = new Random();

    public Mood newMood() {
        Mood mood = new Mood();

    }

    private void setSituation(Mood mood) {
        switch(rand.nextInt(4)) {
            case(0): mood.setSituation("Alone");
                break;
            case(1): mood.setSituation("With one other person");
                break;
            case(2): mood.setSituation("With two to several people");
                break;
            case(3): mood.setSituation("With a crowd.");
        }
    }

    private void setTrigger(Mood mood) {
        switch(rand.nextInt(3)) {
            case(0): mood.setTrigger("Random");
                     break;
            case(1): mood.setTrigger("Random", "Random");
                     break;
            case(2): mood.setTrigger("Random", "Random", "Random");
        }
    }

    private void setEmoji(Mood mood) {
        switch(rand.nextInt(4)) {
            case(0): mood.setEmoji("Happy");
                break;
            case(1): mood.setEmoji("Angry");
                break;
            case(2): mood.setEmoji("Sad");
                break;
            case(3): mood.setEmoji("Rattled");
        }
    }
}
