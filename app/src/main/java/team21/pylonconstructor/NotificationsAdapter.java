package team21.pylonconstructor;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by ryanp on 2017-04-01.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Notification> notificationList;
    private NotificationsAdapter adapter;
    Profile profile = Controller.getInstance().getProfile();
    public View view;
    public ClipData.Item currentItem;

    //Refactored this to include Locale.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.US);
    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        private CardView mCardView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.ntf_title);
            mCardView = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public NotificationsAdapter(Context mContext, List<Notification> profileUserNamesList) {
        this.mContext = mContext;
        this.notificationList = profileUserNamesList;
        this.adapter = this;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final int pos = position;
        Mood mood = null;
        final String moodId = notificationList.get(position).getMoodid();

        try {
            mood = elasticSearch.getmoodfromid(moodId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (mood != null) {
            final String follower = mood.getUser().getUserName();
            holder.title.setText(follower + " tagged you in a mood event");
        }


        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement onClick
                Log.i("NotificationsAdapter", notificationList.get(position).getSeenflag());
                String moodId = notificationList.get(position).getMoodid();
                System.out.println("Clicked " + moodId);
                notificationList.get(position).setSeenflag();
                elasticSearch.updateNotification(notificationList.get(position));
                Intent intent = new Intent(mContext, ViewTaggedMoodActivity.class);
                intent.putExtra("mood_id", moodId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
