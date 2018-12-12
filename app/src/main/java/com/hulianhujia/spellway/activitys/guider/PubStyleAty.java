package com.hulianhujia.spellway.activitys.guider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.PubCommunityRcyAdp;
import com.hulianhujia.spellway.adpaters.PubDiaryAdp;
import com.hulianhujia.spellway.event.DeletEvent;
import com.hulianhujia.spellway.event.PickPhotoEvent;
import com.hulianhujia.spellway.javaBeans.AddCommunityBean;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

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
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class PubStyleAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.btPub)
    TextView btPub;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private int indexNum = 0;
    private LoginBean.UserInfoBean userInfo;
    private PubCommunityRcyAdp adp;
    private List<Uri> imgs = new ArrayList<>();
    private List<File> imgFiles = new ArrayList<>();
    private int REQUEST_TAKE_PHOTO = 1;
    private int ALBUM_REQUEST_CODE = 2;
    private static final int PICK_PHOTO = 3;
    private String mCurrentPhotoPath;
    private String TAG = "info";
    private File file;
    private String url;
    private String sId;
    @Override
    public int getContentId() {
        return R.layout.activity_pub_style_aty;
    }

    @Override
    public void initData() {


    }

    @Override
    public void initView() {
        initNine();
        getUser();
        EventBus.getDefault().register(this);
        url=getIntent().getStringExtra("url");
        sId=getIntent().getStringExtra("sId");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getApplication());
    }
    private void initNine() {
        StaggeredGridLayoutManager m = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(m);
        adp = new PubCommunityRcyAdp(this, imgs);
        recyclerview.setAdapter(adp);
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
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    imgs .addAll(uris) ;
                    for (int i = 0; i < imgs.size(); i++) {
                        for (int j = 0; j < imgs.size(); j++) {
                            if (imgs.get(i).toString().equals(imgs.get(j).toString())&&i!=j){
                                imgs.remove(j);
                            }
                        }
                    }
                    try {
                        /*获取图骗路径*/
                        for (Uri uri3 : imgs) {
                            String imgPath = MyUtils.getPath(this, uri3);
                            File file = new File(imgPath);
                            Log.e(TAG, "onActivityResult:图片路径 " + imgPath);
                            imgFiles.add(file);
                        }
                        /*显示*/
                        adp = new PubCommunityRcyAdp(this, imgs);
                        recyclerview.setAdapter(adp);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
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
    @OnClick({R.id.btExit, R.id.btPub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btPub:
                if (url==null||url.equals("")){
                    OkGo.post(Urls.PUBLIC_URL + Urls.ADD_STYLE)
//                        .params("title", "1")
                            .params("username", userInfo.getUsername())
                            .params("content",etContent.getText().toString())
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
                                    BaseBean bean = g.fromJson(s, BaseBean.class);
                                    ToastUtils.toast(bean.getMsg());
                                    if (bean.getCode()==1){
                                        finish();
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call: +t" + throwable.toString());
                                }
                            });
                }else {
                    OkGo.post(Urls.PUBLIC_URL + Urls.ADD_STYLE)
//                        .params("title", "1")
                            .params("username", userInfo.getUsername())
                            .params("content",etContent.getText().toString())
                            .params("news_id",url.split("&id=")[1])
                            .addFileParams("imgFile[]",imgFiles)
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 添加开始"+url.split("&id=")[1]);
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Log.e(TAG, "call: 添加" + s);
                                    Gson g = new Gson();
                                    BaseBean bean = g.fromJson(s, BaseBean.class);
                                    if (bean.getCode()==1){
                                        ToastUtils.toast("编辑成功，等待后台审核");
                                        finish();
                                    }else {
                                        ToastUtils.toast("");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, "call: +t" + throwable.toString());
                                }
                            });
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handDelet(DeletEvent event){
        imgs.remove(event.getPoision());
        imgFiles.remove(event.getPoision());
        adp=new PubCommunityRcyAdp(this,imgs);
        recyclerview.setAdapter(adp);
    }
}
