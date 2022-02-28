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
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.ETEmail);
        pwdLogin = findViewById(R.id.ETPassword);
        btnLogin = findViewById(R.id.BtnLogin);
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
                    emailLogin.setError("Favor de validar el correo");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pwdLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String textoPwd = pwdLogin.getText().toString().trim();
                if(!(CAActivity.validateLengthPwd(textoPwd) && CAActivity.validateUpperCase(textoPwd) && CAActivity.validateLowerCase(textoPwd))){
                    pwdLogin.setError("Favor de colocar contraseña valida");
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
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
        cursor.moveToFirst();
        emailLogin.setText(cursor.getString(1));
        Toast.makeText(LoginActivity.this, "Se recupero el correo", Toast.LENGTH_SHORT).show();
        cursor.close();
    }
    public boolean verifyData(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM Users", null);
        if(cursor.moveToFirst()){
            do{
                Toast.makeText(LoginActivity.this, emailLogin.getText().toString().trim() + cursor.getString(1), Toast.LENGTH_SHORT).show();

                if( (emailLogin.getText().toString().trim().equals(cursor.getString(1).trim())) && (pwdLogin.getText().toString().trim().equals(cursor.getString(2).trim())) ){
                    cursor.close();
                    return true;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
}