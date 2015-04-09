package com.szucie.asua.secret.actys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import android.support.v4.*;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.GetCode;
import com.szucie.asua.secret.net.Login;
import com.szucie.asua.secret.tools.Phone_md5;

public class LoginActivity extends ActionBarActivity {
    Button getCode,login;
    EditText phonenumber,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getCode = (Button)findViewById(R.id.getcode);
        login = (Button)findViewById(R.id.login);
        phonenumber = (EditText)findViewById(R.id.pnum);
        code = (EditText)findViewById(R.id.checkcode);
       // final String phone = phonenumber.getText().toString();
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phonenumber.getText())){
                    Toast.makeText(LoginActivity.this,getString(R.string.phone_num_not_null),Toast.LENGTH_SHORT).show();
                }else {
                   final ProgressDialog pd = ProgressDialog.show(LoginActivity.this,getResources().getString(R.string.conn_server),getResources().getString(R.string.conning_server));
                    new GetCode(phonenumber.getText().toString(),new GetCode.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                           pd.dismiss();
                            Toast.makeText(LoginActivity.this,getString(R.string.get_code_success),Toast.LENGTH_SHORT).show();
                        }
                    },new GetCode.FailCallback() {
                        @Override
                        public void onFail() {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this,getString(R.string.get_code_fail),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(code.getText())){
                    Toast.makeText(LoginActivity.this,getString(R.string.code_not_null),Toast.LENGTH_SHORT).show();
                }
                final ProgressDialog pd = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.conn_server),getResources().getString(R.string.conning_server));
                new Login(Phone_md5.md5(phonenumber.getText().toString()),code.getText().toString(),new Login.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {//new一个successCallback的时候，就将这个token达到了，最根本还是从netconnetion拿到的
                        if(token != null){
                System.out.println(">>>>>>>>>>>"+Phone_md5.md5(phonenumber.getText().toString()));
                System.out.println(">>>>>>>>>>>"+token);
                            pd.dismiss();
                            Config.setCacheToken(LoginActivity.this,token);//缓存token
                            Config.setCachePhoneNum(LoginActivity.this,phonenumber.getText().toString());

                            Toast.makeText(LoginActivity.this,getString(R.string.login_success),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,AtyTimeLine.class);
                            intent.putExtra(Config.KEY_TOKEN,token);
                            intent.putExtra(Config.KEY_PHONE,phonenumber.getText().toString());
                            startActivity(intent);
                            finish();
                        }


                      //  intent.putExtra(Config.)

                    }
                },new Login.FailCallback() {
                    @Override
                    public void onFail() {
                 //       pd.dismiss();
                        Toast.makeText(LoginActivity.this,getString(R.string.login_fail),Toast.LENGTH_SHORT).show();



                    }
                });
            }
        });
    }




}

