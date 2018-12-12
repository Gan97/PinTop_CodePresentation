package com.hulianhujia.spellway.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.IconDialog;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.SmsBean;
import com.hulianhujia.spellway.untils.CodeUtils;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017\6\21 0021.
 */

public class GuideFragment extends Fragment implements IconDialog.TakePhoto, IconDialog.Photos {

    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etPwdTwice)
    EditText etPwdTwice;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.btIdCard)
    ImageView btIdCard;
    @Bind(R.id.btGuideCard)
    ImageView btGuideCard;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btSend)
    TextView btSend;
    @Bind(R.id.etJym)
    EditText etJym;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.imgYz)
    ImageView imgYz;
    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;

    private CodeUtils codeUtils;
    private IconDialog iconDialog;
    private int REQUEST_TAKE_PHOTO = 1;
    private int ALBUM_REQUEST_CODE = 2;
    private String mCurrentPhotoPath;
    private boolean flag = true;
    private String TAG = "info";
    private int time=60;
    private File idCard;
    private File guideCard;
    private List<File> idCards=new ArrayList<>();
    private List<File> guideCards=new ArrayList<>();
    private String smsCode;
    private LoadingDialog loadingDialog;
    private Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            time--;
            if (time<=0){
                time=60;
                btSend.setClickable(true);
                btSend.setText("获取验证码");
            }else {
                btSend.setClickable(false);
                btSend.setText(time+"s");
                handler.sendEmptyMessageDelayed(1,1000);
            }
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_guide, container, false);
        ButterKnife.bind(this, view);
        init();
        loadingDialog=new LoadingDialog(getContext());
        return view;
    }

    private void init() {
        iconDialog = new IconDialog(getActivity());
        iconDialog.setPhotos(this);
        iconDialog.setTakePhoto(this);
        initCodeUtils();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /*使能图片验证码控件*/
    private void initCodeUtils() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        imgYz.setImageBitmap(bitmap);
    }
    /*使能短信验证码*/
    private void initYzm() {
        EventHandler eh = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Log.e(TAG, "afterEvent:进入回调 ");
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Log.e(TAG, "afterEvent:进入回调完成");
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e(TAG, "提交验证码成功");
                        regisGuide();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.e(TAG, "发送成功，请查收");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        ArrayList<HashMap<String, Object>> hashMaps = (ArrayList<HashMap<String, Object>>) data;
                        for (HashMap<String, Object> each : hashMaps) {
                            Log.d("TAG", each.toString());
                        }
                    }
                } else {
                    final Throwable throwable = (Throwable) data;
                    Log.e(TAG, event + "sdsdsd" + throwable.toString());
                    ToastUtils.toast(throwable.toString());
                }
            }
        };
        //注册短信验证的监听
        SMSSDK.registerEventHandler(eh);
    }
    private void regisGuide(){
        OkGo.post(Urls.PUBLIC_URL + Urls.REGISER)
                .params("username", etPhone.getText().toString())
                .params("password", etPwd.getText().toString())
                .params("idCard",idCard)
                .params("certificate",guideCard)
                .params("type", "2")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 注册导游开始" );
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: 注册导游返回"+s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call: 注册导游错误"+throwable.toString() );
            }
        });
    }
    @OnClick({R.id.btIdCard, R.id.btGuideCard, R.id.btSend,R.id.textView3,R.id.imgYz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btIdCard:
                flag = true;
                iconDialog.show();
                break;
            case R.id.btGuideCard:
                flag = false;
                iconDialog.show();
                break;
            case R.id.btSend:
                OkGo.post(Urls.PUBLIC_URL+Urls.SMS_URL)
                        .params("phone",etPhone.getText().toString())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 短信验证码开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码获得" + s);
                                Gson g = new Gson();
                                SmsBean smsBean = g.fromJson(s, SmsBean.class);
                                if (smsBean.getCode()==1){
                                    smsCode=smsBean.getReturnArr().getMsgCode();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码错误"+throwable.toString() );
                            }
                        });
                handler.sendEmptyMessageDelayed(1,0);
                break;
            case R.id.textView3:
                if (idCard==null||guideCard==null||etPhone.getText().toString().length()==0||etPwd.getText().toString().length()==0
                        ||etPwdTwice.getText().toString().length()==0){
                    ToastUtils.toast("信息不完整，请补全信息");
                }else {
                    if (!etPwd.getText().toString().equals(etPwdTwice.getText().toString())){
                        ToastUtils.toast("两次密码输入的不相同，请重新输入");
                    }
                    else {
                        Log.e(TAG, "onViewClicked:校验码"+codeUtils.getCode() );
                        if (etJym.getText().toString().equalsIgnoreCase(codeUtils.getCode())){
                            Log.e(TAG, "onViewClicked: "+"校验码陈宫" );
                            if (smsCode.equals(etYzm.getText().toString())){
                                regisGuide();
                            }else {
                                ToastUtils.toast("短信验证码错误");
                            }
                        }
                        else {
                            ToastUtils.toast("校验码错误");
                        }
                    }

                }
                break;
            case R.id.imgYz:
                codeUtils.createCode();
                Bitmap bitmap = codeUtils.createBitmap();
                imgYz.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    public void takePhoto() {
        iconDialog.dismiss();
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {//判断是否有相机应用
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();//创建临时图片文件
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    //FileProvider 是一个特殊的 ContentProvider 的子类，
                    //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            "com.spellway.provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
        } catch (Exception e) {
            Log.e("info", e.toString());
        }
    }

    @Override
    public void photos() {
        iconDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    /*创建临时存放*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //创建临时文件,文件前缀不能少于三个字符,后缀如果为空默认未".tmp"
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",         /* 后缀 */
                storageDir      /* 文件夹 */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) return;
                Log.e(TAG, "onActivityResult: "+mCurrentPhotoPath );
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                if (flag == true) {
                    idCard=new File(mCurrentPhotoPath);
                    idCards.clear();
                    idCards.add(idCard);
                    btIdCard.setImageBitmap(bitmap);
                } else if (flag == false) {
                    guideCard=new File(mCurrentPhotoPath);
                    guideCards.clear();
                    guideCards.add(guideCard);
                    btGuideCard.setImageBitmap(bitmap);
                }
                break;
            case 2:
                if (resultCode != Activity.RESULT_OK) return;
                Uri uri = data.getData();
                String path=null;
                try {
                    path = MyUtils.getPath(getContext(), uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    if (path!=null){
                        idCard=new File(path);
                    }
                    Glide.with(getActivity()).load(uri).into(btIdCard);
                } else {
                    if (path!=null){
                        guideCard=new File(path);
                    }
                    Glide.with(getActivity()).load(uri).into(btGuideCard);
                }
                break;
        }
    }
}
