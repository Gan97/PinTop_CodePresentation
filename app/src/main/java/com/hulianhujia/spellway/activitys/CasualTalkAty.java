package com.hulianhujia.spellway.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.PubCommunityRcyAdp;
import com.hulianhujia.spellway.adpaters.PubDiaryAdp;
import com.hulianhujia.spellway.customViews.IconDialog;
import com.hulianhujia.spellway.event.DeletEvent;
import com.hulianhujia.spellway.event.PickPhotoEvent;
import com.hulianhujia.spellway.javaBeans.AddCommunityBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class CasualTalkAty extends BaseActivity implements IconDialog.Photos, IconDialog.TakePhoto, View.OnClickListener {
    private static final int PICK_PHOTO = 3;
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.btPub)
    TextView btPub;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    /*    @Bind(R.id.photos)
        NineGridImageView photos;*/
    private IconDialog iconDialog;
    private NineGridImageViewAdapter<Uri> adapter;
    private int indexNum = 0;
    private int REQUEST_TAKE_PHOTO = 1;
    private int ALBUM_REQUEST_CODE = 2;
    private String mCurrentPhotoPath;
    private String TAG = "info";
    private File file;
    private LoginBean.UserInfoBean userInfo;
    private PubCommunityRcyAdp adp;
    private List<Uri> imgs = new ArrayList<>();
    private List<File> imgFiles= new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_casual_talk;
    }

    @Override
    public void initView() {
        getUser();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        initNine();
        btPub.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(getApplication());
    }

    private void initNine() {
        StaggeredGridLayoutManager m= new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(m);
        adp=new PubCommunityRcyAdp(this,imgs);
        recyclerview.setAdapter(adp);
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
//                photos.setImagesData(imgs);
                break;
            case 2:
                if (resultCode != Activity.RESULT_OK) return;
                Uri uri1 = data.getData();
                imgs.add(0, uri1);

                indexNum++;
//                photos.setImagesData(imgs);
                break;
            case PICK_PHOTO:
                if (resultCode==RESULT_OK){
                    List<Uri> uris = Matisse.obtainResult(data);
                    imgs.addAll(uris);
                    imgFiles.clear();
                    for (int i = 0; i < imgs.size(); i++) {
                        for (int j = 0; j < imgs.size(); j++) {
                            Log.e(TAG, "onActivityResult: "+imgs.get(i)+"====="+imgs.get(j) );
                            if (imgs.get(i).toString().endsWith(imgs.get(j).toString())&&i!=j){
                                imgs.remove(j);
                                Log.e(TAG, "onActivityResult: 去掉" );
                            }
                        }
                    }
                    Uri uri2 = imgs.get(0);
                    try {
                        /*获取图骗路径*/
                        String path = MyUtils.getPath(this, uri2);
                        file=new File(path);

                        for (Uri uri3:imgs){
                            String imgPath = MyUtils.getPath(this, uri3);
                            File file = new File(imgPath);
                            Log.e(TAG, "onActivityResult:图片路径 "+imgPath );
                            imgFiles.add(file);
                        }
                        /*显示*/
                        adp=new PubCommunityRcyAdp(this,imgs);
                        recyclerview.setAdapter(adp);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPub:
                OkGo.post(Urls.PUBLIC_URL + Urls.UPDATE_COMMINUTY)
                        .params("title", "1")
                        .params("username", userInfo.getUsername())
                        .params("content",etContent.getText().toString())
                        .params("where", Contents.LOCATION)
                        .addFileParams("imgFile[]",imgFiles)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 添加开始"+imgFiles.size());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 添加" + s);
                                Gson g = new Gson();
                                AddCommunityBean bean = g.fromJson(s, AddCommunityBean.class);
                                if (bean.getCode()==1){
                                    finish();
                                }
                                ToastUtils.toast(bean.getMsg());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: +t" + throwable.toString());
                            }
                        });
                break;
            case R.id.btExit:
                finish();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void pickPhoto(PickPhotoEvent pickPhotoEvent) {
        Matisse.from(this)

                .choose(MimeType.ofImage())
                .showSingleMediaType(true)
                .countable(true)
                .maxSelectable(9)
                .spanCount(3)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Dracula)
                .imageEngine(new GlideEngine())
                .forResult(PICK_PHOTO);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handDelet(DeletEvent event){
        imgs.remove(event.getPoision());
        imgFiles.remove(event.getPoision());
        adp=new PubCommunityRcyAdp(this,imgs);
        recyclerview.setAdapter(adp);
    }
}
