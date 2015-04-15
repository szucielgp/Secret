package com.szucie.asua.secret.net;


import com.szucie.asua.secret.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUA on 2015/4/15.
 */
public class GetComment {
    public GetComment(String phone_md5,String token,int page,int perpage,String msgId,
                    final  SuccessCallBack successCallBack,final FailCallBack failCallBack){

        new NetConnection(Config.SERVER_URL,HttpMethord.POST,new NetConnection.SuccessCallback() {
            List<Comment> comments = new ArrayList<Comment>();

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch(jsonObject.getInt(Config.STATUS) ){
                        case Config.STATUS_SUCCESS:

                            int page = jsonObject.getInt(Config.KEY_PAGE);
                            int perpage = jsonObject.getInt(Config.KEY_PER_PAGE);
                            String msgId = jsonObject.getString(Config.KEY_MESS_ID);
                            JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_COMMENTS);
                            JSONObject jobj;
                            for (int i=0;i<jsonArray.length();i++){
                                jobj = jsonArray.getJSONObject(i);
                               comments.add(new Comment(jobj.getString(Config.KEY_COTENT),
                                       jobj.getString(Config.KEY_PHONE_MD5)));
                            }
                            if(successCallBack!=null){
                                successCallBack.onSuccess(page,perpage,comments,msgId);
                            }
                            else {
                                if (failCallBack!=null){
                                    failCallBack.onFail(Config.STATUS_FAIL);
                                }
                            }

                            break;
                        case Config.STATUS_TOKEN_INVALID:
                            if (failCallBack!=null){
                                failCallBack.onFail(Config.STATUS_TOKEN_INVALID);
                            }
                            break;
                        case Config.STATUS_FAIL:
                            if (failCallBack!=null){
                                failCallBack.onFail(Config.STATUS_FAIL);
                            }
                            break;
                        default:
                            if (failCallBack!=null){
                                failCallBack.onFail(Config.STATUS_FAIL);
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack!=null){
                        failCallBack.onFail(Config.STATUS_FAIL);
                    }
                }


            }
        },new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallBack!=null){
                    failCallBack.onFail(Config.STATUS_FAIL);
                }
            }
        },Config.KEY_ACTION,Config.ACTION_GET_COMMENT,
                Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,
                Config.KEY_PAGE,page+"",
                Config.KEY_PER_PAGE,perpage+"",
                Config.KEY_MESS_ID,msgId);
    }

    public static interface SuccessCallBack{
        void onSuccess(int page,int perpage,List<Comment> Comments,String msgId);
    };
    public static interface FailCallBack{
        void onFail(int errCode);
    }
}
