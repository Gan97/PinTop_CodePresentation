package com.hulianhujia.spellway.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hulianhujia.spellway.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/4/5.
 */

public class IconDialog extends Dialog {
    @Bind(R.id.btTakePhoto)
    TextView btTakePhoto;
    @Bind(R.id.btPhotos)
    TextView btPhotos;
    private TakePhoto takePhoto;
    private Photos photos;

    public IconDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icondialog);
        ButterKnife.bind(this);
        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto.takePhoto();
            }
        });
        btPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photos.photos();
            }
        });
    }

    public void setTakePhoto(TakePhoto takePhoto) {
        this.takePhoto = takePhoto;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public interface TakePhoto {
        void takePhoto();
    }

    public interface Photos {
        void photos();
    }

}
