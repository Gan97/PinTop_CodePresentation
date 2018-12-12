package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.GuidePhotosAdp;
import com.hulianhujia.spellway.customViews.ConfirmDialog;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.AddPhotosBean;
import com.hulianhujia.spellway.javaBeans.PhotosBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuidePhotosAty extends BaseActivity implements YesListener,PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.grideView)
    PullToRefreshGridView grideView;
    @Bind(R.id.btAdd)
    TextView btAdd;
    private List<Uri> imgUris = new ArrayList<>();
    private List<File> imgs=new ArrayList<>();
    private final static int PICK_PHOTO=1;
    private UserInfoBean userInfo;
    private LoadingDialog dialog;
    private String TAG="info";
    private ConfirmDialogN confirmDialog;
    private int page=1;
    private GuidePhotosAdp adp;
    private List<PhotosBean.ReturnArrBean> datas=new ArrayList<>();
    private PhotosBean pb;
    private String guideName;
    @Override
    public int getContentId() {
        return R.layout.activity_guide_photos_aty;
    }

    @Override
    public void initView() {
        guideName=getIntent().getStringExtra("guideName");
        dialog=new LoadingDialog(this);
        adp=new GuidePhotosAdp(this,datas);
        grideView.setAdapter(adp);
        grideView.setMode(PullToRefreshBase.Mode.BOTH);
        grideView.setOnRefreshListener(this);
        grideView.setOnItemClickListener(this);
        confirmDialog=new ConfirmDialogN(this);
        confirmDialog.setYesListener(this);
        confirmDialog.setFlag(1);
        confirmDialog.setTitle("");
        userInfo= (UserInfoBean) SharedUtils.readUserInfo(this);
        if (!guideName.equals(userInfo.getReturnArr().getUsername())){
            btAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_GET_PHOTO)
                .params("guidename",guideName)
                .params("p",page)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 获取图片开始"+ userInfo.getReturnArr().getUsername());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 获取相册获得" + s);
                        Gson g = new Gson();
                        PhotosBean photosBean = g.fromJson(s, PhotosBean.class);
                        pb=photosBean;
                        if (photosBean.getCode()==1){
                            datas.clear();
                            datas.addAll(photosBean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call:获取相册错误 "+throwable.toString() );
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_PHOTO:
                if (resultCode!=RESULT_OK)return;
                List<Uri> uris = Matisse.obtainResult(data);
                for (Uri uri:uris){
                    try {
                        String path = MyUtils.getPath(getApplicationContext(), uri);
                        if (path!=null&&path.length()!=0){
                            File file  = new File(path);
                            imgs.add(file);
                        }
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                confirmDialog.show();

        }
    }

    @OnClick({R.id.btExit, R.id.btAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btAdd:
                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .spanCount(3)
                        .maxSelectable(10)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new GlideEngine())
                        .forResult(PICK_PHOTO);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_GET_PHOTO)
                .params("guidename",guideName)
                .params("p",page)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 获取图片开始" );

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        grideView.onRefreshComplete();
                        Log.e(TAG, "call: 获取相册获得" + s);
                        Gson g = new Gson();
                        PhotosBean photosBean = g.fromJson(s, PhotosBean.class);
                        if (photosBean.getCode()==1){
                            datas.clear();
                            datas.addAll(photosBean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        grideView.onRefreshComplete();
                        Log.e(TAG, "call:获取相册错误 "+throwable.toString() );
                    }
                });
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_GET_PHOTO)
                .params("guidename",guideName)
                .params("p",page)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 获取图片开始" );

                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        grideView.onRefreshComplete();
                        Log.e(TAG, "call: 获取相册获得" + s);
                        Gson g = new Gson();
                        PhotosBean photosBean = g.fromJson(s, PhotosBean.class);
                        ToastUtils.toast(photosBean.getMsg());
                        if (photosBean.getCode()==1){
                            datas.addAll(photosBean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        grideView.onRefreshComplete();
                        Log.e(TAG, "call:获取相册错误 "+throwable.toString() );
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent= new Intent(GuidePhotosAty.this,PhotoDetailAty.class);
        intent.putExtra("datas", (Serializable) datas);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    @Override
    public void yes(int flag) {
        switch (flag){
            case 1:
                OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_ADD_PHOTO)
                        .params("guidename",userInfo.getReturnArr().getUsername())
                        .addFileParams("imgFile[]",imgs)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                dialog.show();
                                Log.e(TAG, "call: 添加图片开始"+imgs.size());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                dialog.dismiss();
                                Log.e(TAG, "call: 添加图片获得" + s);
                                Gson g = new Gson();
                                AddPhotosBean bea = g.fromJson(s, AddPhotosBean.class);
                                ToastUtils.toast(bea.getMsg());
                                if (bea.getCode()==1){
                                    page=1;
                                    OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_GET_PHOTO)
                                            .params("guidename",userInfo.getReturnArr().getUsername())
                                            .params("p",page)
                                            .getCall(StringConvert.create(),RxAdapter.<String>create())
                                            .doOnSubscribe(new Action0() {
                                                @Override
                                                public void call() {
                                                    dialog.show();
                                                    Log.e(TAG, "call: 获取图片开始"+ userInfo.getReturnArr().getUsername());
                                                }
                                            }).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<String>() {
                                                @Override
                                                public void call(String s) {
                                                    dialog.dismiss();
                                                    Log.e(TAG, "call: 获取相册获得" + s);
                                                    Gson g = new Gson();
                                                    PhotosBean photosBean = g.fromJson(s, PhotosBean.class);
                                                    pb=photosBean;
                                                    if (photosBean.getCode()==1){
                                                        datas.clear();
                                                        datas.addAll(photosBean.getReturnArr());
                                                        adp.notifyDataSetChanged();
                                                    }
                                                }
                                            }, new Action1<Throwable>() {
                                                @Override
                                                public void call(Throwable throwable) {
                                                    dialog.dismiss();
                                                    Log.e(TAG, "call:获取相册错误 "+throwable.toString() );
                                                }
                                            });
                                }
                                confirmDialog.dismiss();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                confirmDialog.dismiss();
                                Log.e(TAG, "call: 添加图片错误"+throwable.toString() );
                            }
                        });
                break;
        }
    }
}
