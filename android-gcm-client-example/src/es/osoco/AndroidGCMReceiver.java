package es.osoco;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: asantalla
 * Date: 03/08/12
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class AndroidGCMReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        AndroidGCMService.runIntentInService(context, intent);
        setResult(Activity.RESULT_OK, null, null);
    }
}