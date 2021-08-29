package com.tranetech.openspace.sendmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity1 extends AppCompatActivity  {
    private Button button;
    EditText editText;
    EditText editText1;
    ArrayList list=new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getData();
        button = (Button) this.findViewById(R.id.LoginButton);
        editText = (EditText) this.findViewById(R.id.editT1);
        editText1 = (EditText) this.findViewById(R.id.editT2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMailActivity();
            }
        });


    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void SendMailActivity() {

        String username = editText.getText().toString().trim();
        String password = editText1.getText().toString().trim();

        if (isValidEmail(username) == true) {
            if (isValidatePassword(password, username)) {

                Intent intent = new Intent(this, SendMailActivity.class);
                intent.putExtra("username", editText.getText().toString());
                intent.putExtra("password", editText1.getText().toString());
                this.startActivity(intent);

            } else {
                Toast.makeText(this, "Erorr password.....",
                        Toast.LENGTH_LONG).show();

            }
        }
        else{
            Toast.makeText(this, "Erorr email.....",
                    Toast.LENGTH_LONG).show();
        }


    }
    public void getData() {
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = (User)postSnapshot.getValue(User.class);
                    list.add(user);
                }
                for (int i = 0; i < list.size(); i++) {
                    User user = (User)list.get(i);
                    Log.e("username",user.getusername());
                    Log.e("password",user.getpassword());

                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }
    public boolean isValidatePassword(String password,String username){

        for(int i=0;i<list.size();i++){
            User user = (User)list.get(i);
            if(username.equals(user.username)&&password.equals(user.password)){
                return true;
            }
        }
        return false;
    }
}