package com.example.drawview;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;

public class DrawView extends Activity {


	private BackGroundVeiw bgVeiw =  null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        bgVeiw = (BackGroundVeiw) findViewById(R.id.blank_view);
        bgVeiw.initXY(displayMetrics.widthPixels, displayMetrics.heightPixels);
       // bgVeiw.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_draw_view, menu);
        return true;
    }

    
}

