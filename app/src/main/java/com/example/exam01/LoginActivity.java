package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.exam01.db.DbHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText emailLogin;
    private EditText pwdLogin;
    private ImageButton btnLogin;

    // lets remove unused variables or methods
    // remove all commented code
    private DbHelper dbh;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.ETEmail);
        pwdLogin = findViewById(R.id.ETPassword);
        btnLogin = findViewById(R.id.BtnLogin);

        // I like the use of this database
        // take a look into Room DB in android basically an abstraction of SQLite db
        DbHelper dbh = new DbHelper(LoginActivity.this);
        SQLiteDatabase db = dbh.getReadableDatabase();

        loadEmail(db);

        CreateAccountActivity CAActivity = new CreateAccountActivity();

        emailLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String textoEmail = emailLogin.getText().toString().trim();
                if(!(CAActivity.validateLengthEmail(textoEmail) && CAActivity.validateEmail(textoEmail))){

                    // let's use English to display messages
                    emailLogin.setError("Favor de validar el correo");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyData(db)){
                    Intent home = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(home);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Favor de verificar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadEmail(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM Users order by 1 desc", null);
        cursor.moveToFirst();
        emailLogin.setText(cursor.getString(1).toString());
        Toast.makeText(LoginActivity.this, "Se recupero el correo", Toast.LENGTH_SHORT).show();
    }
    public boolean verifyData(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
        if(cursor.moveToFirst()){
            do{
                if( (emailLogin.getText().toString().trim().equals(cursor.getString(1).toString().trim())) && (pwdLogin.getText().toString().trim().equals(cursor.getString(2).toString().trim())) ){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        return false;
    }
}