package com.hulianhujia.spellway.ease;

import android.util.Log;

import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * author: ShuaiTao
 * data: on 2017\9\23 0023 11:59
 * E-Mail: bill_dream@sina.com
 */

public class EaseUt {
    private static String TAG="infooo";
    private static EaseUser otherUser;
    public static HashMap<String,EaseUser> userMap;
    public static EaseUser getUserInfo(final String username){
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        if(username.equals(EMClient.getInstance().getCurrentUser())) {
            EaseUser easeUser = new EaseUser(EMClient.getInstance().getCurrentUser());
            easeUser.setAvatar(Contents.USER.getPicture());
            easeUser.setNickname(Contents.USER.getNickname());
            return easeUser;
        }
        else {
            if (userMap.get(username)!=null){
                Log.e(TAG, "getUserInfo: 到别人的头像了"+username+(userMap.get(username)==null)+userMap.get(username).getAvatar() );
                return userMap.get(username);
            }else if (userMap.get(username)==null){
                /*OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
                        .params("username",username)
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 拉取用户信息" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call: 拉取获得" + s);
                                Gson g = new Gson();
                                UserInfoBean bean = g.fromJson(s, UserInfoBean.class);
                                if (bean.getCode() == 1) {
                                    EaseUser easeUser = new EaseUser(username);
                                    easeUser.setAvatar(bean.getReturnArr().getPicture());
                                    easeUser.setNickname(bean.getReturnArr().getNickname());
                                    userMap.put(username, easeUser);
                                } else {

                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: 拉取失败"+throwable.toString() );
                            }
                        });*/
            }
            return userMap.get(username);
        }
    }

}
