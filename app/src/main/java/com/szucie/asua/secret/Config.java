package com.szucie.asua.secret;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ASUA on 2015/4/8.
 */
public class Config {

    public static final String APP_ID = "com.szucie.asua.secret";
    public static final String SERVER_URL = "http://192.168.155.1:8080/SecretServer/index.jsp";
    //请求的key常量的配置
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ACTION = "action";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PHONE_MD5 = "phone_md5";
    public static final String KEY_CODE = "code";
    public static final  String KEY_TIMELINE ="timeline" ;
    public static final  String  KEY_PAGE = "page" ;
    public static final  String  KEY_PER_PAGE = "perpage" ;
    public static final  String  KEY_STATUS = "status" ;
    public static final  String  KEY_MESSAGE = "msg" ;
    public static final String KEY_MESS_ID ="msgId" ;
    public static final  String ACTION_GET_COMMENT = "get_comment";
    public static final  String KEY_COMMENTS = "comments";
    public static final String  KEY_COTENT = "content";

    public static final  String CHASET ="utf-8" ;
    //请求的action类型的常量配置
    public static final  String ACTION_GET_CODE="send_pass";
    public static final  String ACTION_LOGIN="login";
    public static final  String  ACTION_UPLOAD_CONTACKS = "upload_contacts";
    public static final  String  ACTION_CONTACKS = "contacks";
    public static final  String ACTION_TIMELINE ="timeline" ;
    public static final  String ACTION_PUB_COMMENT ="pub_comment" ;
    public static final String ACTION_PUB_MESSAGE = "publish";

    //配置返回状态的常量
    public static final  String STATUS = "status";
    public static final  int STATUS_SUCCESS = 1;
    public static final  int STATUS_FAIL = 0;
    public static final  int STATUS_TOKEN_INVALID = 2;
    public static final  int ATY_NEED_FRESH = 0;


    // public static final  String KEY_ITEMS = "items";



    //其他的地方要直接使用这个类将这个方法申明为static的,一个得到缓存的token，一个设置token
    public static String getCashedToken(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_APPEND).getString(KEY_TOKEN,null);
    }

    public static void setCacheToken(Context context,String token){//两个参数，一个context，标示
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID, Context.MODE_APPEND).edit();
        e.putString(KEY_TOKEN,token);
        e.commit();
    }
//获取缓存的号码，得到缓存的号码
  public static String getCachedPhoneNum(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_APPEND).getString(KEY_PHONE,null);
    }

    public static void setCachePhoneNum(Context context,String phoneNub){//两个参数，一个context，标示
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_APPEND).edit();
        e.putString(KEY_PHONE,phoneNub);
        e.commit();
    }
}
