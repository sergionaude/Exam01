package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.exam01.db.DBQuerys;
import com.example.exam01.db.DbHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText pwdLogin;
    private ImageButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.ETEmail);
        pwdLogin = findViewById(R.id.ETPassword);
        btnLogin = findViewById(R.id.BtnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo y creo la cuenta
                DbHelper dbh = new DbHelper(LoginActivity.this);
                SQLiteDatabase db = dbh.getReadableDatabase();

                Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
                cursor.moveToFirst();
                Integer hoy = cursor.getColumnIndex("Email");
                String valor1 = cursor.getString(1).toString();
//                String valor1 = cursor.getColumnName(1).toString();
//
                Toast.makeText(LoginActivity.this,valor1.toString(), Toast.LENGTH_SHORT).show();

                db.execSQL(DBQuerys.validateQuery(emailLogin.getText().toString().trim(), pwdLogin.getText().toString().trim()));
                if(db != null ){
                    //Toast.makeText(LoginActivity.this,"The User is OK", Toast.LENGTH_SHORT).show();
                    db.close();
                  //  Intent home = new Intent(getBaseContext(), HomeActivity.class);
                  //  startActivity(home);
                }
                else{
                    Toast.makeText(LoginActivity.this,"The Users was not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}