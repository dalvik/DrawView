package com.example.drawview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
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

    float minScaleR;// ��С���ű���
    static final float MAX_SCALE = 4f;// ������ű���

    static final int NONE = 0;// ��ʼ״̬
    static final int DRAG = 1;// �϶�
    static final int ZOOM = 2;// ����
    int mode = NONE;

    PointF prev = new PointF();
    PointF mid = new PointF();
    float dist = 1f;

    private int bw = 50;
    
    private int bh = 50;
    
    private int save_dw = 0;
    
    private int save_dh = 0;
    
    private int x = 0;
    
    private int y = 0;
    
    private int lastX = 0;
    
    private int lastY = 0;
    
    private Paint MyVewPaint = new Paint();
    private String TAG = "Touch";
    
    private boolean clickFlag = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw_view);

		MyVewPaint.setColor(Color.RED);
        imgView = (BackGroundVeiw) findViewById(R.id.imag);// ��ȡ�ؼ�
        bitmap = imgView.getBitmap();
        imgView.setImageBitmap(bitmap);// ���ؼ�
        
        imgView.setOnTouchListener(this);// ���ô�������
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);// ��ȡ�ֱ���
        minZoom();
        center();
        imgView.setImageMatrix(matrix);
    }

    /**
     * ��������
     */
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        // ���㰴��
        case MotionEvent.ACTION_DOWN:
            savedMatrix.set(matrix);
            prev.set(event.getX(), event.getY());
            mode = DRAG;
            clickFlag = true;
            save_dw = bw;
            save_dh = bh;
            x = 0;
            y = 0;
            lastX = imgView.getX();
            lastY = imgView.getY();
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            dist = spacing(event);
            clickFlag = false;
            if (spacing(event) > 10f) {
                savedMatrix.set(matrix);
                save_dw = bw;
                save_dh = bh;
                midPoint(mid, event);
                mode = ZOOM;
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        	if(clickFlag) {
        		clickFlag = false;
            	MyView myView = new MyView(MyVewPaint, (int)(event.getX()- bw/2), (int)(event.getY() - bh/2) , bw,  bh);
            	imgView.addPoint(myView);
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
            		x = (int) dx;
            		y = (int) dy;
            		imgView.setXY(x + lastX, y + lastY);
                	matrix.postTranslate(dx, dy);
                }
            } else if (mode == ZOOM) {
                float newDist = spacing(event);
                if (newDist > 10f) {
                    matrix.set(savedMatrix);
                    float tScale = newDist / dist;
                    bw = (int)(tScale* save_dw);
                    bh = (int)(tScale *save_dh);
                    imgView.scale(bw, bh);
                    matrix.postScale(tScale, tScale, mid.x, mid.y);
                }
            }
            break;
        }
        imgView.setImageMatrix(matrix);
        return true;
    }


    /**
     * ��С���ű��������Ϊ100%
     */
    private void minZoom() {
        minScaleR = Math.max((float) dm.widthPixels / (float) bitmap.getWidth(), (float) dm.heightPixels / (float) bitmap.getHeight());
        Log.d(TAG, "### minScaleR = " + minScaleR);
        if (minScaleR < 1.0) {
            matrix.postScale(minScaleR, minScaleR);
        }
    }

    private void center() {
        center(true, true);
    }

    /**
     * �����������
     */
    protected void center(boolean horizontal, boolean vertical) {

        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // ͼƬС����Ļ��С���������ʾ��������Ļ���Ϸ������������ƣ��·�������������
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
        matrix.postTranslate(deltaX, deltaY);
    }

    /**
     * ����ľ���
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * ������е�
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}