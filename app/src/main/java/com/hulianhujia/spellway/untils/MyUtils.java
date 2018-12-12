package com.hulianhujia.spellway.untils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hulianhujia.spellway.R;
import com.zhihu.matisse.ui.MatisseActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by FHP on 2017/3/20.
 */

public class MyUtils {
    private  static Context context;
    public MyUtils(Context context) {
        this.context = context;
    }


    public static class AtyContainer {
        private static AtyContainer instance ;
        private static List<Activity> activityStack = new ArrayList<Activity>();
        public static AtyContainer getInstance() {
            if (instance==null){
                instance= new AtyContainer();
            }else {

            }
            return instance;
        }
        public void addActivity(Activity aty) {
            activityStack.add(aty);
        }
        public void removeActivity(Activity aty) {
            activityStack.remove(aty);
        }
        /**
         * 结束所有Activity
         */
        public void finishAllActivity() {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    //获取版本
    public static  void writeLoc(Context context,String lat,String lon){
       SharedPreferences sharedPreferences =context.getSharedPreferences("loc",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("lat",lat);
        edit.putString("lon",lon);
        edit.commit();
    }
    public static String readLat(Context context){
        SharedPreferences sp = context.getSharedPreferences("loc", Context.MODE_PRIVATE);
        String lat = sp.getString("lat", "");
        return lat;
    }
    public static String readLon(Context context){
        SharedPreferences sp = context.getSharedPreferences("loc", Context.MODE_PRIVATE);
        String lon = sp.getString("lon", "");
        return lon;
    }
    public String getVersionName() throws Exception {
// 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
// getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    //获取md5
    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void trans(FragmentManager manager, int layId, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layId, fragment);
        transaction.commit();
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
                //底部导航栏
//                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = 100;
        int height = 100;
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置指示器
    public static void setIndicator(int size, Context context, int ResLayoutId, final LinearLayout parent, ViewPager viewPager) {
        for (int j = 0; j < size; j++) {
            View view1 = LayoutInflater.from(context).inflate(ResLayoutId, null);
            Button bt = (Button) view1;
            bt.setBackgroundResource(R.drawable.radiobt_slct);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.indector), (int) context.getResources().getDimension(R.dimen.indector));
            params.setMargins(10, 0, 0, 0);
            bt.setLayoutParams(params);
            parent.addView(bt);
            View childAt = parent.getChildAt(0);
            childAt.setBackgroundResource(R.drawable.radiobt_slct1);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < parent.getChildCount(); i++) {
                    View childAt = parent.getChildAt(i);
                    childAt.setBackgroundResource(R.drawable.radiobt_slct);
                }
                parent.getChildAt(position).setBackgroundResource(R.drawable.radiobt_slct1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void setIndicator(int size, Context context, int ResLayoutId, final LinearLayout parent) {
        for (int j = 0; j < size; j++) {
            View view1 = LayoutInflater.from(context).inflate(ResLayoutId, null);
            Button bt = (Button) view1;
            bt.setBackgroundResource(R.drawable.radiobt_slct);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.indector), (int) context.getResources().getDimension(R.dimen.indector));
            params.setMargins(10, 0, 0, 0);
            bt.setLayoutParams(params);
            parent.addView(bt);
            View childAt = parent.getChildAt(0);
            childAt.setBackgroundResource(R.drawable.radiobt_slct1);
        }
    }

    //
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {

        }
        return null;
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }
    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToFormat(long timeStamp) {
        String times = timeStamp + "";
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 10) {
            return time / 3600 / 24 + "天前";
        } else {
            return timeStampToStr(timeStamp);
        }
    }
    /*秒差距*/
    public static long secLeft(long timeStamp){
        String times = timeStamp + "";
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;
        return time;
    }
    /**
     * 将一个时间戳转换成提示性时间字符串，今天 xxx；
     *
     * @param timeStamp
     * @return
     */
    public static String convertTimeToCustom(long timeStamp) {
        String times = timeStamp + "";
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        long curTime = zero / (long) 1000;

        long time = curTime - timeStamp;
        if (time<0){
            return "今天";
        }else if (time>0&&time<60*60*24){
            return "昨天";
        }else if (time>3600*24&&time<60*60*48){
            return "前天";
        }
        else {
            return timeStampToMD(timeStamp);
        }

    }
    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStr(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToStrYMD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToStrY(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    /**
     * 时间戳转化为小时分钟时间格式
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToHM(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static String timeStampToMD(long timeStamp) {
        String times = String.valueOf(timeStamp);
        if (times.length() >= 11) {
            timeStamp = Long.parseLong(times) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }
    public static List<String> initFake(){
        List<String> fake = new ArrayList<>();
        fake.add("哈雷克斯之火葬魔咒");
        fake.add("加拉隆的深渊之核");
        fake.add("塔拉克的天坠之火");
        fake.add("来自虚空的火焰撞击");
        return fake;
    }
/*
* 资源转uri
* */
    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    public static Uri ResourceIdToUri(Context context, int resourceId)
    {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }
    public static void toast(String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    private static final double EARTH_RADIUS = 6378137;
    public static String distanceOfTwoPoints(double lat1,double lng1,
                                             double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;

        DecimalFormat df = new DecimalFormat("0.0");
        String format = df.format(s/1000);

        return format+"km";
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
