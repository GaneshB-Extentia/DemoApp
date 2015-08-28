package com.ext.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MyWearActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        mTextView = (TextView) findViewById(R.id.text);
    }
}