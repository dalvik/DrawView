package com.example.drawview;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

public class PonitList {

	private List<Rect> list = null;
	
	public PonitList() {
		list = new ArrayList<Rect>();
	}
	
	public void add(Rect rect) {
		list.add(rect);
	}
	
	public boolean checkPoint(int x, int y) {
		for(Rect r:list) {
			if(x>r.left && x<r.right && y>r.top && y<r.bottom) {
				return true;
			}
		}
		return false;
	}
}
