package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam01.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton ImgButtonCreateAccount;
    private ImageButton ImgButtonLogin;
    private TextView TextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ImgButtonCreateAccount = findViewById(R.id.IMBCreateAccount);
        ImgButtonLogin = findViewById(R.id.IMBLogin);
        TextViewLogin = findViewById(R.id.TVLogin);

        ImgButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(login);
            }
        });

        ImgButtonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent createAccount = new Intent(getBaseContext(), CreateAccountActivity.class);
                startActivity(createAccount);
            }
        });


    }
}