package team21.pylonconstructor;

import android.graphics.Bitmap;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the mood data class
 *  1. Emoji
 *  2. Situation
 *  3. Tag
 *  4. Trigger
 *  5. User
 *  6. Image
 *  7. Latitude
 *  8. Longitude
 *
 *  Also tests some incorrect or bad cases or input
 *
 * User Stories Tested: US 01.02.01, US 02.01.01, US 02.03.01
 *  - Have the required moods
 *  - Tests for correct input in trigger
 *  - For US 02.03.01, I was unable to find a way to create a bitmap that exceeds
 *      the restriction of 65536 bytes, thus the test just tests that a image exists
 *
 * Assumptions:
 *  - None
 *
 * @author William
 */
public class MoodTest {

    /**
     * Test various data that the mood
     *  -Happy, Sad, Angry, Confuse, Disgust, Scared, Surprise, and Shameful
     *
     * @throws Exception Assert has failed
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
        mood.setLocation(180.0, 140.0);

        //Test that the changes are stored
        assertEquals(mood.getEmoji(), "Happy");
        assertEquals(mood.getSituation(), situation);
        assertEquals(mood.getTrigger(), "Bear Dog");
        assertEquals(mood.isHasTag(), true);
        assertEquals(mood.getUser().getUserName(), "Test");
        assertEquals(mood.getId(), "TestID");
        assertEquals(mood.getLongitude(), 140.0, 0);
        assertEquals(mood.getLatitude(), 180.0, 0);

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
     *  -Happy, Sad, Angry, Confuse, Disgust, Scared, Surprise, and Shameful
     *
     * @throws Exception Assert has failed
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

    /**
     * Tests trigger
     * - Trigger cannot exceed 3 words
     * - Trigger cannot exceed 20 characters
     *
     * @throws Exception Assert has failed
     */
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

            //Valid input (Less than twenty characters)
            assertEquals(mood.getTrigger(), trigger);
            assertTrue(false);
        } catch (Exception ReasonTooLongException) {
            //Invalid input (More that 20 characters)
            assertTrue(true);
        }
    }

    /**
     * Tests if an image is stored, as well as the size of the image.
     * -Restriction is 65536 bytes.
     * -Cannot exceed restriction
     *
     * @throws Exception Assert has failed
     */
    @Test
    public void imageTest() throws Exception {
        Mood mood = new Mood();

        // Create a bitmap
        Bitmap image = Bitmap.createBitmap(500, 500,  Bitmap.Config.ARGB_8888);

        //Test for a image smaller than restriction
        try {
            mood.setImage(image);

            //Image is less than the restriction
            assertTrue(true);
        } catch (Exception ImageTooLargeException) {
            //Image is greater than restriction
            assertTrue(false);
        }

        // Cannot create bitmap that exceeds restrictions.
        //  The following is the highest this creator can go
        //image = Bitmap.createBitmap(999999999, 999999999,  Bitmap.Config.ARGB_8888);
    }
}

























