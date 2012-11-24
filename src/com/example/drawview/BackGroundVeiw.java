package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BackGroundVeiw extends ImageView {

	private Context context;
	
	private Paint paint = null;
	
	private String TAG = "BackGroundVeiw";
	
	private List<MyView> pointList = new ArrayList<MyView>();
	
	private List<ViewPath> pathList = new ArrayList<ViewPath>();
	
	private boolean DEBUG = true;
	
	private Paint viewPathPaint = new Paint();
	
	private Bitmap bg = null;//
	
	private int dx = 0;;
	
	private int dy = 0;
	
	public BackGroundVeiw(Context context) {
		super(context);
		this.context = context;
		initGesture();
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initGesture();
	}
	
	public BackGroundVeiw(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initGesture();
	}


	private void initGesture() {
		//bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
		//bg = BitmapFactory.decodeFile("/mnt/sdcard/IPED/Image/20120929150149.jpg");
		bg = BitmapFactory.decodeResource(getResources(), R.drawable._birds_24);
		System.out.println(bg.getWidth());
		paint = new Paint();
		paint.setColor(color.white);
		viewPathPaint.setColor(Color.BLUE);
		viewPathPaint.setStrokeWidth(2);
		viewPathPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int length = pointList.size();
		for(int i=0; i<length; i++) {
			MyView myView = pointList.get(i);
			int left = dx + myView.left;
			int top = dy + myView.top;
			int right = left + myView.width;
			int bottom = top + myView.height;
			Rect rectTmp = new Rect(left, top, right, bottom);
			canvas.drawRect(rectTmp, myView.paint);
		}
		
		int pathLength = pathList.size();
		for(int i=0; i<pathLength; i++) {
			ViewPath  viewPath = pathList.get(i);
			canvas.drawLine(dx + viewPath.startX, dy + viewPath.startY, dx + viewPath.stopX, dy + viewPath.stopY, viewPath.paint);
		}
	}
	
	public Bitmap getBitmap() {
		return bg;
	}
	
	public void addPoint(MyView myView) {
		//MyView myView = new MyView(MyVewPaint, new Rect((int)event.getX() -  rectWidth/2, (int)event.getY() - rectHeight/2 , (int)event.getX() +  rectWidth/2,  (int)event.getY() + rectHeight/2));
		int length = pointList.size();
		if(length>0) {
			MyView myViewTemp = pointList.get(length-1);
			int startX = myViewTemp.left + myView.width/2;
			int startY = myViewTemp.top + myViewTemp.height/2;
			int endX = myView.left + myView.width/2;
			int endY = myView.top + myViewTemp.height/2;
			
			ViewPath viewPath = new ViewPath(startX, startY, endX, endY, viewPathPaint);
			pathList.add(viewPath);
		}
		pointList.add(myView);
		invalidate();
	}
	
	public void setXY(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		invalidate();
	}
	
	public void scale(int dw, int dh) {
		int length = pointList.size();
		pathList.clear();
		for(int i=0;i<length;i++) {
			MyView myView = pointList.get(i);
			int deltaL = dw - myView.width;
			int deltalT = dh - myView.height;
			myView.width = dw;
			myView.height = dh;
			myView.left -=deltaL/2;
			myView.top -= deltalT/2;
			pointList.remove(i);
			pointList.add(i, myView);
			if(i>0) {
				MyView myViewTemp = pointList.get(i-1);
				int startX = myViewTemp.left + myViewTemp.width/2;
				int startY = myViewTemp.top + myViewTemp.height/2;
				int endX = myView.left + myView.width/2;
				int endY = myView.top + myView.height/2;
				ViewPath viewPath = new ViewPath(startX, startY, endX, endY, viewPathPaint);
				pathList.add(viewPath);
			}
		}
	}
	
	public int getX() {
		return dx;
	}
	
	public int getY() {
		return dy;
	}
}