package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ryanp on 2017-03-27.
 */

public class TaggedMoodAdapter extends MoodAdapter {
    public TaggedMoodAdapter(Context mContext, List<Mood> moodList) {
        super(mContext, moodList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tagged_mood_card, parent, false);
        return new MyViewHolder(itemView);
    }

}