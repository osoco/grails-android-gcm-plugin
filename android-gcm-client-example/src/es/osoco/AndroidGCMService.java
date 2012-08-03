package es.osoco;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
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
            if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
                handleRegistration(intent);
            } else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                Notification notification = new Notification(R.drawable.ic_launcher, intent.getStringExtra("msg"), System.currentTimeMillis());
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.number += 1;
                notificationManager.notify(0, notification);
            }
        } finally {
            synchronized(LOCK) {
                sWakeLock.release();
            }
        }
    }

    private void handleRegistration(Intent intent) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.4.0.251:8080/android-gcm-example/device/subscribe");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("deviceToken", intent.getStringExtra("registration_id")));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO
        } catch (IOException e) {
            // TODO
        }
    }
}