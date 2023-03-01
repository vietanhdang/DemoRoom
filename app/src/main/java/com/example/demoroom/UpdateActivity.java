package com.example.demoroom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoroom.Database.UserDatabase;

public class UpdateActivity extends AppCompatActivity {
    private EditText edt_username;
    private EditText edt_address;
    private Button btn_update_user;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edt_username = findViewById(R.id.edt_username);
        edt_address = findViewById(R.id.edt_address);
        btn_update_user = findViewById(R.id.btn_update_user);
        mUser = (User) getIntent().getSerializableExtra("user");
        if(mUser != null){
            edt_username.setText(mUser.getUsername());
            edt_address.setText(mUser.getAddress());
        }
        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }


        });
    }
    private void updateUser() {
        String username = edt_username.getText().toString().trim();
        String address = edt_address.getText().toString().trim();
        if(username.isEmpty() || address.isEmpty()){
            return;
        }else{
            mUser.setUsername(username);
            mUser.setAddress(address);
            UserDatabase.getInstance(this).userDAO().updateUser(mUser);
            Toast.makeText(this, "Update user success", Toast.LENGTH_SHORT).show();
            Intent intentResult = new Intent(this, MainActivity.class);
            setResult(Activity.RESULT_OK, intentResult);
            finish();
        }
    }
}