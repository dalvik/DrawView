package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
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
	
	private boolean isScaling = false;
	
	private Paint MyVewPaint = new Paint();
	
	private Paint viewPathPaint = new Paint();
	
	private int oper = 0;
	
	private boolean addFlag = true;
	
	private Bitmap bg = null;//Bitmap.createBitmap(1000, 800, Config.RGB_565);
	
	private GestureDetector gestureDetector = null;
	
	private ScaleGestureDetector scaleGestureDetector = null;
	
	private int left = 0;
	
	private int top = 0;
	
	private int right = 0;
	
	private int bottom = 0;

	private int screenWidth = 0;
	
	private int screenHeight = 0;
	
	private int displayWidth = 0;
	
	private int displayHeight = 0;
	
	int lastx = 0;
	
	int lasty = 0;
	
	private int lastLeft;

	private int lastTop;

	private int lastRight;

	private int lastBottom;
	
	private int isloading = 0;
	
	private static int MAX = 0;
	
    private int dx1 = 45;
    
    private int dy1 = 60;
	
	private Rect rect = null;
	
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
		System.out.println(bg.getWidth());
		paint = new Paint();
		paint.setColor(color.white);
		MyVewPaint.setColor(Color.RED);
		viewPathPaint.setColor(Color.BLUE);
		viewPathPaint.setStrokeWidth(2);
		viewPathPaint.setAntiAlias(true);
		gestureDetector = new GestureDetector(this);
		scaleGestureDetector = new ScaleGestureDetector(context,this);
		setFocusable(true);
		setLongClickable(true);
		gestureDetector.setIsLongpressEnabled(true);
		
		right = bg.getWidth();
		bottom = bg.getHeight();
		MAX = right + 10 * dy1;
		rect = new Rect(left, top, right, bottom);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int counts = event.getPointerCount();
		if(scaleGestureDetector.onTouchEvent(event) && counts>=2) {
			return true;
		}else if(gestureDetector.onTouchEvent(event)) {
			return true;
		}else {
			/*if(oper == Constants.TOUCH) {
				if(addFlag) {
					addFlag = false;
					MyView myView = new MyView(MyVewPaint, new Rect((int)event.getX() -  rectWidth/2, (int)event.getY() - rectHeight/2 , (int)event.getX() +  rectWidth/2,  (int)event.getY() + rectHeight/2));
					int length = pointList.size();
					if(length>0) {
						MyView myViewTemp = pointList.get(length-1);
						ViewPath viewPath = new ViewPath(myViewTemp.rect.centerX(), myViewTemp.rect.centerY(), myView.rect.centerX(), myView.rect.centerY(), viewPathPaint);
						pathList.add(viewPath);
					}
					pointList.add(myView);
					invalidate();
					addFlag = true;
				}
			}*/
		}
		if(DEBUG) {
			Log.d(TAG, "### touch x = " + event.getX() + " y = " + event.getY() + " oper = "+ oper);
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bg, null, new Rect(left , top, right, bottom), null);
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
		//oper = Constants.TOUCH;
		lastx = (int)event.getRawX();
		lasty = (int) event.getRawY();
		if(DEBUG) {
			Log.d(TAG, "### onDown x = " + event.getX() + " y = " + event.getY());
		}
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		oper = Constants.FILING;
		if(DEBUG) {
			Log.d(TAG, "### onFling x = " + e1.getX() + " y = " + e1.getY());
		}
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		oper = Constants.LONGPRESS;
		if(DEBUG) {
			Log.d(TAG, "### onLongPress x = " + e.getX() + " y = " + e.getY());
		}
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		oper = Constants.SCROLL;
		if(DEBUG) {
			Log.d(TAG, "### onScroll x = " + e1.getX() + " y = " + e1.getY());
		}
		if(displayWidth > screenWidth) {
			//Log.i(TAG, "scroll =" + tempBitmap.getWidth() + "  " + tempBitmap.getHeight() + " left=" + left + " top=" + top + " reight=" + right + " bottom=" + bottom);
			int dx;
			dx = (int) e2.getRawX() - lastx;
			left = left + dx;
			right = right + dx;
			
			if(left>=0) {
				left = left - dx;
				right = right - dx;
			}

			if(right<=screenWidth) {
				right = right -dx;
				left = left - dx;
			}
		}
		if(displayHeight > screenHeight) {
			int dy;
			dy = (int) e2.getRawY() - lasty;
			top = top + dy;
			bottom = bottom + dy;
			if(top>=0) {
				top = top -dy;
				bottom = bottom - dy;
			}
 
			if(bottom<=screenHeight) {
				bottom = bottom - dy;
				top = top - dy;
			}
		}
		this.invalidate();
		lastx = (int) e2.getRawX();
		lasty = (int) e2.getRawY(); 
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		if(DEBUG) {
			Log.d(TAG, "### onSingleTapUp x = " + e.getX() + " y = " + e.getY());
		}
		oper = Constants.TOUCH;
		return true;
	}

	private float lastFactor;
    
	private float currentFactor;
    
    
	@Override
	public boolean onScale(ScaleGestureDetector decDetector) {
		if(DEBUG) {
			Log.d(TAG, "### onScale");
		}
		currentFactor = decDetector.getScaleFactor();
		float delta1 = currentFactor - lastFactor;
        lastFactor = currentFactor;
        Log.d(TAG, "delta1=" + delta1);
       	 if (delta1 >= 0.02 && !onProcessing()) {
       		 left = left - dx1;
       		 top = top - dy1;
       		 right = right + dx1;
       		 bottom = bottom + dy1;
       		 int max = right - left;
       		 Log.d(TAG, "max=" + max);
   		     if(max<=MAX && max>0) {
   		 		this.invalidate();
   		 		return false;
   		 	 } else {
   		 		 left = left + dx1;
          		 top = top + dy1;
          		 right = right - dx1;
          		 bottom = bottom - dy1;
          		 this.invalidate();
   		 		 return false;
   		 	 }
	      } else if(delta1<=-0.021 && !onProcessing()) {
	    	  if(top<0 && bottom<screenHeight) {
		    	  top = top + dy1;
		    	  bottom = bottom + dy1;
	    	  } else if(top>0 && bottom>screenHeight) {
	    		  top = top - dy1;
		    	  bottom = bottom - dy1;
	    	  }
	    	  if(left>0 && right>screenWidth) {
		    	  right -= (2*dx1);
		    	  top += dy1;
		    	  bottom -= dy1;
	    	  } else  if(left < 0 && right < screenWidth) {
	    		  left += (2*dx1);
		    	  top += dy1;
		    	  bottom -= dy1;
	    	  }else {
	    		  left += dx1;
		    	  right -= dx1;
		    	  top += dy1;
		    	  bottom -= dy1;
	    	  }
	    	  int r_l = right - left;
	    	  if(r_l <= 280) {
	    		  left -= dx1;
    	    	  top -= dy1;
    	    	  right += dx1;
    	    	  bottom += dy1;
    	     }
	     }
       	this.invalidate();
		return false;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		isScaling = true;
		oper = Constants.SCALING;
		lastFactor = detector.getScaleFactor();
    	currentFactor = detector.getScaleFactor();
    	dx1 = 45;
    	dy1 = 60;
    	lastLeft = left;
 		lastTop = top;
 		lastRight = right;
 		lastBottom = bottom;
		if(DEBUG) {
			Log.d(TAG, "### onScaleBegin");
		}
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		isScaling = false;
		oper = Constants.TOUCH;
		isloading = 0;
		int hopeWidth = right-left;
		int homeHeight = bottom-top;
		Bitmap temp = BitmapUtil.adaptive(screenWidth, screenHeight, bg);
		if(temp != null) {
			bg.recycle();
			bg = null;
			System.gc();
			bg = temp;
		}
		if(DEBUG) {
			Log.d(TAG, "### onScaleEnd");
		}
	}

	
	public void initXY(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		isloading = 0;
		if(displayWidth <= screenWidth) {
			left = (screenWidth - displayWidth)/2;
			right = displayWidth + left;
		}
		if(DEBUG){
			Log.d(TAG, "### initXY left=" + left + "  right=" + right);
		}
	}
	
	private boolean onProcessing() {
		return isloading == 1;
	}
}
