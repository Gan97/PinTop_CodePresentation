package com.hulianhujia.spellway.untils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.platform.comapi.map.C;
import com.hulianhujia.spellway.event.SaveSuccessEvent;
import com.hulianhujia.spellway.event.SaveSuccessEvent2;
import com.hulianhujia.spellway.event.SaveUserSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by FHP on 2017/7/13.
 */

public class SharedUtils {

    /**
     * desc:保存对象
     * @param context
     * @param obj 要保存的对象，只能保存实现了serializable的对象
     * modified:
     */
    private final static String FILENAME = "login_data_save";
    private final static String KEY = "loginResult";
    private final static String KEY2 = "userInfo";
    private final static String LOCATION_KEY = "loc";
    private static String TAG="info";

    public static void saveLat(Context context,String lat){
        SharedPreferences.Editor edit = context.getSharedPreferences(LOCATION_KEY, Context.MODE_PRIVATE).edit();
        edit.putString("lat",lat);
        edit.commit();
    }
    public static void saveLon(Context context,String lon){
        SharedPreferences.Editor edit = context.getSharedPreferences(LOCATION_KEY, Context.MODE_PRIVATE).edit();
        edit.putString("lon",lon);
        edit.commit();
    }
    public static String readLat(Context context){
        SharedPreferences sp = context.getSharedPreferences(LOCATION_KEY, Context.MODE_PRIVATE);
        return sp.getString("lat","");
    }
    public static String readLon(Context context){
        SharedPreferences sp = context.getSharedPreferences(LOCATION_KEY, Context.MODE_PRIVATE);
        return sp.getString("lon","");
    }
    public static void saveObject(Context context, Object obj,String type){
        try {
            Log.e(TAG, "saveObject: 开始" );

            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            Log.e(TAG, "saveObject: 写入" );
            os.writeObject(obj);
            Log.e(TAG, "saveObject: 关流" );
            os.close();
            bos.close();
            Log.e(TAG, "saveObject: 关流完成" );
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(KEY, bytesToHexString);
            sharedata.commit();
            EventBus.getDefault().post(new SaveSuccessEvent(type));
            Log.e(TAG, "saveObject: 成功" );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }
    public static void saveObject2(Context context, Object obj,String type){
        try {
            Log.e(TAG, "saveObject: 开始" );

            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            Log.e(TAG, "saveObject: 写入" );
            os.writeObject(obj);
            Log.e(TAG, "saveObject: 关流" );
            os.close();
            bos.close();
            Log.e(TAG, "saveObject: 关流完成" );
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(KEY, bytesToHexString);
            sharedata.commit();
            EventBus.getDefault().post(new SaveSuccessEvent2(type));
            Log.e(TAG, "saveObject: 成功" );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }
    public static void saveUser(Context context, Object obj){
        try {
            Log.e(TAG, "saveUser: 开始" );

            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit();
            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            Log.e(TAG, "saveObject: 写入" );
            os.writeObject(obj);
            Log.e(TAG, "saveObject: 关流" );
            os.close();
            bos.close();
            Log.e(TAG, "saveObject: 关流完成" );
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(KEY2, bytesToHexString);
            sharedata.commit();
//            EventBus.getDefault().post(new SaveUserSuccessEvent());
            Log.e(TAG, "saveObject: 成功" );
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("", "保存obj失败");
        }
    }


    /**
     * desc:将数组转为16进制
     * @param bArray
     * @return
     * modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if(bArray == null){
            return null;
        }
        if(bArray.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    /**
     * desc:获取保存的Object对象
     * @param context
     * @return
     * modified:
     */
    public static Object readObject(Context context){
        try {
            SharedPreferences sharedata = context.getSharedPreferences(FILENAME, 0);
            if (sharedata.contains(KEY)) {
                String string = sharedata.getString(KEY, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    public static Object readUserInfo(Context context){
        try {
            SharedPreferences sharedata = context.getSharedPreferences(FILENAME, 0);
            if (sharedata.contains(KEY2)) {
                String string = sharedata.getString(KEY2, "");
                if(TextUtils.isEmpty(string)){
                    return null;
                }else{
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is=new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }
    /**
     * desc:将16进制的数据转为数组
     * <p>创建人：聂旭阳 , 2014-5-25 上午11:08:33</p>
     * @param data
     * @return
     * modified:
     */
    public static byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch3 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch3 = (hex_char1-55)*16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch4 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch4 = hex_char2-55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3+int_ch4;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

}
