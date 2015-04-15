package com.szucie.asua.secret.net;

import com.szucie.asua.secret.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUA on 2015/4/14.
 */
public class TimeLine {
    public TimeLine(final String phone_md5,final String token,final int page,final int perpage,final SuccessCallBack successCallBack,final FailCallBack failCallBack) {
        new NetConnection(Config.SERVER_URL, HttpMethord.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                List<Message> mList = new ArrayList<Message>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.STATUS)){
                        case Config.STATUS_SUCCESS:

                            int page = jsonObject.getInt(Config.KEY_PAGE);
                            int perpage = jsonObject.getInt(Config.KEY_PER_PAGE);
                            JSONArray jsonArray = jsonObject.getJSONArray(Config.KEY_TIMELINE);
                            JSONObject jobj;
                            for (int i=0;i<jsonArray.length();i++){
                                jobj = jsonArray.getJSONObject(i);
                                Message mess =  new Message();
                                mess.setMessage(jobj.getString(Config.KEY_MESSAGE));
                                mess.setMes_id(jobj.getString(Config.KEY_MESS_ID));
                                mess.setPhone_md5(jobj.getString(Config.KEY_PHONE_MD5));
                                mList.add(mess);
                            }
                            if(successCallBack!=null){
                                successCallBack.onSuccess(page,perpage,mList);
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
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

                if (failCallBack!=null){
                    failCallBack.onFail(Config.STATUS_FAIL);
                }
            }
        }, Config.KEY_ACTION, Config.ACTION_TIMELINE,
                Config.KEY_PHONE_MD5, phone_md5,
                Config.KEY_TOKEN, token,
                Config.KEY_PAGE, page+"",
                Config.KEY_PER_PAGE, perpage+"");//这里是字符串键值对，不能穿int型进来

    }

    public static interface SuccessCallBack{
        void onSuccess(int page,int perpage,List<Message> list);
    };
    public static interface FailCallBack{
        void onFail(int errCode);
    }
}
