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

    private int rectWidth = 50;
    
    private int rectHeight = 50;
    
    private int dw = 50;
    
    private int dh = 50;
    
    private int save_dw = 0;
    
    private int save_dh = 0;
    
    private Paint MyVewPaint = new Paint();
    private String TAG = "Touch";
    
    private boolean clickFlag = false;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw_view);

		MyVewPaint.setColor(Color.RED);
        imgView = (BackGroundVeiw) findViewById(R.id.imag);// ��ȡ�ؼ�
        //bitmap = BitmapFactory.decodeResource(getResources(), this.getIntent().getExtras().getInt("IMG"));// ��ȡͼƬ��Դ
        //bitmap = BitmapFactory.decodeFile("/mnt/sdcard/IPED/Image/20120929150149.jpg");
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
            save_dw = dw;
            save_dh = dh;
            //Log.d(TAG, "### ACTION_DOWN");
            break;
        // ���㰴��
        case MotionEvent.ACTION_POINTER_DOWN:
            dist = spacing(event);
            // �����������������10�����ж�Ϊ���ģʽ
            clickFlag = false;
            if (spacing(event) > 10f) {
                savedMatrix.set(matrix);
                save_dw = dw;
                save_dh = dh;
                midPoint(mid, event);
                mode = ZOOM;
            }
            Log.d(TAG, "### ACTION_POINTER_DOWN");
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_POINTER_UP:
        	if(clickFlag) {
        		clickFlag = false;
            	MyView myView = new MyView(MyVewPaint, (int)(event.getX()- dw/2), (int)(event.getY() - dh/2) , dw,  dh);
            	imgView.addPoint(myView);
            	Log.d(TAG, "### add point");
            }
            mode = NONE;
            Log.d(TAG, "### ACTION_UP");
            break;
        case MotionEvent.ACTION_MOVE:
            if (mode == DRAG) {
                matrix.set(savedMatrix);
                float dx = (event.getX() - prev.x);
                if(Math.abs(dx)>5f){
                	clickFlag = false;
                	float dy = event.getY() - prev.y;
                	imgView.setXY((int)dx, (int)dy);
                	matrix.postTranslate(dx, dy);
                	Log.d(TAG, "### ACTION_MOVE  drag === " + dx  +  "  "+ dy);
                }
            } else if (mode == ZOOM) {
                float newDist = spacing(event);
                if (newDist > 10f) {
                    matrix.set(savedMatrix);
                    float tScale = newDist / dist;
                    dw = (int)(tScale* save_dw);
                    dh = (int)(tScale *save_dh);
                    imgView.scale(dw, dh);
                    Log.d(TAG, "### ACTION_MOVE  zoom tScale = "+ tScale);
                    matrix.postScale(tScale, tScale, mid.x, mid.y);
                }
            }
            break;
        }
        imgView.setImageMatrix(matrix);
        CheckView();
        return true;
    }

    /**
     * ���������С���ű������Զ�����
     */
    private void CheckView() {
        float p[] = new float[9];
        matrix.getValues(p);
        if (mode == ZOOM) {
            if (p[0] < minScaleR) {
                matrix.setScale(minScaleR, minScaleR);
                imgView.scale(rectWidth, rectHeight);
                Log.d(TAG, "###  CheckView ZOOM = " + minScaleR + " dw = " + dw);
            }
            if (p[0] > MAX_SCALE) {
            	imgView.scale(save_dw, save_dh);
                matrix.set(savedMatrix);
                Log.d(TAG, "###  CheckView MAX_SCALE = " + MAX_SCALE);
            }
        }
        Log.d(TAG, "### CheckView =" + p[0]);
        center();
    }

    /**
     * ��С���ű��������Ϊ100%
     */
    private void minZoom() {
        //minScaleR = Math.min((float) dm.widthPixels / (float) bitmap.getWidth(), (float) dm.heightPixels / (float) bitmap.getHeight());
    	minScaleR = (float) dm.widthPixels / (float) bitmap.getWidth();
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
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = screenHeight - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }
        Log.d(TAG, "### deltaX = "+ deltaX + " delayY = " + deltaY);
        if(deltaX != 0 && deltaY != 0) {
        	imgView.setXY((int)-deltaX, (int)-deltaY);
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