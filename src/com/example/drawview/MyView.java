package com.example.drawview;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class MyView  {

	public Paint paint = null;
	
	public int left;
	
	public int top;
	
	public int width;
	
	public int height;
	
	public Bitmap bitmap;
	
	public MyView(Paint paint, int left, int top, int width, int height, Bitmap bitmap) {
		this.paint = paint;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.bitmap = bitmap;
	}
	
	

	
}
