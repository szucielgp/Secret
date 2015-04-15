package com.szucie.asua.secret.actys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.R;
import com.szucie.asua.secret.net.Comment;
import com.szucie.asua.secret.net.GetComment;
import com.szucie.asua.secret.net.PublishComment;
import com.szucie.asua.secret.tools.Phone_md5;

import java.util.List;

public class AtyMessage extends ActionBarActivity {
    public TextView textView;
    public ListView listView;
    public EditText editText;
    public Button sendComment;
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
        editText = (EditText)findViewById(R.id.comment);
        sendComment = (Button)findViewById(R.id.sendcomment);

        textView.setText(message);

      getComments();//一开始加载一次，然后等发布评论后，再去加载一次，刷新列表


        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editText.getText())){
                    Toast.makeText(AtyMessage.this,getResources().getString(R.string.comment_not_null),Toast.LENGTH_SHORT).show();
                    return;//如果评论为空，就不往下执行
                }
               new PublishComment(Phone_md5.md5(Config.getCachedPhoneNum(AtyMessage.this)),token,
                       editText.getText().toString(),message_id,
                       new PublishComment.SucccessCallBack() {
                           @Override
                           public void onSuccess() {
                               editText.setText("");
                     Toast.makeText(AtyMessage.this,getResources().getString(R.string.publish_comment_success),Toast.LENGTH_SHORT).show();
                                getComments();
                           }
                       },new PublishComment.FailCallBack() {
                   @Override
                   public void onFail(int errCode) {
                       Toast.makeText(AtyMessage.this,getResources().getString(R.string.publish_comment_fail),Toast.LENGTH_SHORT).show();

                   }
               });
            }
        });
    }


    public void getComments(){
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
