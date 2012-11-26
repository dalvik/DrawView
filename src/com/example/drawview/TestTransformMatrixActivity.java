package com.example.drawview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class TestTransformMatrixActivity extends Activity implements
		OnTouchListener {
	private TransformMatrixView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		view = new TransformMatrixView(this);
		view.setScaleType(ImageView.ScaleType.MATRIX);
		view.setOnTouchListener(this);

		setContentView(view);
	}

	class TransformMatrixView extends ImageView {
		private Bitmap bitmap;
		private Matrix matrix;

		public TransformMatrixView(Context context) {
			super(context);
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable._birds_24);
			matrix = new Matrix();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// ����ԭͼ��
			canvas.drawBitmap(bitmap, 0, 0, null);
			// �����任���ͼ��
			canvas.drawBitmap(bitmap, matrix, null);
			super.onDraw(canvas);
		}

		@Override
		public void setImageMatrix(Matrix matrix) {
			this.matrix.set(matrix);
			super.setImageMatrix(matrix);
		}

		public Bitmap getImageBitmap() {
			return bitmap;
		}
	}

	public boolean onTouch(View v, MotionEvent e) {
		System.out.println("ee" + e);
		if (e.getAction() == MotionEvent.ACTION_UP) {
			Matrix matrix = new Matrix();
			// ���ͼ��Ŀ�Ⱥ͸߶�(162 x 251)
			Log.e("TestTransformMatrixActivity",
					"image size: width x height = "
							+ view.getImageBitmap().getWidth() + " x "
							+ view.getImageBitmap().getHeight());
			// 1. ƽ��
			/*matrix.postTranslate(view.getImageBitmap().getWidth(), view
					.getImageBitmap().getHeight());
			// ��x����ƽ��view.getImageBitmap().getWidth()����y�᷽��view.getImageBitmap().getHeight()
			view.setImageMatrix(matrix);

			// ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			float[] matrixValues = new float[9];
			matrix.getValues(matrixValues);
			for (int i = 0; i < 3; ++i) {
				String temp = new String();
				for (int j = 0; j < 3; ++j) {
					temp += matrixValues[3 * i + j] + "\t";
				}
				Log.e("TestTransformMatrixActivity", temp);
			}*/

			// // 2. ��ת(Χ��ͼ������ĵ�)
			 matrix.setRotate(45f, view.getImageBitmap().getWidth() / 2f,
			 view.getImageBitmap().getHeight() / 2f);
			
			 // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			 matrix.postTranslate(view.getImageBitmap().getWidth() * 1.5f,
			 0f);
			 view.setImageMatrix(matrix);
			
			 // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			 float[] matrixValues = new float[9];
			 matrix.getValues(matrixValues);
			 for(int i = 0; i < 3; ++i)
			 {
			 String temp = new String();
			 for(int j = 0; j < 3; ++j)
			 {
			 temp += matrixValues[3 * i + j ] + "\t";
			 }
			 Log.e("TestTransformMatrixActivity", temp);
			 }

			// // 3. ��ת(Χ������ԭ��) + ƽ��(Ч��ͬ2)
			// matrix.setRotate(45f);
			// matrix.preTranslate(-1f * view.getImageBitmap().getWidth() / 2f,
			// -1f * view.getImageBitmap().getHeight() / 2f);
			// matrix.postTranslate((float)view.getImageBitmap().getWidth() /
			// 2f, (float)view.getImageBitmap().getHeight() / 2f);
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate((float)view.getImageBitmap().getWidth() *
			// 1.5f, 0f);
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 4. ����
			// matrix.setScale(2f, 2f);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(view.getImageBitmap().getWidth(),
			// view.getImageBitmap().getHeight());
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 5. ���� - ˮƽ
			// matrix.setSkew(0.5f, 0f);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(view.getImageBitmap().getWidth(), 0f);
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 6. ���� - ��ֱ
			// matrix.setSkew(0f, 0.5f);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(0f, view.getImageBitmap().getHeight());
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// 7. ���� - ˮƽ + ��ֱ
			// matrix.setSkew(0.5f, 0.5f);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(0f, view.getImageBitmap().getHeight());
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 8. �Գ� (ˮƽ�Գ�)
			// float matrix_values[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
			// matrix.setValues(matrix_values);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(0f, view.getImageBitmap().getHeight() * 2f);
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 9. �Գ� - ��ֱ
			// float matrix_values[] = {-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
			// matrix.setValues(matrix_values);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(view.getImageBitmap().getWidth() * 2f, 0f);
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			// // 10. �Գ�(�Գ���Ϊֱ��y = x)
			// float matrix_values[] = {0f, -1f, 0f, -1f, 0f, 0f, 0f, 0f, 1f};
			// matrix.setValues(matrix_values);
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// float[] matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }
			//
			// // �������ƽ�Ʊ任��������Ϊ���ñ任���ͼ���ԭͼ���ص�
			// matrix.postTranslate(view.getImageBitmap().getHeight() +
			// view.getImageBitmap().getWidth(),
			// view.getImageBitmap().getHeight() +
			// view.getImageBitmap().getWidth());
			// view.setImageMatrix(matrix);
			//
			// // ����Ĵ�����Ϊ�˲鿴matrix�е�Ԫ��
			// matrixValues = new float[9];
			// matrix.getValues(matrixValues);
			// for(int i = 0; i < 3; ++i)
			// {
			// String temp = new String();
			// for(int j = 0; j < 3; ++j)
			// {
			// temp += matrixValues[3 * i + j ] + "\t";
			// }
			// Log.e("TestTransformMatrixActivity", temp);
			// }

			view.invalidate();
		}
		return true;
	}
}