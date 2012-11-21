package com.example.drawview;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtil {
	// 背景缩放
	// 等比例缩放
	public static Bitmap adaptive(float scalX, float scalY, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		int width = bitmap.getWidth();// 获取资源位图的宽
		int height = bitmap.getHeight();// 获取资源位图的高
		float w = scalX / bitmap.getWidth();
		float h = scalY / bitmap.getHeight();
		matrix.postScale(w, h);// 获取缩放比例
		// 根据缩放比例获取新的位图
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}
}
