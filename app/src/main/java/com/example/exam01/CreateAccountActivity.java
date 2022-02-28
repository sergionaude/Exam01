package com.example.exam01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.exam01.db.DBQuerys;
import com.example.exam01.db.DbHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText emailCA;
    private EditText pwdCA;
    private EditText pwdConfirmCA;
    private ImageButton btnCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        emailCA = findViewById(R.id.ETEmailCA);
        pwdCA = findViewById(R.id.ETPasswordCA);
        pwdConfirmCA = findViewById(R.id.ETPasswordConfirmCA);
        btnCA = findViewById(R.id.BtnCreateNew);

        btnCA.setEnabled(false);
        btnCA.setVisibility(View.INVISIBLE);

        emailCA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String emailTxt = emailCA.getText().toString().trim();
                if(!validateEmail(emailTxt) || !validateLengthEmail(emailTxt)){
                    emailCA.setError("Invalid email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pwdConfirmCA.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validateAll(emailCA.getText().toString().trim(), pwdCA.getText().toString().trim(), pwdConfirmCA.getText().toString().trim() )){
                    btnCA.setEnabled(true);
                    btnCA.setVisibility(View.VISIBLE);
                }
                else {
                    btnCA.setEnabled(false);
                    btnCA.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnCA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardo y creo la cuenta
                DbHelper dbh = new DbHelper(CreateAccountActivity.this);
                SQLiteDatabase db = dbh.getWritableDatabase();
                db.execSQL(DBQuerys.insertQuery(emailCA.getText().toString().trim(), pwdCA.getText().toString().trim()));
                if(db != null ){
                    Toast.makeText(CreateAccountActivity.this,"The User was created", Toast.LENGTH_SHORT).show();
                    db.close();
                }
                Intent confirmaCuenta = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(confirmaCuenta);
            }
        });
    }

    public boolean validateEmail(String email){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        return mather.find();
    }

    public boolean validateLengthEmail(String email){
        return email.length() > 6;
    }

    public boolean validateLengthPwd(String pwd){
        return pwd.length() >= 8;
    }

    public boolean validateLowerCase(String pwd){
        for(int i = 0;  i<= pwd.length(); i ++){
            if(Character.isLowerCase(pwd.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public boolean validateUpperCase(String pwd){

        for(int i = 0;  i<= pwd.length(); i ++){
            if(Character.isUpperCase(pwd.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public boolean validateMatch(String pwd1, String pwd2){
        if(pwd1.equals(pwd2)){
            return true;
        }
        else{
            Toast.makeText(CreateAccountActivity.this,"The passwords should be equals", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean validateAll(String email, String pwd1, String pwd2){

        if (!validateLengthEmail(email)){
            Toast.makeText(CreateAccountActivity.this,"The email is incorrect",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateEmail(email)){
            Toast.makeText(CreateAccountActivity.this,"The email is incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateLengthPwd(pwd1)){
            Toast.makeText(CreateAccountActivity.this,"The pwd is should has at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateMatch(pwd1, pwd2)){
            Toast.makeText(CreateAccountActivity.this,"The passwords should be equals", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateLowerCase(pwd1)){
            Toast.makeText(CreateAccountActivity.this,"The password should has at least one lower case letter",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateUpperCase(pwd1)){
            Toast.makeText(CreateAccountActivity.this,"The password should has at least one lower case letter",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}