package com.szucie.asua.secret.net;

import android.content.Context;

import com.szucie.asua.secret.Config;
import com.szucie.asua.secret.ld.MyContacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUA on 2015/4/9.
 */
public class UploadContacts {
   public  UploadContacts(String phone_md5,String token,String jSonArr, final SuccessCallback successCallback,
                          final FailCallback failCallback,String ... kvs ){

       //jSonArr = MyContacts.getContacts(context);

       new NetConnection(Config.SERVER_URL,HttpMethord.POST,new NetConnection.SuccessCallback() {
           @Override
           public void onSuccess(String result) {
               try {
                   JSONObject jsonObject = new JSONObject(result);
                   switch (jsonObject.getInt(Config.STATUS)){
                       case Config.STATUS_SUCCESS:
                           if(successCallback!=null){
                               successCallback.onSuccess();
                           }else {
                               if (failCallback!=null){
                                   failCallback.onFail(Config.STATUS_FAIL);
                               }
                           }
                           break;
                       case Config.STATUS_TOKEN_INVALID:
                           if (failCallback!=null){
                               failCallback.onFail(Config.STATUS_TOKEN_INVALID);
                           }
                           break;
                       default:
                           if (failCallback!=null){
                               failCallback.onFail(Config.STATUS_FAIL);
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
               if (failCallback!=null){
                   failCallback.onFail(Config.STATUS_FAIL);
               }
           }
       },Config.KEY_ACTION,Config.ACTION_UPLOAD_CONTACKS,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_TOKEN,token,Config.ACTION_CONTACKS,jSonArr);

   }

    public static   interface  SuccessCallback{

        void onSuccess();
    }

    public static  interface  FailCallback{
        void onFail(int errCode);//设置errCode是为了在上传的时候出现不同的情况去做不同的操作
    }
}



