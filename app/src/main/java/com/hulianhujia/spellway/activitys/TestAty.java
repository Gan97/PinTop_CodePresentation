package com.hulianhujia.spellway.activitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.untils.MyUtils;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TestAty extends AppCompatActivity {

    @Bind(R.id.photos)
    NineGridImageView photos;
    private List<Uri> imgs=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_aty);
        ButterKnife.bind(this);
        NineGridImageViewAdapter<Uri> adapter = new NineGridImageViewAdapter<Uri>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, Uri uri) {
                Glide.with(context).load(uri).into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<Uri> list) {
                if (index==list.size()-1){
                    Toast.makeText(getApplicationContext(),"啊啊啊",Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        };
        Uri uri1 = MyUtils.ResourceIdToUri(this, R.mipmap.ic_launcher);
        imgs.add(uri1);
        photos.setAdapter(adapter);
        photos.setImagesData(imgs);

    }

}
