package com.ext.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cartentia.com.common.manager.JsonRequestHandler;
import cartentia.com.common.manager.VolleyManager;

/**
 * Created by Abhijeet.Bhosale on 8/28/2015.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
    }

    public void onClick(View view) throws UnsupportedEncodingException {
        String url = "http://121.243.26.108:8001/api/users?username=nfcuser1&password=user123";

        Request request = new JsonRequestHandler(Request.Method.GET, url, null, null, new Response.Listener() {
            @Override
            public void onResponse(Object o) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        VolleyManager.getInstance().addRequestToQueue(request);

    }
}
