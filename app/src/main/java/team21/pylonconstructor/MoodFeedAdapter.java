package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

/**
 * Created by ryanp on 2017-03-27.
 */

public class MoodFeedAdapter extends MoodAdapter {


    public MoodFeedAdapter(Context mContext, List<Mood> moodList) {
        super(mContext, moodList);
    }

    @Override
    /**
     * Showing popup menu when tapping on 3 dots
     */
    public void showPopupMenu(View view, int pos) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_mood_feed, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(pos));
        popup.show();
    }


    /**
     * Click listener for popup menu items
     * <a href="http://stackoverflow.com/questions/41836938/saving-card-position-in-adapter">
     * Source site</a>
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int pos;

        public MyMenuItemClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.action_unfollow_user) {
                Profile unfollowee = moodList.get(pos).getUser();
                //TODO: implement unfollow here

                adapter.notifyDataSetChanged();
                return true;
            }
            return false;
        }
    }

}