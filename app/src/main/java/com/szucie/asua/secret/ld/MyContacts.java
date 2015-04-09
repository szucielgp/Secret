package com.szucie.asua.secret.ld;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.szucie.asua.secret.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ASUA on 2015/4/8.
 */
public class MyContacts {
    //返回一个JSon数组的格式

    public static  String getContacts(Context context){
      Cursor cursor= context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject= new JSONObject();
        while(cursor.moveToNext()){
            String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            try {
               // jsonObject ;
                jsonObject.put(Config.KEY_PHONE,phoneNum);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(phoneNum);

        }
        return jsonArray.toString();
    }
}
