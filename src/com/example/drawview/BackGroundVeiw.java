package com.example.drawview;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class BackGroundVeiw extends View implements OnClickListener {

	private Paint paint = null;
	
	private Bitmap bg = Bitmap.createBitmap(1000, 800, Config.RGB_565);
	
	public BackGroundVeiw(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(color.white);
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(color.white);
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		paint.setColor(color.white);
	}

	@Override
	public void onClick(View v) {
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bg, 0, 0, paint);
	}
	

	
}
