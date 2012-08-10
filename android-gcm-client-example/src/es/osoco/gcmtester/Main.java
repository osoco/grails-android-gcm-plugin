package es.osoco.gcmtester;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Main extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle(R.string.ab_title);

//        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
//        registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
//        registrationIntent.putExtra("sender", "1073974434973");
//        startService(registrationIntent);
    }
}