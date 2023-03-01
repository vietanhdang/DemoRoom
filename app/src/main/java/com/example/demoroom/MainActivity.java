package com.example.demoroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroom.Database.UserDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 100;
    private EditText edt_username;
    private EditText edt_address;
    private Button btn_add_user;
    private RecyclerView rv_user;

    private UserAdapter userAdapter;
    private List<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        userAdapter = new UserAdapter(this::clickUpdateUser);
        listUser = new ArrayList<>();
        userAdapter.setData(listUser);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_user.setLayoutManager(linearLayoutManager);
        rv_user.setAdapter(userAdapter);


        btn_add_user.setOnClickListener(v -> addUser());
        loadData();

    }

    public void initUi(){
        edt_username = findViewById(R.id.edt_username);
        edt_address = findViewById(R.id.edt_address);
        btn_add_user = findViewById(R.id.btn_add_user);
        rv_user = findViewById(R.id.recycler_view_users);
    }

    private void addUser() {


        String username = edt_username.getText().toString().trim();
        String address = edt_address.getText().toString().trim();
        if(username.isEmpty() || address.isEmpty()) {
        }
        else{
            User user = new User(username, address);
            if(isUserExist(username)){
                Toast.makeText(this, "User is exist", Toast.LENGTH_SHORT).show();
                return;
            }else{
                UserDatabase.getInstance(this).userDAO().insertUser(user);
                Toast.makeText(this, "Add user successfully", Toast.LENGTH_SHORT).show();
                edt_address.setText("");
                edt_username.setText("");
            }
            hideSoftKeyboard();
            loadData();

        }
    }

    private void loadData(){
        listUser = UserDatabase.getInstance(this).userDAO().getAllUser();
        userAdapter.setData(listUser);
    }

    //hide soft keyboard
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private boolean isUserExist(String username){
        List<User> listUser = UserDatabase.getInstance(this).userDAO().checkUser(username);
        return listUser.size() > 0;
    }



    private void clickUpdateUser(User user) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", (Serializable) user);
        intent.putExtras(bundle);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK){
            loadData();
        }
    }
}