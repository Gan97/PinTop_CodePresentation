package com.hulianhujia.spellway.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.customViews.IconDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisGuiderAty extends BaseActivity implements IconDialog.TakePhoto, IconDialog.Photos {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etPwdTwice)
    EditText etPwdTwice;
    @Bind(R.id.btIdCard)
    ImageView btIdCard;
    @Bind(R.id.btGuideCard)
    ImageView btGuideCard;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btSend)
    TextView btSend;
    @Bind(R.id.etJym)
    EditText etJym;
    private IconDialog iconDialog;
    private int REQUEST_TAKE_PHOTO = 1;
    private int ALBUM_REQUEST_CODE = 2;
    private String mCurrentPhotoPath;
    private boolean flag=true;
    @Override
    public int getContentId() {
        return R.layout.activity_regis_guider_aty;
    }

    @Override
    public void initView() {
        iconDialog=new IconDialog(this);
        iconDialog.setPhotos(this);
        iconDialog.setTakePhoto(this);
    }

    @OnClick({R.id.btExit, R.id.btIdCard, R.id.btGuideCard, R.id.btSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btIdCard:
                flag=true;
                iconDialog.show();
                break;
            case R.id.btGuideCard:
                flag=false;
                iconDialog.show();
                break;
            case R.id.btSend:
                break;
        }
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
        }catch (Exception e){
            Log.e("info",e.toString());
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
        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) return;
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                if (flag==true){
                    btIdCard.setImageBitmap(bitmap);
                }else if (flag==false){
                    btGuideCard.setImageBitmap(bitmap);
                }
                break;
            case 2:
                if (resultCode != Activity.RESULT_OK) return;
                Uri uri = data.getData();
                if (flag){
                    Glide.with(getApplicationContext()).load(uri).into(btIdCard);
                }else {
                    Glide.with(getApplicationContext()).load(uri).into(btGuideCard);
                }
                break;
        }
    }

    public void photos(View view) {

    }
}
