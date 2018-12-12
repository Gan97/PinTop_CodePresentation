package com.hulianhujia.spellway.untils;

import android.text.TextUtils;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * author: ShuaiTao
 * data: on 2017\9\28 0028 11:13
 * E-Mail: bill_dream@sina.com
 */

public class EncodeUtil {
    private static String toHexUtil(int n){
        String rt="";
        switch(n){
            case 10:rt+="A";break;
            case 11:rt+="B";break;
            case 12:rt+="C";break;
            case 13:rt+="D";break;
            case 14:rt+="E";break;
            case 15:rt+="F";break;
            default:
                rt+=n;
        }
        return rt;
    }

    public static String toHex(int n){
        StringBuilder sb=new StringBuilder();
        if(n/16==0){
            return toHexUtil(n);
        }else{
            String t=toHex(n/16);
            int nn=n%16;
            sb.append(t).append(toHexUtil(nn));
        }
        return sb.toString();
    }

    public static String parseAscii(String str){
        StringBuilder sb=new StringBuilder();
        byte[] bs=str.getBytes();
        for(int i=0;i<bs.length;i++)
            sb.append(toHex(bs[i]));
        return sb.toString();
    }
    public static void main(){
        String s="xyz";
        System.out.println("转换后的字符串是："+parseAscii(s));
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
    public static String DeCode(String data,String key){
        String cr="";
        String str="";
        key=md5(key);
        int x = 0;
        data = new String(Base64.decode(data,Base64.DEFAULT));
        int ld = data.length();
        int lx = key.length();
        for (int i = 0; i < ld; i++) {
            if (x==lx){
                x=0;
            }
            cr=cr+key.substring(x,lx);
            x++;
        }

        for (int i = 0; i < ld; i++) {
            if (Integer.parseInt(parseAscii(data.substring(i,lx)))<Integer.parseInt(parseAscii(cr.substring(i,lx)))){
                str=str+asciiToString(((Integer.parseInt(parseAscii(data.substring(i,lx)))+256)-(Integer.parseInt(parseAscii(cr.substring(i,lx)))))+"");
            }
            else {
                str=str+asciiToString((Integer.parseInt(parseAscii(data.substring(i,lx)))-Integer.parseInt(parseAscii(cr.substring(i,lx))))+"");
            }
        }
        return str;
    }
}
