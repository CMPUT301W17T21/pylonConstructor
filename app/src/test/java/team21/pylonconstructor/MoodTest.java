package team21.pylonconstructor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Willi_000 on 2017-03-30.
 */
//TODO: Date
public class MoodTest {
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
}
