package team21.pylonconstructor;

import android.content.Context;
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

/**
 * Created by ryanp on 2017-04-01.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {


    private Context mContext;
    private List<Notification> notificationList;
    private NotificationsAdapter adapter;
    Profile profile = Controller.getInstance().getProfile();

    //Refactored this to include Locale.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.US);
    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, dtView;
        private CardView mCardView;
        private Button acceptButton, declineButton;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.ntf_title);
            //dtView = (TextView) view.findViewById(R.id.req_dt);

        }
    }


    public NotificationsAdapter(Context mContext, List<Notification> profileUserNamesList) {
        this.mContext = mContext;
        this.notificationList = profileUserNamesList;
        this.adapter = this;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("NotificationsAdapter", "notifs received: " +notificationList.get(0).getMoodid());
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int pos = position;
        final String follower = notificationList.get(position).getMoodid();
        holder.title.setText(follower);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
