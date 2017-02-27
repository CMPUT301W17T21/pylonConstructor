package team21.pylonconstructor;

import org.junit.Test;

import java.io.IOError;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Willi_000 on 2017-02-27.
 */

/**
 * Note: This class only tests data and data storage.
 * It does not test views, or filters
 */
public class DataTest {
    public DataTest() {
        super(/* TODO: Add activity here */);
    }

    /**
     * Tests the mood class and its contents
     *
     * @throws Exception
     */
    @Test
    public void moodTest() throws Exception {
        MoodList moodList = new MoodList();
        Mood mood = new Mood();

        //Test if mood has been added
        moodList.add(mood);
        assertTrue(moodList.has(mood));

        //Test specifics of a mood
        assertEquals(mood.getEmoji(), moodList.getMood(0).getEmoji());
        assertEquals(mood.getDate(), moodList.getMood(0).getDate());
        assertEquals(mood.getTrigger(), moodList.getMood(0).getTrigger());
        assertEquals(mood.getSituation(), moodList.getMood(0).getSituation());

        //Edit mood
        mood.setEmoji("Anger");
        mood.setDate("2010-10-10");
        mood.setTrigger(/* TODO: Add trigger here */);
        mood.setSituation("Alone");
        moodList.add(mood);

        //Test editing of mood
        assertEquals(mood.getEmoji(), moodList.getMood(1).getEmoji());
        assertEquals(mood.getDate(), moodList.getMood(1).getDate());
        assertEquals(mood.getTrigger(), moodList.getMood(1).getTrigger());
        assertEquals(mood.getSituation(), moodList.getMood(1).getSituation());

        //Test the different mood states
        moodStateTest("Anger");
        moodStateTest("Confusion");
        moodStateTest("Disgust");
        moodStateTest("Fear");
        moodStateTest("Happiness");
        moodStateTest("Sadness");
        moodStateTest("Sahme");
        moodStateTest("Suprise");

        //TODO: Comment out to continue as the following should fail
        moodStateTest("RandomText");    //Not a mood. Should fail
        moodStateTest("NotAMood");      //Not a mood. Should fail
        System.out.println("Above test cases have not passed if this line is reached");
        return;

        //Test consistent icons
        Mood mood1 = new Mood();
        Mood mood2 = new Mood();
        assertEquals(mood1.getEmoji(), mood2.getEmoji());

        mood1.setEmoji("Sadness");
        assertNotEquals(mood1.getEmoji(), mood2.getEmoji()); //Should not have same emoji
        mood2.setEmoji("Sadness");
        assertEquals(mood1.getEmoji(), mood2.getEmoji());

        //Test textual explanation
        assertTrue(mood.setText("01234567890123456789"));
        assertFalse(mood.setText("0123456789012345678901")); //Over 20 char limit
        assertTrue(mood.setText("0O()"));
        assertTrue(mood.setText("Hello","World"));
        assertFalse(mood.setText("This", "Is", "Over", "Limit")); //Over 3 words

        //Test photograph explanation
        assertNotEquals(mood.getImage(), moodList.getMood(1).getImage()); //No image in moodList moods
        mood.setImage(/* TODO: Add image here */);
        moodList.add(mood);
        assertEquals(mood.getImage(), moodList.getMood(2).getImage());

        //Test photo size
        assertTrue(mood.getImageSize < 65536);
        mood.setImage(/* TODO: Add image here */);    //Set an image that is greater than 65536 bytes
        assertFalse(mood.getImageSize < 65536);

        //Test social situation
        socialSituationTest("Alone");
        socialSituationTest("Pair");
        socialSituationTest("Group");
        socialSituationTest("Crowd");

        //TODO: Comment out to continue as the following should fail
        socialSituationTest("Fail Case");   //Wrong input. Will fail
        socialSituationTest("Invalid");     //Wrong input. Will fail
        System.out.println("Above test cases have not passed if this line is reached");
        return;

        //Test if mood event has been removed
        moodList.remove(moood);
        assertFalse(moodList.has(mood));
    }

    /**
     * Tests the mood states
     * Assumes that mood state is different (NULL, or empty) if invalid mood state is set
     *
     * @throws Exception
     */
    @Test
    public void moodStateTest(String state) throws Exception {
        Mood mood = new Mood();

        mood.setEmoji(state);

        assertEquals(mood.getEmoji(), state);
    }

    /**
     * Tests the social situation field of moods
     * Assumes that the social situation is not set if invalid
     *
     * @throws Exception
     */
    @Test
    public void socialSituationTest(String situation) throws Exception{
        Mood mood = new Mood();

        mood.setSituation(state);

        assertEquals(mood.getSituation(), situation);
    }

    /**
     * Tests the location of moods
     *
     * @throws Exception
     */
    @Test
    public void locationTest() throws Exception{
        MoodList moodList = new MoodList();
        Mood mood = new Mood();

        //Add location test
        mood.setLocation();
        assertTrue(moodList.hasLocation(0));

        //Test 5km radius
        mood.setLocation(/* TODO: Add location here */); //Set location within 5km
        assertTrue(mood.isIn5km());
        mood.setLocation(/* TODO: Add location here */); //Set location out of 5km
        assertFalse(moodList.isIn5km());
    }

    /**
     * Tests the profile
     *
     * @throws Exception
     */
    @Test
    public void profileTest() throws Exception{
        Profile profile1 = new Profile();
        Profile profile2 = new Profile();

        //Test for unique username
        profile1.setUserName("Python");
        profile2.setUserName("C++");
        assertNotEquals(profile1.getUserName(), profile2.getUserName());
    }

    /**
     * Tests offline mode
     *
     * @throws Exception
     */
    @Test
    public void offlineTest() throws Exception{
        MoodList moodList = new MoodList();
        Mood mood = new Mood();
        Database onlineDB = new Database();
        boolean state = false; //False = Offline, True = Online

        //Add
        moodList.add(mood);
        assertFalse(onlineDB.hasMood(mood));
        assertTrue(moodList.has(mood));

        //Test updating add
        state = true;
        assertTrue(onlineDB.hasMood(mood));
        assertTrue(moodList.has(mood));

        //Delete
        state = false;
        moodList.remove(mood);
        assertFalse(moodList.has(mood));
        assertTrue(onlineDB.hasMood(mood)); //Still has mood

        //Test updating delete
        state = true;
        assertFalse(onlineDB.hasMood(mood));  //Removed
        assertFalse(moodList.has(mood));
    }
}
