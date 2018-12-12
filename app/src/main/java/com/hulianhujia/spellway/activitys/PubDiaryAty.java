package com.hulianhujia.spellway.activitys;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.PubDiaryAdp;
import com.hulianhujia.spellway.customViews.IconDialog;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.DeletEvent;
import com.hulianhujia.spellway.event.PubDiaryPhotoEvent;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.PubDiaryBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class PubDiaryAty extends BaseActivity implements IconDialog.Photos, IconDialog.TakePhoto, View.OnClickListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.btCommit)
    TextView btSave;
    @Bind(R.id.etTitle)
    EditText etTitle;
    @Bind(R.id.etTitle2)
    EditText etTitle2;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.photos)
    RecyclerView photos;
    private IconDialog iconDialog;
    private List<Uri> imgs = new ArrayList<>();
    private List<String> imgPaths = new ArrayList<>();
    private String mCurrentPhotoPath;
    private int REQUEST_TAKE_PHOTO = 1;
    private int ALBUM_REQUEST_CODE = 2;
    private String lat;
    private String lon;
    private int indexNum = 0;
    private String loc;
    private String TAG = "infoo";
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //监听配置
    public AMapLocationClientOption mLocationOption = null;
    private LoginBean.UserInfoBean userInfo;
    private static final int PICK_PHOTO = 3;
    private List<File> imgFiles = new ArrayList<>();
    private PubDiaryAdp adp;
    private LoadingDialog loadingDialog;
    @Override
    public int getContentId() {
        return R.layout.activity_pub_diary_aty;
    }
    @Override
    public void initView() {
        loadingDialog = new LoadingDialog(this);
        getUser();
        EventBus.getDefault().register(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 0);
        } else {

        }
        initNineView();
        initLocation();
        btSave.setOnClickListener(this);
    }
    private void getUser() {
        LoginBean.UserInfoBean userInfoBean = (LoginBean.UserInfoBean) SharedUtils.readObject(getApplicationContext());
        userInfo = userInfoBean;
    }
    private void initLocation() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                String city = aMapLocation.getCity();
                Log.e(TAG, "onLocationChanged: " + city);
                loc = city;
                lat=aMapLocation.getLatitude()+"";
                lon=aMapLocation.getLongitude()+"";

            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);

        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    private void initNineView() {
        StaggeredGridLayoutManager m = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        photos.setLayoutManager(m);
        adp = new PubDiaryAdp(this, imgs);
        photos.setAdapter(adp);
    }

    @Override
    public void photos() {
        iconDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    @Override
    public void takePhoto() {
        iconDialog.dismiss();
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
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
                    Uri photoURI = FileProvider.getUriForFile(this,
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
    /*创建临时存放*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //.getExternalFilesDir()方法可以获取到 SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) return;
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                imgs.add(0, uri);
                indexNum++;
                break;
            case 2:
                if (resultCode != Activity.RESULT_OK) return;
                Uri uri1 = data.getData();
                imgs.add(0, uri1);
                indexNum++;
                break;
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    imgs.addAll(uris);
                    List<String> paths = Matisse.obtainPathResult(data);
                    for (String path : paths) {
                        File file = new File(path);
                        imgFiles.add(file);
                    }
                    for (int i = 0; i < imgs.size(); i++) {
                        for (int j = 0; j < imgs.size(); j++) {
                            if (imgs.get(i).toString().equals(imgs.get(j).toString())&&i!=j){
                                imgs.remove(j);
                                imgFiles.remove(j);
                            }
                        }
                    }
                    adp = new PubDiaryAdp(PubDiaryAty.this, imgs);
                    photos.setAdapter(adp);
                }
                break;
        }
    }

    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCommit:
                OkGo.post(Urls.PUBLIC_URL + Urls.ADD_DIARY)
                        .params("title", etTitle.getText().toString())
                        .params("username", userInfo.getUsername())
                        .params("content", etContent.getText().toString())
                        .params("where", Contents.LOCATION)
                        .params("lat",SharedUtils.readLat(this)==null?"0":SharedUtils.readLat(this))
                        .params("lng",SharedUtils.readLon(this)==null?"0":SharedUtils.readLon(this))
                        .params("tag",etTitle2.getText().toString() )
                        .addFileParams("imgFile[]", imgFiles)
                        .getCall(new StringConvert(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call:开始 "+Contents.LOCATION );
                                loadingDialog.show();
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: add日记" + s);
                                Gson g = new Gson();
                                PubDiaryBean bea = g.fromJson(s, PubDiaryBean.class);
                                ToastUtils.toast(bea.getMsg());
                                if (bea.getCode() == 1) {
                                    finish();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 日记失败" + throwable.toString());
                            }
                        });
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handPick(PubDiaryPhotoEvent event) {
        Matisse.from(this)
                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .capture(true)
                .countable(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.spellway.provider"))
                .maxSelectable(9)
                .spanCount(3)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(new GlideEngine())
                .forResult(PICK_PHOTO);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handDelet(DeletEvent event){
        imgs.remove(event.getPoision());
        imgFiles.remove(event.getPoision());
        adp=new PubDiaryAdp(this,imgs);
        photos.setAdapter(adp);
    }
}
