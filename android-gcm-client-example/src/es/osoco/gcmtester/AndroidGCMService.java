package es.osoco.gcmtester;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static es.osoco.gcmtester.Main.PREFERENCES_NAME;
import static es.osoco.gcmtester.Main.PREFERENCE_SENDER_ID;
import static es.osoco.gcmtester.Main.PREFERENCE_MESSAGE_KEY;
import static es.osoco.gcmtester.Main.MESSAGE;

public class AndroidGCMService extends IntentService {

    private static PowerManager.WakeLock sWakeLock;
    private static final Object LOCK = AndroidGCMService.class;

    public AndroidGCMService() {
        super(AndroidGCMService.class.getName());
    }

    static void runIntentInService(Context context, Intent intent) {
        synchronized(LOCK) {
            if (sWakeLock == null) {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                sWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "AndroidGCMWakeLock");
            }
        }
        sWakeLock.acquire();
        intent.setClassName(context, AndroidGCMService.class.getName());
        context.startService(intent);
    }

    @Override
    public final void onHandleIntent(Intent intent) {
        try {
            String action = intent.getAction();
            String error = intent.getStringExtra("error");

            if(error == null) {
                if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
                    handleRegistration(intent);
                }
                else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                    handleNotificationReception(intent);
                }
            }
            else {
                if(error.equals("INVALID_PARAMETERS")) {
                    showNotification("Invalid parameters", "Please, review the Sender ID.");
                }
                else if(error.equals("INVALID_SENDER")) {
                    showNotification("Invalid sender", "Please, review the Sender ID.");
                }
            }
        } finally {
            synchronized(LOCK) {
                sWakeLock.release();
            }
        }
    }

    private void handleRegistration(Intent intent) {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://grails-android-gcm-sender.herokuapp.com/device/subscribe");
        String deviceToken = intent.getStringExtra("registration_id");
        Log.d("OSOCO-GCM",deviceToken);

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("projectId", preferences.getString(PREFERENCE_SENDER_ID, "")));
            nameValuePairs.add(new BasicNameValuePair("deviceToken", deviceToken));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO
        } catch (IOException e) {
            // TODO
        }
    }

    private void handleNotificationReception(Intent intent) {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        String title = getResources().getString(R.string.newNotification);
        String message = intent.getStringExtra(preferences.getString(PREFERENCE_MESSAGE_KEY, ""));
        showNotification(title, message);

        Intent broadcastIntent = new Intent(Main.PUSH_NOTIFICATION_RECEIVED);
        broadcastIntent.putExtra(Main.PUSH_NOTIFICATION_MESSAGE, message);
        sendBroadcast(broadcastIntent);
    }

    @SuppressWarnings("deprecation")
    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new Notification(R.drawable.logo, title, System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent(this, Main.class);
        intent.putExtra(MESSAGE, message);

        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(this, title, message, activity);

        notificationManager.notify(0, notification);
    }
}