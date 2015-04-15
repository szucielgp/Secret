package com.szucie.asua.secret.actys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.Comment;
import com.szucie.asua.secret.net.GetComment;

import java.util.List;

public class AtyMessage extends ActionBarActivity {
    public TextView textView;
    public ListView listView;
    public String phone_md5,token, message,message_id;
    public CommentListAdapter commentListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aty_message);
        Intent intent = getIntent();
        message = intent.getStringExtra(Config.KEY_MESSAGE);
        message_id = intent.getStringExtra(Config.KEY_MESS_ID);
        phone_md5 = intent.getStringExtra(Config.KEY_PHONE_MD5);
        token = intent.getStringExtra(Config.KEY_TOKEN);
        textView = (TextView) findViewById(R.id.tvmessage);
        listView=(ListView)findViewById(R.id.commentlist);
        textView.setText(message);
       final  ProgressDialog pd = ProgressDialog.show(AtyMessage.this,getResources().getString(R.string.get_comment),getResources().getString(R.string.get_comment_ing));
        new GetComment(phone_md5,token,1,20,message_id,new GetComment.SuccessCallBack() {
            @Override
            public void onSuccess(int page, int perpage, List<Comment> Comments, String msgId) {
                pd.dismiss();
                Toast.makeText(AtyMessage.this,getResources().getString(R.string.get_comment_success),Toast.LENGTH_SHORT).show();
                commentListAdapter = new CommentListAdapter(AtyMessage.this,Comments);
                listView.setAdapter(commentListAdapter);

            }
        },new GetComment.FailCallBack() {
            @Override
            public void onFail(int errCode) {

                pd.dismiss();
                if (errCode==Config.STATUS_FAIL){
                    Toast.makeText(AtyMessage.this,getResources().getString(R.string.get_comment_fail),Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(AtyMessage.this,LoginActivity.class));
                }
                else if(errCode == Config.STATUS_TOKEN_INVALID){
                    startActivity(new Intent(AtyMessage.this,LoginActivity.class));

                }

            }
        });
    }



}
