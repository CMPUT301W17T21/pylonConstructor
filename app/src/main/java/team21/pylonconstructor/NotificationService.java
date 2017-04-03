package team21.pylonconstructor;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by singla1 on 3/28/17.
 */

/**
 * The following makes an object that is used for push notifications
 * adapted from https://www.youtube.com/watch?v=NgQzJ0s0XmM#t=2.883802
 * accessed on 28-03-2017 by Shivansh
 **/

public class NotificationService extends IntentService {

    private static final String TAG = "team21.pylonconstructor";
    NotificationCompat.Builder notification;
    private ElasticSearch elasticSearch = new ElasticSearch();

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "Service has started");
        while(true){
            /**
             * The following makes an object that is used for push notifications
             * adapted from https://www.youtube.com/watch?v=NgQzJ0s0XmM#t=2.883802
             * accessed on 28-03-2017 by Shivansh
             **/
            notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);
            try {
                ArrayList<Notification> notificationlist = elasticSearch.getNotification(Controller.getInstance().getProfile().getUserName());
                for (Notification ntf : notificationlist) {
                    if (ntf != null && ntf.getSeenflag().equals("0")) {
                        //Build the notification
                        notification.setSmallIcon(R.drawable.ic_noun_654393_cc);
                        notification.setTicker("New mood activity");
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("PylonConstructor");
                        notification.setContentText(ntf.getTaggedby().concat(" tagged you in a post"));
                        Intent i = new Intent(this, NotificationsActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                        notification.setContentIntent(pendingIntent);

                        //Build and Issue notification
                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify((int)System.currentTimeMillis(), notification.build());
                    }
                }
            }
            catch (Exception e){
                Log.i("Error","Notification could not be obtained!");
            }
            // Wait 3s before trying again
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
