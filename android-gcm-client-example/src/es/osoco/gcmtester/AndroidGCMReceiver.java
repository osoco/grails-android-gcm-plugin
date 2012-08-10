package es.osoco.gcmtester;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AndroidGCMReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        AndroidGCMService.runIntentInService(context, intent);
        setResult(Activity.RESULT_OK, null, null);
    }
}