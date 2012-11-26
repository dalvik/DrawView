package com.example.drawview;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BackGroundVeiw extends ImageView {

	private Context context;
	
	private Paint paint = null;
	
	private boolean DEBUG = true;
	
	private Bitmap bg = null;
	
	private MyViewPath  viewPath = null;
	
	public BackGroundVeiw(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}


	private void init() {
		bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg).copy(Config.RGB_565, true);
		paint = new Paint();
		paint.setColor(color.white);
	}
	
	
	public Bitmap getBitmap() {
		return bg;
	}
	
	
	public Bitmap  createBitmap(Bitmap bitmap, Matrix matrix, int w, int h,MyViewPoint myView, MyViewPath newViewPath ) {
		Canvas canvas = new Canvas(bitmap);
		canvas.drawBitmap(bitmap, matrix, paint);
		Rect rect = myView.rect;
		Bitmap tmp = myView.bitmap;
        if(null == tmp){
            canvas.drawRect(rect, myView.paint);
        } else {
            canvas.drawBitmap(tmp, null, rect, myView.paint);
        }
		if(viewPath != null) {
		    canvas.drawLine( viewPath.startX, viewPath.startY, newViewPath.startX,newViewPath.startY, viewPath.paint);
		    viewPath = newViewPath;
		}else {
		    viewPath = newViewPath;
		}
		return bitmap;
	}
}