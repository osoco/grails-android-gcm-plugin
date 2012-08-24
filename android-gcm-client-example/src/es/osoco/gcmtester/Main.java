package es.osoco.gcmtester;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Main extends SherlockFragmentActivity {

    public static final String PREFERENCES_NAME = "es.osoco.gcmtester.preferences";
    public static final String PREFERENCE_MESSAGE_KEY = "messageKey";
    public static final String PREFERENCE_SENDER_ID = "senderId";

    public static final String MESSAGE = "MESSAGE";

    public static final String PUSH_NOTIFICATION_RECEIVED = "PUSH_NOTIFICATION_RECEIVED";
    public static final String PUSH_NOTIFICATION_MESSAGE = "PUSH_NOTIFICATION_MESSAGE";

    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle(R.string.action_bar_title);

        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        ((EditText) findViewById(R.id.senderIdEditText)).setText(preferences.getString(PREFERENCE_SENDER_ID, ""));
        ((EditText) findViewById(R.id.messageKeyEditText)).setText(preferences.getString(PREFERENCE_MESSAGE_KEY, ""));

        Button registerInTestServerButton = (Button) findViewById(R.id.registerInTestServerButton);
        registerInTestServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senderId = ((EditText) findViewById(R.id.senderIdEditText)).getText().toString();
                String messageKey = ((EditText) findViewById(R.id.messageKeyEditText)).getText().toString();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PREFERENCE_SENDER_ID, senderId);
                editor.putString(PREFERENCE_MESSAGE_KEY, messageKey);
                editor.commit();

                Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
                registrationIntent.putExtra("app", PendingIntent.getBroadcast(Main.this, 0, new Intent(), 0));
                registrationIntent.putExtra("sender", senderId);
                startService(registrationIntent);
            }
        });

        BroadcastReceiver pushNotificationReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
            if(intent.getAction().equals(PUSH_NOTIFICATION_RECEIVED)) {
                showMessage(intent.getStringExtra(PUSH_NOTIFICATION_MESSAGE));
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(0);
            }
            }
        };
        registerReceiver(pushNotificationReceiver, new IntentFilter(PUSH_NOTIFICATION_RECEIVED));
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();

        if(intent != null && intent.getStringExtra(MESSAGE) != null) {
            showMessage(intent.getStringExtra(MESSAGE));
        }
    }

    private void showMessage(String message) {
        TextView messageTextView = (TextView) Main.this.findViewById(R.id.pushMessageReceivedId);
        messageTextView.setTextColor(Color.BLACK);
        messageTextView.setTypeface(null, Typeface.BOLD);
        messageTextView.setText(message);
    }
}