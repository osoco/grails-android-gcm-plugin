package es.osoco.gcmtester;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class Main extends SherlockFragmentActivity {

    private static String messageKey;

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
    }

    public static String getMessageKey() {
        return messageKey;
    }
}