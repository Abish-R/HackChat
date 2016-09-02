package helix.hackchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by kundan on 10/22/2015.
 */
public class PushNotificationService extends GcmListenerService {
    //ChatActivity ca = new ChatActivity();
    @Override
    public void onMessageReceived(String from, Bundle data) {
        int recvr_id = Integer.parseInt(data.getString("ReceiverId"));
        String message = data.getString("Message");
        String message_id = data.getString("MessageId");
        int sndr_id = Integer.parseInt(data.getString("SenderId"));
        String sent_date = data.getString("SentDate");
        String notify = data.getString("Notification");
        createNotification("New Message", sndr_id,recvr_id,message_id,message,sent_date,notify);
    }
    public void createNotification(String tit, int sdr_id, int recvr_id, String msg_id, String msg, String snt_dt, String notify) {
        int icon = R.drawable.icon;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, msg, when);

        //String title = "DashBoard Builder";//context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(this, ChatActivity.class);
        notificationIntent.putExtra("sender_id",sdr_id);
        notificationIntent.putExtra("receiver_id",recvr_id);
        notificationIntent.putExtra("Notification",notify);
        notificationIntent.putExtra("msg_id",msg_id);
        notificationIntent.putExtra("message",msg);
        notificationIntent.putExtra("sent_date",snt_dt);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(this,(int)System.currentTimeMillis(), notificationIntent, 0);
        notification.setLatestEventInfo(this, tit, notify, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);

    }
}
