package team21.pylonconstructor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by ryanp on 2017-03-25.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> requestList;
    private team21.pylonconstructor.RequestAdapter adapter;
    Profile profile = Controller.getInstance().getProfile();

    //Refactored this to include Locale.
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.US);
    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, trigger, dtView;
        private CardView mCardView;
        private Button acceptButton, declineButton;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.req_title);
            dtView = (TextView) view.findViewById(R.id.req_dt);
            acceptButton = (Button) view.findViewById(R.id.accept_btn);
            declineButton = (Button) view.findViewById(R.id.decline_btn);

        }
    }


    public RequestAdapter(Context mContext, List<String> requestList) {
        this.mContext = mContext;
        this.requestList = requestList;
        this.adapter = this;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int pos = position;
        final String request = requestList.get(position);
        holder.title.setText(request);


        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elasticSearch.acceptRequests(profile.getUserName(), request);
                requestList = elasticSearch.getFollowRequests(profile.getUserName());
                adapter.notifyDataSetChanged();

            }
        });
        holder.declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elasticSearch.declineRequests(profile.getUserName(), request);
                requestList = elasticSearch.getFollowRequests(profile.getUserName());
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

}
