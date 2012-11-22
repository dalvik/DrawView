package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	
	private Bitmap bg = null;//Bitmap.createBitmap(1000, 800, Config.RGB_565);
	
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
		bg = BitmapFactory.decodeFile("/mnt/sdcard/IPED/Image/20120929150149.jpg");
		paint = new Paint();
		paint.setColor(color.white);
		viewPathPaint.setColor(Color.BLUE);
		viewPathPaint.setStrokeWidth(2);
		viewPathPaint.setAntiAlias(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//canvas.drawBitmap(bg, 0,0, null);
		int length = pointList.size();
		for(int i=0; i<length; i++) {
			MyView myView = pointList.get(i);
			canvas.drawRect(myView.rect, myView.paint);
		}
		
		int pathLength = pathList.size();
		for(int i=0; i<pathLength; i++) {
			ViewPath  viewPath = pathList.get(i);
			canvas.drawLine(viewPath.startX, viewPath.startY, viewPath.stopX, viewPath.stopY, viewPath.paint);
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
			ViewPath viewPath = new ViewPath(myViewTemp.rect.centerX(), myViewTemp.rect.centerY(), myView.rect.centerX(), myView.rect.centerY(), viewPathPaint);
			pathList.add(viewPath);
		}
		pointList.add(myView);
		invalidate();
	}
}