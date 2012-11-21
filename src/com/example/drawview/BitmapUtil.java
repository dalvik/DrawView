package com.example.drawview;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtil {
	// ��������
	// �ȱ�������
	public static Bitmap adaptive(float scalX, float scalY, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		int width = bitmap.getWidth();// ��ȡ��Դλͼ�Ŀ�
		int height = bitmap.getHeight();// ��ȡ��Դλͼ�ĸ�
		float w = scalX / bitmap.getWidth();
		float h = scalY / bitmap.getHeight();
		matrix.postScale(w, h);// ��ȡ���ű���
		// �������ű�����ȡ�µ�λͼ
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}
}
