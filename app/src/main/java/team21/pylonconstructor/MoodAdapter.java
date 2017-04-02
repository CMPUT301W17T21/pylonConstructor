package team21.pylonconstructor;

/**
 * Created by rperez on 2017-03-12.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class extracts the data from the mood objects and passes it to the views to be displayed.
 * @see Mood
 *
 * RecyclerView with card views adapted from
 * http://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * accessed 03-13-2017 by rperez
 * @version 1.0
 */
public abstract class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MyViewHolder> {

    Context mContext;
    List<Mood> moodList;
    MoodAdapter adapter;

    //Refactored this to include Locale.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.US);
    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, trigger, dtView, socialSituationTextView;
        public ImageView thumbnail, overflow, emoji;
        private CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            emoji = (ImageView) view.findViewById(R.id.emoji);
            trigger = (TextView) view.findViewById(R.id.trigger);
            dtView = (TextView) view.findViewById(R.id.dt);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            socialSituationTextView = (TextView) view.findViewById(R.id.social_situation_mood_display);

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
        final int pos = position;
        ArrayList<String> situation;
        Mood mood = moodList.get(position);
        holder.title.setText(mood.getUser().getUserName());
        holder.title.append(" is feeling ");

        holder.trigger.setText(mood.getTrigger());


        holder.thumbnail.setImageBitmap(mood.getImage());
        holder.mCardView.setCardBackgroundColor(Color.GREEN);

        situation = mood.getSituation();
        if (situation != null) {
            holder.socialSituationTextView.setText("with ");
            int size = situation.size();
            int i;

            for (i = 0; i < size; i++) {
                if (i == 0) {
                    holder.socialSituationTextView.append(situation.get(i));
                } else {
                    holder.socialSituationTextView.append(", " + situation.get(i));
                }
            }
//            holder.socialSituationTextView
//                    .setPaintFlags(holder.socialSituationTextView
//                    .getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        }

        String dateStr = sdf.format(mood.getDate());
        holder.dtView.setText(dateStr);

        Drawable emoticon = mContext.getDrawable(R.drawable.ic_happy_263a);

        String e = mood.getEmoji();
        if (e.equals(mContext.getString(R.string.happy_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_happy_263a);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.happy_color));
            holder.title.append(mContext.getString(R.string.happy_button_label));


        }

        if (e.equals(mContext.getString(R.string.sad_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_sad_2639);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.sad_color));
            holder.title.append(mContext.getString(R.string.sad_button_label));


        }

        if (e.equals(mContext.getString(R.string.angry_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_angry_1f620);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.angry_color));
            holder.title.append(mContext.getString(R.string.angry_button_label));


        }

        if (e.equals(mContext.getString(R.string.confused_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_confused_1f615_1);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.confused_color));
            holder.title.append(mContext.getString(R.string.confused_button_label));

        }

        if (e.equals(mContext.getString(R.string.disgusted_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_disgusted_1f616);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.disgusted_color));
            holder.title.append(mContext.getString(R.string.disgusted_button_label));


        }

        if (e.equals(mContext.getString(R.string.scared_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_scared_1f631);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.scared_color));
            holder.title.append(mContext.getString(R.string.scared_button_label));


        }

        if (e.equals(mContext.getString(R.string.surprised_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_surprised_1f632);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.surprised_color));
            holder.title.append(mContext.getString(R.string.surprised_button_label));


        }

        if (e.equals(mContext.getString(R.string.shameful_button_label))) {
            emoticon = mContext.getDrawable(R.drawable.ic_shameful_1f612);
            holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.shameful_color));
            holder.title.append(mContext.getString(R.string.shameful_button_label));

        }

        holder.emoji.setImageDrawable(emoticon);



        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, pos);
            }
        });

        holder.socialSituationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    public void showPopupMenu(View view, int pos) {
        // sub-class dependent implementations
    }

    /**
     * Click listener for popup menu items
     * <a href="http://stackoverflow.com/questions/41836938/saving-card-position-in-adapter">
     * Source site</a>
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        // sub-class dependent implementation

        int pos;

        public MyMenuItemClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return false;
        }
    }
    @Override
    public int getItemCount() {
        return moodList.size();
    }
}