package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<Rect> pathList = null;
	
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
		pathList = new  ArrayList<Rect>();
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
		    Rect r = new Rect(viewPath.startX,viewPath.startY, newViewPath.startX, newViewPath.startY);
		    pathList.add(r);
		}else {
		    viewPath = newViewPath;
		}
		return bitmap;
	}
	
	public boolean checkPath(int x, int y) {
		System.out.println(x + " ===== " + y);
		for(Rect r:pathList) {
			System.out.println("###" + r);  
			//if(x>r.left && x<r.right && y>r.top && y<r.bottom) {
			int x1 = r.left;
			int y1 = r.top;
			int x2 = r.right;
			int y2 = r.bottom;
			System.out.println("x1 = " + x1 + " x2 = " + x2  + " y1 = " + y1 + " y2 = " + y2);
			if(x>x1 && x<x2 && y>y1 && y<y2) {
				double d = (Math.abs((y2 - y1) * x +(x1 - x2) * y + ((x2 * y1) -(x1 * y2)))) / (Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x1 - x2, 2)));
				System.out.println("dd = " + d);
				if(d<=5f) {
					return true;
				}
			}
		}
		return false;
	}
}