package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class BackGroundVeiw extends View implements OnClickListener {

	private int rectWidth = 50;
	
	private int rectHeight = 50;
	
	private Paint paint = null;
	
	private String TAG = "BackGroundVeiw";
	
	private List<MyView> pointList = new ArrayList<MyView>();
	
	private boolean DEBUG = true;
	
	private Paint MyVewPaint = new Paint();
	
	private Bitmap bg = Bitmap.createBitmap(1000, 800, Config.RGB_565);
	
	public BackGroundVeiw(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(color.white);
		MyVewPaint.setColor(Color.RED);
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(color.white);
		MyVewPaint.setColor(Color.RED);
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setColor(color.white);
		MyVewPaint.setColor(Color.RED);
	}

	@Override
	public void onClick(View v) {
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(DEBUG) {
			Log.d(TAG, "### touch x = " + event.getX() + " y = " + event.getY());
		}
		MyView myView = new MyView(MyVewPaint, new Rect((int)event.getX() -  rectWidth/2, (int)event.getY() - rectHeight/2 , (int)event.getX() +  rectWidth/2,  (int)event.getY() + rectHeight/2));
		pointList.add(myView);
		invalidate();
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bg, 0, 0, paint);
		int length = pointList.size();
		for(int i=0; i<length; i++) {
			MyView myView = pointList.get(i);
			canvas.drawRect(myView.rect, myView.paint);
		}
	}
	

	
}
