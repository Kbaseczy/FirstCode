package com.example.broadcast_forceoffline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor ;
    private AutoCompleteTextView account;
    private EditText password;
    private Button submit;
    private CheckBox rememberPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //todo 每个应用都有一个默认的配置文件preferences.xml，使用getDefaultSharedPreferences获取。
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        account = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rememberPass = findViewById(R.id.rememberPass);
        submit = findViewById(R.id.email_sign_in_button);
        boolean isRemember = pref.getBoolean("remember password",false);
        //todo 判断checkBox 获取用户信息
        if(isRemember){
            String accountR = pref.getString("account","");
            String pwdR = pref.getString("password","");
            account.setText(accountR);
            password.setText(pwdR);
            rememberPass.setChecked(true);
        }
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String accounts = account.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                if(accounts.equals("123")&&pwd.equals("123")){
                    //TODO 判断checkBox 存储用户信息
                    editor = pref.edit();  //todo 缺少此条初始化.造成空对象引用
                    if(rememberPass.isChecked()){
                        //todo :Editor.putString(java.lang.String, java.lang.String)' on a null object reference
                        editor.putString("account",accounts);
                        editor.putString("password",pwd);
                        editor.putBoolean("remember password",true);
                    }else {
                        editor.clear();
                    }
                    editor.apply(); //todo 保存提交

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

