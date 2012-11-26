package com.example.drawview;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public class MyViewPoint  {

	public Paint paint = null;
	
	public int left;
	
	public int top;
	
	public int width;
	
	public int height;
	
	public Bitmap bitmap;
	
	public Rect rect;
	
	public MyViewPoint(Paint paint, int left, int top, int width, int height, Bitmap bitmap) {
		this.paint = paint;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
		this.bitmap = bitmap;
	}
	
	
	public MyViewPoint(Paint paint, Rect rect, Bitmap bitmap) {
        this.paint = paint;
        this.rect = rect;
        this.bitmap = bitmap;
    }
	
}
