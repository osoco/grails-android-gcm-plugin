package es.osoco.gcmtester;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Main extends SherlockFragmentActivity {

    private static String messageKey;

    public static final String PUSH_NOTIFICATION_RECEIVED = "PUSH_NOTIFICATION_RECEIVED";
    public static final String PUSH_NOTIFICATION_MESSAGE = "PUSH_NOTIFICATION_MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setTitle(R.string.action_bar_title);

        Button registerInTestServerButton = (Button) findViewById(R.id.registerInTestServerButton);
        registerInTestServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageKey = ((EditText) findViewById(R.id.messageKeyEditText)).getText().toString();

                EditText senderIdEditText = (EditText) findViewById(R.id.senderIdEditText);

                Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
                registrationIntent.putExtra("app", PendingIntent.getBroadcast(Main.this, 0, new Intent(), 0));
                registrationIntent.putExtra("sender", senderIdEditText.getText().toString());
                startService(registrationIntent);
            }
        });

        BroadcastReceiver pushNotificationReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(intent.getAction().equals(PUSH_NOTIFICATION_RECEIVED))
                {
                    TextView message = (TextView) Main.this.findViewById(R.id.pushMessageReceivedId);
                    message.setText(intent.getStringExtra(PUSH_NOTIFICATION_MESSAGE));
                }
            }
        };
        registerReceiver(pushNotificationReceiver, new IntentFilter(PUSH_NOTIFICATION_RECEIVED));
    }

    public static String getMessageKey() {
        return messageKey;
    }
}