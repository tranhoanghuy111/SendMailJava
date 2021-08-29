package com.tranetech.openspace.sendmail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SendMailActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Button send = (Button) this.findViewById(R.id.button1);
        Intent intent = this.getIntent();
        final String username= intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("SendMailActivity", "Send Button Clicked.");


                String toEmails = ((TextView) findViewById(R.id.editText3))
                        .getText().toString();
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = ((TextView) findViewById(R.id.editText4))
                        .getText().toString();
                String emailBody = ((TextView) findViewById(R.id.editText5))
                        .getText().toString();
                new SendMailTask(SendMailActivity.this).execute(username,
                        password, toEmailList, emailSubject, emailBody);
            }
        });
    }
}
