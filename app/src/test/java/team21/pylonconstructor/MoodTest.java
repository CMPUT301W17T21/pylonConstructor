package team21.pylonconstructor;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the mood data class
 *  1. Emoji
 *  2. Situation
 *  3. Tag
 *  4. Trigger
 *  5. Date
 *  6. User
 *  7. Image
 *  8. Latitude
 *  9. Longitude
 *
 *  Also tests some incorrect or bad cases or input
 *
 * User Stories Tested: US 01.02.01
 *  -Have the required moods
 *
 * Assumptions:
 *  - None
 *
 * @author William
 */
//TODO: Date
public class MoodTest {

    /**
     * Test various data that the mood
     *  -Happy, Sad, Angry, Confuse, Disgust, Scared, Surprise, Sand hameful
     *
     * @throws Exception
     */
    @Test
    public void createMood() throws Exception {
        Mood mood = new Mood();
        ArrayList<String> situation = new ArrayList<>();

        //Change the variables
        mood.setEmoji("Happy");
        situation.add(0, "One Person");
        mood.setSituation(situation);
        mood.setTrigger("Bear Dog");
        mood.setHasTag(true);
        mood.setUser(new Profile("Test"));
        mood.setId("TestID");

        //Test that the changes are stored
        assertEquals(mood.getEmoji(), "Happy");
        assertEquals(mood.getSituation(), situation);
        assertEquals(mood.getTrigger(), "Bear Dog");
        assertEquals(mood.isHasTag(), true);
        assertEquals(mood.getUser().getUserName(), "Test");
        assertEquals(mood.getId(), "TestID");

        //Some invalid cases
        assertNotEquals(mood.getEmoji(), "Ha");
        assertNotEquals(mood.getEmoji(), "Sad");
        assertNotEquals(mood.getSituation(), "Person");
        assertNotEquals(mood.getTrigger(), "Bear");
        assertNotEquals(mood.getUser().getUserName(), "User");
        assertNotEquals(mood.getId(), "Test");
    }

    /**
     * Test all the types of moods
     *  -Happy, Sad, Angry, Confuse, Disgust, Scared, Surprise, Sand hameful
     *
     * @throws Exception
     */
    @Test
    public void testAllMoods() throws Exception {
        Mood mood = new Mood();
        String emoji, emoji2;

        emoji = "Happy";
        emoji2 = "Sad";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Sad";
        emoji2 = "Happy";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Angry";
        emoji2 = "Sad";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Confused";
        emoji2 = "Sad";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Distrust";
        emoji2 = "Angry";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Scared";
        emoji2 = "Distrust";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Surpise";
        emoji2 = "Blah";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);

        emoji = "Shameful";
        emoji2 = "Sad";
        mood.setEmoji(emoji);
        assertEquals(mood.getEmoji(), emoji);
        assertNotEquals(mood.getEmoji(), emoji2);
    }
    @Test
    public void triggerTest() throws Exception {
        Mood mood = new Mood();
        String trigger;

        //Test a valid trigger
        trigger = "Test Trigger";
        mood.setTrigger(trigger);
        assertEquals(mood.getTrigger(), trigger);

        //Test an invalid trigger with more than three words
        trigger = "Test Trigger Is Long";
        try {
            mood.setTrigger(trigger);

            //Valid input (3 or less words inclusive)
            assertEquals(mood.getTrigger(), trigger);
            assertTrue(false);
        } catch (Exception ReasonTooLongException) {
            //Invalid input (More than 3 words)
            assertTrue(true);
        }

        //Test an invalid trigger with more than twenty characters
        trigger = "Test TriggerIs ReallyLong";
        try {
            mood.setTrigger(trigger);

            //Valid input (3 or less words inclusive)
            assertEquals(mood.getTrigger(), trigger);
            assertTrue(false);
        } catch (Exception ReasonTooLongException) {
            //Invalid input (More than 3 words)
            assertTrue(true);
        }
    }

    @Test
    public void dateTest() throws Exception {}
    @Test
    public void imageTest() throws Exception {}
}

























