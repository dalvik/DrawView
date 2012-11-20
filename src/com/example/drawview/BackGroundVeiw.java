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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class BackGroundVeiw extends View implements OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

	private Context context;
	
	private int rectWidth = 50;
	
	private int rectHeight = 50;
	
	private Paint paint = null;
	
	private String TAG = "BackGroundVeiw";
	
	private List<MyView> pointList = new ArrayList<MyView>();
	
	private List<ViewPath> pathList = new ArrayList<ViewPath>();
	
	private boolean DEBUG = true;
	
	private Paint MyVewPaint = new Paint();
	
	private Paint viewPathPaint = new Paint();
	
	private Bitmap bg = Bitmap.createBitmap(1000, 800, Config.RGB_565);
	
	private GestureDetector gestureDetector = null;
	
	private ScaleGestureDetector scaleGestureDetector = null;
	
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
		paint = new Paint();
		paint.setColor(color.white);
		MyVewPaint.setColor(Color.RED);
		viewPathPaint.setColor(Color.BLUE);
		
		gestureDetector = new GestureDetector(this);
		scaleGestureDetector = new ScaleGestureDetector(context,this);
		setFocusable(true);
		setLongClickable(true);
		gestureDetector.setIsLongpressEnabled(true);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int counts = event.getPointerCount();
		if(scaleGestureDetector.onTouchEvent(event) && counts>=2) {
			return true;
		}else if(gestureDetector.onTouchEvent(event)) {
			return true;
		}
		if(DEBUG) {
			Log.d(TAG, "### touch x = " + event.getX() + " y = " + event.getY());
		}
		MyView myView = new MyView(MyVewPaint, new Rect((int)event.getX() -  rectWidth/2, (int)event.getY() - rectHeight/2 , (int)event.getX() +  rectWidth/2,  (int)event.getY() + rectHeight/2));
		int length = pointList.size();
		if(length>0) {
			MyView myViewTemp = pointList.get(length-1);
			ViewPath viewPath = new ViewPath(myViewTemp.rect.centerX(), myViewTemp.rect.centerY(), myView.rect.centerX(), myView.rect.centerY(), viewPathPaint);
			pathList.add(viewPath);
		}
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
		
		int pathLength = pathList.size();
		for(int i=0; i<pathLength; i++) {
			ViewPath  viewPath = pathList.get(i);
			canvas.drawLine(viewPath.startX, viewPath.startY, viewPath.stopX, viewPath.stopY, viewPath.paint);
		}
	}

	@Override
	public boolean onDown(MotionEvent event) {
		if(DEBUG) {
			Log.d(TAG, "### onDown x = " + event.getX() + " y = " + event.getY());
		}
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(DEBUG) {
			Log.d(TAG, "### onFling x = " + e1.getX() + " y = " + e1.getY());
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if(DEBUG) {
			Log.d(TAG, "### onScroll x = " + e1.getX() + " y = " + e1.getY());
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if(DEBUG) {
			Log.d(TAG, "### onSingleTapUp x = " + e.getX() + " y = " + e.getY());
		}
		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector arg0) {
		if(DEBUG) {
			Log.d(TAG, "### onScale");
		}
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector arg0) {
		if(DEBUG) {
			Log.d(TAG, "### onScaleBegin");
		}
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector arg0) {
		if(DEBUG) {
			Log.d(TAG, "### onScaleEnd");
		}
	}

	

	
}
