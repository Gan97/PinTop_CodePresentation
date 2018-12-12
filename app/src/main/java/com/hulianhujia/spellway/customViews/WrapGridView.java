package com.hulianhujia.spellway.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class WrapGridView extends GridView {

	public WrapGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public WrapGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public WrapGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height= MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
	}
}
