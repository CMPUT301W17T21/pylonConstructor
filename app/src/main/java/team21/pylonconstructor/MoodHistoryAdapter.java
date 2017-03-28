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

public class MoodHistoryAdapter extends MoodAdapter {


    public MoodHistoryAdapter(Context mContext, List<Mood> moodList) {
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
        inflater.inflate(R.menu.menu_mood_history, popup.getMenu());
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
            Intent slideStart = new Intent(mContext, UpdateMoodActivity.class);

            boolean delete = false;
            switch (menuItem.getItemId()) {
                case R.id.action_delete_mood:
                    delete = true;
                case R.id.action_edit_mood:


                    super.getClass();


                    if (delete) {
                        Mood dmood = moodList.get(pos);
                        Controller.getInstance().deleteMood(dmood);
                        adapter.notifyDataSetChanged();
                    }

                    else {
                        slideStart.putExtra("emoj", moodList.get(pos).getEmoji());
                        slideStart.putExtra("situ", moodList.get(pos).getSituation());
                        slideStart.putExtra("trig", moodList.get(pos).getTrigger());
                        slideStart.putExtra("date", moodList.get(pos).getDate().getTime());
                        slideStart.putExtra("id", moodList.get(pos).getId());

                        slideStart.putExtra("username", moodList.get(pos).getUser().getUserName());
                        slideStart.putExtra("image",  moodList.get(pos).getImage());
                        slideStart.putExtra("EDIT",  1);
                        mContext.startActivity(slideStart);
                    }

                    return true;
                default:
            }
            return false;
        }
    }

}
