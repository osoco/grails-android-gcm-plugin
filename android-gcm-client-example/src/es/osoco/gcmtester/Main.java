package es.osoco.gcmtester;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

//        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
//        registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
//        registrationIntent.putExtra("sender", "1073974434973");
//        startService(registrationIntent);
    }
}