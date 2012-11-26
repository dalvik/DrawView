package com.example.drawview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends Activity implements OnTouchListener {

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    DisplayMetrics dm;
    BackGroundVeiw imgView;
    Bitmap bitmap;

    float minScaleR;// 最小缩放比例
    float curScaleR = 0;
    static float MAX_SCALE = 4f;// 最大缩放比例

    static final int NONE = 0;// 初始状态
    static final int DRAG = 1;// 拖动
    static final int ZOOM = 2;// 缩放
    int mode = NONE;

    PointF prev = new PointF();
    PointF mid = new PointF();
    float dist = 1f;

    private static final int bw = 50;
    
    private static final int bh = 50;
    
    private Paint MyVewPaint = new Paint();
    
    private String TAG = "Touch";
    
    private float realImageWidth = 0;
    
    private float realImagHeight = 0;
    
    private float initImageWidth = 0;
    
    private float initImageHeight = 0;
    
    private boolean clickFlag = false;
    
    private Bitmap lableBitmap;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw_view);
        lableBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		MyVewPaint.setColor(Color.RED);
		MyVewPaint.setAntiAlias(true);
		MyVewPaint.setStrokeWidth(2);
        imgView = (BackGroundVeiw) findViewById(R.id.imag);
        bitmap = imgView.getBitmap();
        initImageWidth = bitmap.getWidth();
        initImageHeight = bitmap.getHeight();
        imgView.setImageBitmap(bitmap);
        
        imgView.setOnTouchListener(this);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        minZoom();
        center();
        imgView.setImageMatrix(matrix);
    }

    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            savedMatrix.set(matrix);
            prev.set(event.getX(), event.getY());
            mode = DRAG;
            clickFlag = true;
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            dist = spacing(event);
            clickFlag = false;
            if (spacing(event) > 10f) {
                savedMatrix.set(matrix);
                midPoint(mid, event);
                mode = ZOOM;
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        	if(clickFlag) {
        		clickFlag = false;
        		Matrix m = new Matrix();
                m.set(matrix);
                RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
                m.mapRect(rectF);
        		int left = (int) (initImageWidth/realImageWidth * (event.getX() - rectF.left)) - bw/2;
        		int top = (int) (initImageHeight/realImagHeight * (event.getY() - rectF.top)) - bh/2;
        		int right = left + bw/2;
        		int bottom = top + bh/2;
        		Rect rect = new Rect(left, top, right, bottom);
        		MyViewPath viewPath = new MyViewPath(left+bw/4, top + bh/4, 0, 0, MyVewPaint);
        		MyViewPoint myView = new MyViewPoint(MyVewPaint, rect, lableBitmap);
                bitmap = imgView.createBitmap(bitmap, savedMatrix, (int)realImageWidth, (int)realImagHeight, myView, viewPath);
                imgView.setImageBitmap(bitmap);
            }
            mode = NONE;
            break;
        case MotionEvent.ACTION_MOVE:
            if (mode == DRAG) {
                matrix.set(savedMatrix);
                float dx = (event.getX() - prev.x);
                if(Math.abs(dx)>5f){
                	clickFlag = false;
                	float dy = event.getY() - prev.y;
                	matrix.postTranslate(dx, dy);
                }
            } else if (mode == ZOOM) {
                float newDist = spacing(event);
                if (newDist > 10f) {
                    matrix.set(savedMatrix);
                    float tScale = newDist / dist;
                    matrix.postScale(tScale, tScale, mid.x, mid.y);
                }
            }
            break;
        }
        imgView.setImageMatrix(matrix);
        CheckView();
        return true;
    }

    protected void center(boolean horizontal, boolean vertical) {

        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);
        float height = rect.height();
        float width = rect.width();
        float deltaX = 0, deltaY = 0;

        if (vertical) {
            int screenHeight = dm.heightPixels;
            if (height <= screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = screenHeight - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width <= screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        Log.d(TAG, "deltaX= " + deltaX + " deltaY=" + deltaY);
        matrix.postTranslate(deltaX, deltaY);
    }

    private void CheckView() {
        float p[] = new float[9];
        matrix.getValues(p);
        curScaleR = p[0];
        realImageWidth = curScaleR * bitmap.getWidth();
        realImagHeight = curScaleR * bitmap.getHeight();
        if (mode == ZOOM) {
            if (curScaleR < minScaleR) {
                matrix.setScale(minScaleR, minScaleR);
            }
            if (curScaleR > MAX_SCALE) {
                matrix.set(savedMatrix);
            }
        }
        center();
    }
    
    private void minZoom() {
        minScaleR = Math.max((float) dm.widthPixels / (float) bitmap.getWidth(), (float) dm.heightPixels / (float) bitmap.getHeight());
        curScaleR = minScaleR;
        MAX_SCALE = Math.max((float) bitmap.getWidth() / (float) dm.widthPixels, (float) bitmap.getHeight() / (float) dm.heightPixels);;
        if (minScaleR < 1.0) {
            matrix.postScale(minScaleR, minScaleR);
            realImageWidth = minScaleR * bitmap.getWidth();
            realImagHeight = minScaleR * bitmap.getHeight();
        }
    }

    private void center() {
        center(true, true);
    }

    
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}