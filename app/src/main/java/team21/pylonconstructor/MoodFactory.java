package team21.pylonconstructor;

import android.util.Log;

import java.util.Random;

/**
 * Created by joshuarobertson on 2017-03-09.
 */
/**
 * This is currently on hold. In the future this may be adapted to create fake data to populate
 * the database in case all database data is lost.
 *
 * @see Mood
 */

public class MoodFactory {
    Random rand = new Random();

    public Mood newMood() {
        Mood mood = new Mood();
        return mood;
    }

//    private void setSituation(Mood mood) {
//        switch(rand.nextInt(4)) {
//            case(0): mood.setSituation("Alone");
//                break;
//            case(1): mood.setSituation("With one other person");
//                break;
//            case(2): mood.setSituation("With two to several people");
//                break;
//            case(3): mood.setSituation("With a crowd.");
//        }
//    }

    private void setTrigger(Mood mood) {
        switch(rand.nextInt(3)) {
            case (0):try {
                mood.setTrigger("Random");
                break;
                }
                catch (Exception e) {
                    Log.i("Error", "Reason exceeds limit");
                }
                case (1):try {
                    mood.setTrigger("Random Random");
                    break;
                }
                catch (Exception e) {
                    Log.i("Error", "Reason exceeds limit");
                }
                case (2):try {
                    mood.setTrigger("Random Random Random");
                } catch (Exception e) {
                Log.i("Error", "Reason exceeds limit");
                }
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
