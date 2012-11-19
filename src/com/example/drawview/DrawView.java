package com.example.drawview;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class DrawView extends Activity {


	private BackGroundVeiw bgVeiw =  null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_draw_view);
        bgVeiw = (BackGroundVeiw) findViewById(R.id.blank_view);
       // bgVeiw.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_draw_view, menu);
        return true;
    }

    
}

