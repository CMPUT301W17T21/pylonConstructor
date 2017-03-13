package team21.pylonconstructor;

/**
 * Created by ryanp on 2017-03-12.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */

/**
 * This class extracts the data from the mood objects and passes it to the views to be displayed.
 * From https://github.com/CMPUT301W17T21/pylonConstructor/tree/master/doc
 * accessed 03-13-2017 by rperez
 * @see Mood
 *
 * @version 1.0
 */
public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MyViewHolder> {


    private Context mContext;
    private List<Mood> moodList;
    private MoodAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, trigger, dtView;
        public ImageView thumbnail, overflow, emoji;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            emoji = (ImageView) view.findViewById(R.id.emoji);
            trigger = (TextView) view.findViewById(R.id.trigger);
            dtView = (TextView) view.findViewById(R.id.dt);
        }
    }


    public MoodAdapter(Context mContext, List<Mood> moodList) {
        this.mContext = mContext;
        this.moodList = moodList;
        this.adapter = this;
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


        holder.thumbnail.setImageBitmap(mood.getImage());

        String dateStr = sdf.format(mood.getDate());
        holder.dtView.setText(dateStr);

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
     * <a href="http://stackoverflow.com/questions/41836938/saving-card-position-in-adapter">
     * Source site</a>
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int pos;

        public MyMenuItemClickListener() {
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
                        elasticSearch.deleteMood(dmood);
                        Intent intent = new Intent(mContext, MoodHistoryActivity.class);
                        adapter.notifyDataSetChanged();
                    }

                    else {
                        slideStart.putExtra("emoj", moodList.get(pos).getEmoji());
                        slideStart.putExtra("situ", moodList.get(pos).getSituation());
                        slideStart.putExtra("trig", moodList.get(pos).getTrigger());
                        slideStart.putExtra("date", moodList.get(pos).getDate().getTime());


                        slideStart.putExtra("usr", moodList.get(pos).getUser().getUserName());
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
    @Override
    public int getItemCount() {
        return moodList.size();
    }
}