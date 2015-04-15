package com.szucie.asua.secret.actys;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.szucie.asua.secret.Config;
//import com.szucie.asua.secret.R;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.ld.MyContacts;
import com.szucie.asua.secret.net.Message;
import com.szucie.asua.secret.net.TimeLine;
import com.szucie.asua.secret.net.UploadContacts;
import com.szucie.asua.secret.tools.Phone_md5;

import java.util.List;

public class AtyTimeLine extends Activity {//当其中有listView这些时，一般从他继承

    String phoneNum,token,phone_md5;
    public ListView listView ;//设置为全局的可以让下面的使用
    public static  MessageListAdapter messageListAdapter=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acy_time_line);
        Intent intent = getIntent();
       // listView = (ListView) findViewById(android.R.id.list);//名字必须是list！！
        listView = (ListView) findViewById(R.id.atytimeline);//名字必须是list！！
        token = intent.getStringExtra(Config.KEY_TOKEN);
        phoneNum = intent.getStringExtra(Config.KEY_PHONE);
        phone_md5 = Phone_md5.md5(phoneNum);
        //为listView设置监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message msg = (Message) messageListAdapter.getItem(position);
                Intent i = new Intent(AtyTimeLine.this, AtyMessage.class);
                i.putExtra(Config.KEY_MESSAGE, msg.getMessage());
                i.putExtra(Config.KEY_MESS_ID, msg.getMes_id());
                i.putExtra(Config.KEY_PHONE_MD5, msg.getPhone_md5());
                i.putExtra(Config.KEY_TOKEN, token);
                startActivity(i);
            }
        });
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
        final ProgressDialog pd = ProgressDialog.show(AtyTimeLine.this,
                getResources().getString(R.string.get_message),
                getResources().getString(R.string.get_message_ing));
        final TimeLine timeLine = new TimeLine(phone_md5,token,1,20,new TimeLine.SuccessCallBack() {

            @Override
            public void onSuccess(int page, int perpage, List<Message> list) {
                pd.dismiss();
               messageListAdapter = new MessageListAdapter(AtyTimeLine.this,list);
               // messageListAdapter.clear();
                listView.setAdapter(messageListAdapter);
                Toast.makeText(AtyTimeLine.this,getResources().getString(R.string.get_message_success),Toast.LENGTH_SHORT).show();

            }
        },new TimeLine.FailCallBack() {
            @Override
            public void onFail(int errCode) {

                pd.dismiss();
                if(errCode == Config.STATUS_TOKEN_INVALID){
                    Intent intent = new Intent(AtyTimeLine.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(AtyTimeLine.this,getResources().getString(R.string.get_message_fail),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//
//        Message msg = (Message) messageListAdapter.getItem(position);
//        Intent i = new Intent(AtyTimeLine.this, AtyMessage.class);
//        i.putExtra(Config.KEY_MESSAGE, msg.getMessage());
//        i.putExtra(Config.KEY_MESS_ID, msg.getMes_id());
//        i.putExtra(Config.KEY_PHONE_MD5, msg.getPhone_md5());
//        i.putExtra(Config.KEY_TOKEN, token);
//        startActivity(i);
//    }如果这样写，要去在layout中为listView添加标签！！！

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
