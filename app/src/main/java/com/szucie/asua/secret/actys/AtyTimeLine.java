package com.szucie.asua.secret.actys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.ld.MyContacts;
import com.szucie.asua.secret.net.UploadContacts;
import com.szucie.asua.secret.tools.Phone_md5;

public class AtyTimeLine extends ActionBarActivity {

    String phoneNum,token,phone_md5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acy_time_line);
        Intent intent = getIntent();
        token = intent.getStringExtra(Config.KEY_TOKEN);
        phoneNum = intent.getStringExtra(Config.KEY_PHONE);
        phone_md5 = Phone_md5.md5(phoneNum);

        new UploadContacts(phone_md5,token, MyContacts.getContacts(AtyTimeLine.this),new UploadContacts.SuccessCallback() {
            @Override
            public void onSuccess() {

               loadMessage();
            }


        },new UploadContacts.FailCallback() {
            @Override
            public void onFail(int errCode) {

                if (errCode==Config.STATUS_FAIL){

                    startActivity( new Intent(AtyTimeLine.this,LoginActivity.class));
                    finish();
                }else if(errCode==Config.STATUS_TOKEN_INVALID){
                    startActivity( new Intent(AtyTimeLine.this,LoginActivity.class));
                    finish();
                }
            }
        });


    }

    public void loadMessage(){
        System.out.println("<<fddfdf");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acy_time_line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
