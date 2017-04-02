package team21.pylonconstructor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {


    private Context mContext;
    private List<String> profileUserNamesList;
    private ProfileAdapter adapter;
    Profile profile = Controller.getInstance().getProfile();

    ElasticSearch elasticSearch = new ElasticSearch();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, trigger, dtView;
        private CardView mCardView;
        private Button acceptButton, declineButton;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.prf_title);
            //dtView = (TextView) view.findViewById(R.id.req_dt);

        }
    }


    public ProfileAdapter(Context mContext, List<String> profileUserNamesList) {
        this.mContext = mContext;
        this.profileUserNamesList = profileUserNamesList;
        this.adapter = this;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final int pos = position;
        final String follower = profileUserNamesList.get(position);
        holder.title.setText(follower);
    }

    @Override
    public int getItemCount() {
        return profileUserNamesList.size();
    }

}

