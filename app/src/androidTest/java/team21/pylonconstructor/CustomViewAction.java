package team21.pylonconstructor;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * A custom action to allow clicking on a specific button on a card view
 *  that is contained within a recycler view
 *  -Presses the overflow button to bring up delete and edit menu
 *
 * Code from:
 *  http://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
 */


public class CustomViewAction {
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
