package com.example.broadcast_forceoffline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private AutoCompleteTextView account;
    private EditText password;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.email_sign_in_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accounts = account.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if(accounts.equals("123")&&pwd.equals("123")){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"account or password is invalid.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

