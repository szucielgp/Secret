package com.szucie.asua.secret.net;

/**
 * Created by ASUA on 2015/4/14.
 */
public class Message {
    String message,mes_id,phone_md5;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMes_id(String mes_id) {
        this.mes_id = mes_id;
    }

    public void setPhone_md5(String phone_md5) {
        this.phone_md5 = phone_md5;
    }

    public String getMes_id() {

        return mes_id;
    }

    public String getPhone_md5() {
        return phone_md5;
    }
}
