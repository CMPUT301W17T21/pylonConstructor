package team21.pylonconstructor;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Willi_000 on 2017-02-27.
 */

/**
 * Note: This class only tests data and data storage.
 * It does not test views, or filters, intents, activities, etc
 */
public class DataTest {
    public DataTest() {
        super(/* TODO: Add activity here */);
    }

    /**
     * Tests variables of the Mood object
     * -Emoji
     * -Situation
     * -Trigger
     * -User (Username)
     *
     * @throws Exception
     */
    @Test
    public void createMood() throws Exception {
        Mood mood = new Mood();

        //Change the variables
        mood.setEmoji("Happy");
        mood.setSituation("One Person");
        mood.setTrigger("Bear Dog");
        mood.setUser(new Profile("Username"));

        //Test that the changes are stored
        assertEquals(mood.getEmoji(), "Happy");
        assertEquals(mood.getSituation(), "One Person");
        assertEquals(mood.getTrigger(), "Bear Dog");
        assertEquals(mood.getUser().getUserName(), "Username");

        //Some invalid cases
        assertNotEquals(mood.getEmoji(), "Ha");
        assertNotEquals(mood.getSituation(), "Person");
        assertNotEquals(mood.getTrigger(), "Bear");
        assertNotEquals(mood.getUser().getUserName(), "User");
    }

    @Test
    public void moodDate() throws Exception {
        Mood mood = new Mood();
        //TODO: Test the behavior of the date.
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
        mood.setUser(new Profile("UserName")); //TODO: TEST THIS
        mood.setEmoji("Anger");
        //mood.setDate("12 12 2010");
        //mood.setTrigger(/* TODO: Add trigger here */);
        mood.setSituation("Alone");

        //moodList.add(mood);

        //Test editing of mood
        assertEquals(mood.getEmoji(), moodList.getMood(0).getEmoji());
        assertEquals(mood.getDate(), moodList.getMood(0).getDate());
        assertEquals(mood.getTrigger(), moodList.getMood(0).getTrigger());
        assertEquals(mood.getSituation(), moodList.getMood(0).getSituation());

        //Test the different mood states
        /* Tests cannot have parameters
        moodStateTest("Anger");
        moodStateTest("Confusion");
        moodStateTest("Disgust");
        moodStateTest("Fear");
        moodStateTest("Happiness");
        moodStateTest("Sadness");
        moodStateTest("Sahme");
        moodStateTest("Suprise");
        */

        //TODO: Comment out to continue as the following should fail
        //moodStateTest("RandomText");    //Not a mood. Should fail
        //moodStateTest("NotAMood");      //Not a mood. Should fail
        //System.out.println("Above test cases have not passed if this line is reached");

        //Test consistent icons
        Mood mood1 = new Mood();
        Mood mood2 = new Mood();
        assertEquals(mood1.getEmoji(), mood2.getEmoji());

        mood1.setEmoji("Sadness");
        assertNotEquals(mood1.getEmoji(), mood2.getEmoji()); //Should not have same emoji
        mood2.setEmoji("Sadness");
        assertEquals(mood1.getEmoji(), mood2.getEmoji());

        //Test textual explanation
        //TODO: Shouldn't this be throwing errors, not returning booleans?
        /*
        assertTrue(mood.setTrigger("01234567890123456789"));
        assertFalse(mood.setTrigger("0123456789012345678901")); //Over 20 char limit
        assertTrue(mood.setTrigger("0O()"));
        assertTrue(mood.setTrigger("Hello","World"));
        assertFalse(mood.setTrigger("This", "Is", "Over", "Limit")); //Over 3 words
        */

        //Test photograph explanation

        //assertNotEquals(mood.getImage(), moodList.getMood(1).getImage()); //No image in moodList moods
        //mood.setImage(/* TODO: Add image here */);
        //moodList.add(mood);
        //assertEquals(mood.getImage(), moodList.getMood(2).getImage());


        //Test photo size
        //assertTrue(mood.getImageSize() < 65536);
        //mood.setImage(/* TODO: Add image here */);    //Set an image that is greater than 65536 bytes
        //assertFalse(mood.getImageSize() < 65536);

        //Test social situation
        //socialSituationTest("Alone");
        //socialSituationTest("Pair");
        //socialSituationTest("Group");
        //socialSituationTest("Crowd");

        //TODO: Comment out to continue as the following should fail
        //socialSituationTest("Fail Case");   //Wrong input. Will fail
        //socialSituationTest("Invalid");     //Wrong input. Will fail
        //System.out.println("Above test cases have not passed if this line is reached");

        //Test if mood event has been removed
        moodList.remove(mood);
        assertFalse(moodList.has(mood));
    }

    /**
     * Tests the mood states
     * Assumes that mood state is different (NULL, or empty) if invalid mood state is set
     *
     * @throws Exception
     */
    /* Tests cannot have parameters
    @Test
    public void moodStateTest(String state) throws Exception {
        Mood mood = new Mood();

        mood.setEmoji(state);

        assertEquals(mood.getEmoji(), state);
    }
    */

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
        //mood.setLocation();
        //assertTrue(moodList.hasLocation(0));

        //Test 5km radius
        //mood.setLocation(/* TODO: Add location here */); //Set location within 5km
        //assertTrue(mood.isIn5km());
        //mood.setLocation(/* TODO: Add location here */); //Set location out of 5km
       // assertFalse(moodList.isIn5km());
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
    /*
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
    */
}
