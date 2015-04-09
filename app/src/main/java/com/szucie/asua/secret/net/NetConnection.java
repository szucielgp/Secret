package com.szucie.asua.secret.net;

import android.os.AsyncTask;

import com.szucie.asua.secret.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;



/**
 * Created by ASUA on 2015/4/8.
 */
public class NetConnection {
    public NetConnection(final String url,final HttpMethord methord,final  SuccessCallback successCallback,final  FailCallback failCallback,
                       final   String ... kvs ){

        new AsyncTask<Void,Void,String>(){//void的V是大写的因为这里是个一个类型
            @Override
            protected String doInBackground(Void... params) {

                StringBuffer stringBuffer = new StringBuffer();
                String stringBuffer1 ;
                StringBuffer result = new StringBuffer();
                //首先要将字符对变成一组常量，0.2这些位置放的是键
                for (int i=0;i<kvs.length;i+=2) {//这里不是params的长度
                    stringBuffer.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }
              stringBuffer1 =  stringBuffer.substring(0,stringBuffer.length()-1);
    System.out.println("<<<<<"+stringBuffer1);
                try {
                    URLConnection uc =new URL(url).openConnection() ;
                    switch (methord) {
//往服务器写
                        case POST:
                            uc.setDoOutput(true);//设置可以往外写
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHASET));
                           // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream()));
                            //添加字符编码
                            bufferedWriter.write(stringBuffer1);
                            bufferedWriter.flush();
                            break;
                        case GET:
                            try {
                                new URL(url+"?"+stringBuffer.toString()).openConnection();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
//从服务器读取
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream(),Config.CHASET));
                   // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String line ;

                    while ((line =bufferedReader.readLine())!= null) {
                        result.append(line);
                    }
                    System.out.println("Result:"+result);
                    return result.toString();//如果返回正确，在这里就已经返回了
                }catch (Exception e){
                    e.printStackTrace();
                }



  System.out.println(">>>>>>"+url+"/"+ result);
                return null;//其他的情况返回null，之前因为之前返回的是null等，所以解析出错！
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null) {
                    if (successCallback != null) {
                        successCallback.onSuccess(result);//通过接口来将数据传递给外界
                    }
                } else {
                    if (failCallback!=null){
                       failCallback.onFail();
                    }
                }
            }
        }.execute();

    }
    //使用接口在各个activity之间传递数据。都有相同的静态接口
    public static interface SuccessCallback{void onSuccess(String result);}
    public static interface FailCallback{void onFail();}
}
