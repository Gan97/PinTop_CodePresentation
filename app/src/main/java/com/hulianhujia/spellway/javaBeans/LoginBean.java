package com.hulianhujia.spellway.javaBeans;

import java.io.Serializable;

/**
 * Created by FHP on 2017/7/7.
 */

public class LoginBean implements Serializable {

    /**
     * code : 1
     * msg : 登录成功
     * returnArr : {"username":"17781481226","password":"5135156","logintime":1499823889}
     */

    private int code;
    private String msg;
    /**
     * username : 17781481226
     * password : 5135156
     * logintime : 1499823889
     */

    private UserInfoBean returnArr;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfoBean getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(UserInfoBean returnArr) {
        this.returnArr = returnArr;
    }

    public static class UserInfoBean implements Serializable{
        private String username;
        private String password;
        private int logintime;
        private String type;
        public String getUsername() {
            return username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getLogintime() {
            return logintime;
        }

        public void setLogintime(int logintime) {
            this.logintime = logintime;
        }
    }
}
