package com.szucie.asua.secret.net;

import com.szucie.asua.secret.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUA on 2015/4/8.
 */
public class GetCode {

    public  GetCode(String phone, final SuccessCallback successCallback, final FailCallback failCallback){

        new NetConnection(Config.SERVER_URL,HttpMethord.POST,new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.STATUS)){
                        case Config.STATUS_SUCCESS:
                            if (successCallback != null) {
                                successCallback.onSuccess();
                            } else {
                                failCallback.onFail();
                            }
                            break;
                        default:
                            if (failCallback!=null){
                                failCallback.onFail();
                            }
                           break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new NetConnection.FailCallback() {
            @Override
            public void onFail() {
                failCallback.onFail();

            }
        },Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE,phone);

    }


    public static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail();
    }
}
