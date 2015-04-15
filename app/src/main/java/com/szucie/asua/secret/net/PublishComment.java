package com.szucie.asua.secret.net;

import com.szucie.asua.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUA on 2015/4/15.
 */
public class PublishComment {
    public PublishComment(String phone_md5,String token,String content,String msgId,
                          final SucccessCallBack succcessCallBack, final FailCallBack failCallBack){

        new NetConnection(Config.SERVER_URL,HttpMethord.POST,new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (succcessCallBack!=null){
                                succcessCallBack.onSuccess();
                            }
                            break;
                        case Config.STATUS_FAIL:
                            if (failCallBack!=null){
                                failCallBack.onFail(Config.STATUS_FAIL);
                            }
                            break;
                        case Config.STATUS_TOKEN_INVALID:
                            if (failCallBack!=null){
                                failCallBack.onFail(Config.STATUS_TOKEN_INVALID);
                            }
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallBack!=null){
                    failCallBack.onFail(Config.STATUS_FAIL);
                }
            }
        },Config.KEY_ACTION,Config.ACTION_PUB_COMMENT,
                Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKEN,token,
                Config.KEY_COTENT,content,
                Config.KEY_MESS_ID,msgId);
    }
    public static interface SucccessCallBack{
        void onSuccess();
    }
    public static interface FailCallBack{
        void onFail(int errCode);
    }
}
