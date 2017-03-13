package team21.pylonconstructor;

/**
 * Created by ryanp on 2017-03-12.
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MyViewHolder> {

    private Context mContext;
    private List<Mood> moodList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count, trigger;
        public ImageView thumbnail, overflow, emoji;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            emoji = (ImageView) view.findViewById(R.id.emoji);
            trigger = (TextView) view.findViewById(R.id.trigger);
        }
    }


    public MoodAdapter(Context mContext, List<Mood> moodList) {
        this.mContext = mContext;
        this.moodList = moodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mood_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Mood mood = moodList.get(position);
        holder.title.setText(mood.getUser().getUserName());
        holder.trigger.setText(mood.getTrigger());

        Drawable emoticon = mContext.getDrawable(R.drawable.ic_happy_263a);

        String e = mood.getEmoji();
        if (e.equals(mContext.getString(R.string.happy_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_happy_263a);
        }

        if (e.equals(mContext.getString(R.string.sad_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_sad_2639);
        }

        if (e.equals(mContext.getString(R.string.angry_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_angry_1f620);
        }

        if (e.equals(mContext.getString(R.string.confused_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_confused_1f615_1);
        }

        if (e.equals(mContext.getString(R.string.disgusted_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_disgusted_1f616);
        }

        if (e.equals(mContext.getString(R.string.scared_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_scared_1f631);
        }

        if (e.equals(mContext.getString(R.string.surprised_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_surprised_1f632);
        }

        if (e.equals(mContext.getString(R.string.shameful_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_shameful_1f612);
        }

        holder.emoji.setImageDrawable(emoticon);


        // loading album cover using Glide library
        // Glide.with(mContext).load(R.drawable.ic_action_close).into(holder.overflow);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_mood, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return moodList.size();
    }
}