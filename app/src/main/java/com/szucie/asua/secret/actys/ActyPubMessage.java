package com.szucie.asua.secret.actys;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.PublishMessage;

public class ActyPubMessage extends ActionBarActivity {
    EditText pubMessage ;
    Button sendMessage;
    String phone_md5,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acty_pub_message);
           initView();
    }



    public void initView(){
        Intent data = getIntent();
        phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
        token = data.getStringExtra(Config.KEY_TOKEN);

        pubMessage = (EditText)findViewById(R.id.pubmessage);
        sendMessage = (Button)findViewById(R.id.bpubmessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(pubMessage.getText())){
                    Toast.makeText(ActyPubMessage.this, getResources().getString(R.string.pub_message_not_null), Toast.LENGTH_SHORT).show();
                }
                new PublishMessage(phone_md5,token,pubMessage.getText().toString(),new PublishMessage.SucccessCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ActyPubMessage.this, getResources().getString(R.string.pub_message_success), Toast.LENGTH_SHORT).show();
                        setResult(Config.ATY_NEED_FRESH);
                        finish();
                    }
                },new PublishMessage.FailCallBack() {
                    @Override
                    public void onFail(int errCode) {
                        Toast.makeText(ActyPubMessage.this, getResources().getString(R.string.pub_message_fail), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }













    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_acty_pub_message, menu);
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
