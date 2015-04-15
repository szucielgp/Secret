package com.szucie.asua.secret;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import com.szucie.asua.secret.actys.ActyPubMessage;
import com.szucie.asua.secret.actys.AtyMessage;
import com.szucie.asua.secret.actys.AtyTimeLine;
import com.szucie.asua.secret.actys.LoginActivity;
import com.szucie.asua.secret.ld.MyContacts;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拿到缓存的token和号码
        String token = Config.getCashedToken(this);
        String phoneNum = Config.getCachedPhoneNum(this);
        System.out.println(MyContacts.getContacts(this));
       // startActivity(new Intent(MainActivity.this, ActyPubMessage.class));
        if (token!=null && phoneNum!=null) {//因为缓存了token，所以再次打开的时候，不再执行登陆的操作
            Intent intent = new Intent(this,AtyTimeLine.class);
            intent.putExtra(Config.KEY_TOKEN,token);
            intent.putExtra(Config.KEY_PHONE,phoneNum);
            startActivity(intent);
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish();
        //加上这句会按返回键后，直接退出。

    }



}
