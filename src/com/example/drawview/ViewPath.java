package com.example.drawview;

import android.graphics.Paint;

public class ViewPath {

	
	public int startX;
	
	public int startY;
	
	public int stopX;
	
	public int stopY;
	
	public Paint paint;
	
	public ViewPath(int startX, int startY,int stopX, int stopY, Paint paint) {
		this.paint = paint;
		this.startX = startX;
		this.startY = startY;
		this.stopX = stopX;
		this.stopY = stopY;
	}
}
