package com.example.drawview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.widget.ImageView;

public class New extends Activity implements OnTouchListener {

	private Bitmap bg = null;//

	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();
	DisplayMetrics dm;
	Bitmap bitmap;

	float minScaleR;// ��С���ű���
	float curScaleR = 0;
	static float MAX_SCALE = 4f;// ������ű���

	static final int NONE = 0;// ��ʼ״̬
	static final int DRAG = 1;// �϶�
	static final int ZOOM = 2;// ����
	int mode = NONE;

	PointF prev = new PointF();
	PointF mid = new PointF();
	float dist = 1f;

	private Paint MyVewPaint = new Paint();
	private String TAG = "Touch";

	private MyImageView imageView = null;

	private boolean clickFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageView = new MyImageView(this);
		setContentView(imageView);
		bitmap = bg;
		imageView.setImageBitmap(bitmap);// ���ؼ�
		imageView.setClickable(true);
		imageView.setOnTouchListener(this);
		dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);// ��ȡ�ֱ���
		minZoom();
		center();
		imageView.setImageMatrix(matrix);
	}

	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// ���㰴��
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
			if (clickFlag) {
				clickFlag = false;
			}
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				matrix.set(savedMatrix);
				float dx = (event.getX() - prev.x);
				if (Math.abs(dx) > 5f) {
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
		imageView.setImageMatrix(matrix);
		CheckView();
		return true;
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
		Log.d(TAG, "width= " + width + " height=" + height);
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

	private void CheckView() {
		float p[] = new float[9];
		matrix.getValues(p);
		curScaleR = p[0];
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

	/**
	 * ��С���ű��������Ϊ100%
	 */
	private void minZoom() {
		minScaleR = Math.max(
				(float) dm.widthPixels / (float) bitmap.getWidth(),
				(float) dm.heightPixels / (float) bitmap.getHeight());
		MAX_SCALE = Math.max(
				(float) bitmap.getWidth() / (float) dm.widthPixels,
				(float) bitmap.getHeight() / (float) dm.heightPixels);
		;
		if (minScaleR < 1.0) {
			matrix.postScale(minScaleR, minScaleR);
		}
	}

	private void center() {
		center(true, true);
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

	private class MyImageView extends ImageView {

		public MyImageView(Context context) {
			super(context);
			bg = BitmapFactory.decodeResource(getResources(),
					R.drawable._birds_24);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
		}

	}
}