package team21.pylonconstructor;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Willi_000 on 2017-02-27.
 */

public class DataTest {
    public DataTest() {
        super(/* Mood class here */);
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
        mood.setEmoji();
        mood.setDate();
        mood.setTrigger();
        mood.setSituation();
        moodList.add(mood);

        //Test editing of mood
        assertEquals(mood.getEmoji(), moodList.getMood(1).getEmoji());
        assertEquals(mood.getDate(), moodList.getMood(1).getDate());
        assertEquals(mood.getTrigger(), moodList.getMood(1).getTrigger());
        assertEquals(mood.getSituation(), moodList.getMood(1).getSituation());

        //TODO: Test case for extra details (US 02)


        //Test if mood event has been removed
        moodList.remove(moood);
        assertFalse(moodList.has(mood));
    }

    /**
     * Tests the location of moods
     *
     * @throws Exception
     */
    @Test
    public void locationTest() throws Exception{
        //TODO: Test Location (US 06)
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
        //TODO: Test cases for being offline
    }
}
