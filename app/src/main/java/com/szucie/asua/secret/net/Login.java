package com.szucie.asua.secret.net;

import com.szucie.asua.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUA on 2015/4/9.
 */
public class Login {
    public Login(String phone_md5,String code,final SuccessCallback successCallback,final FailCallback failCallback ){
            new NetConnection(Config.SERVER_URL,HttpMethord.POST,new NetConnection.SuccessCallback() {
                @Override
                public void onSuccess(String result) {

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        switch (jsonObject.getInt(Config.STATUS)){
                            case Config.STATUS_SUCCESS:
                                if (successCallback!=null){
                                    successCallback.onSuccess(jsonObject.getString(Config.KEY_TOKEN));
                                    //通过接口拿到token可以判断登陆时间等
                                }
                                else {
                                    if (failCallback!=null){
                                    failCallback.onFail();
                                    }
                                }


                                break;
                            default:
                                if(failCallback!=null){
                                       failCallback.onFail();
                                }
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (failCallback!=null){
                            failCallback.onFail();
                        }
                    }

                }
            },new NetConnection.FailCallback() {
                @Override
                public void onFail() {
                        failCallback.onFail();
                }
            },Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_CODE,code);
    }


    public static interface SuccessCallback{
        void onSuccess(String token);//服务器返回的token返回到了这里
    }
    public static interface FailCallback{
        void onFail();
    }
}
