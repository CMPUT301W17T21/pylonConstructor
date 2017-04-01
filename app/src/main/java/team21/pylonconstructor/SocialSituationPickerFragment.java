package team21.pylonconstructor;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanp on 2017-03-31.
 */

public class SocialSituationPickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ElasticSearch elasticSearch = new ElasticSearch();
        Profile profile = Controller.getInstance().getProfile();

        final ArrayList<String> followingList = elasticSearch.getFollowing(profile.getUserName());

        String[] followingArray = new String[followingList.size()];
        followingArray = followingList.toArray(followingArray);
        final ArrayList<String> selectedUserList = new ArrayList<>();

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        final String[] finalFollowingArray = followingArray;
        builder.setTitle("Select users")
                .setMultiChoiceItems(finalFollowingArray, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int item, boolean isChecked) {

                                if (isChecked) {
                                    Log.i("DialogSelect", "Selected is: " + finalFollowingArray[item]);
                                    selectedUserList.add(finalFollowingArray[item]);
                                }
                                else {
                                    Log.i("DialogSelect", "Unselect is: " + finalFollowingArray[item]);
                                    selectedUserList.remove(finalFollowingArray[item]);
                                }

                            }
                        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UpdateMoodActivity act = (UpdateMoodActivity) getActivity();
                act.setSocialSituationUsersList(selectedUserList);
            } });


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("DialogSelect", "Cancelled!");

            } });

        return builder.create();
    }
}