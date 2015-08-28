package com.ext.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.text.DateFormat;
import java.util.Date;

public class TestActivity extends Activity {

    private static final String TAG = "PhoneActivity";
    private GoogleApiClient mGoogleApiClient;

    String NOTIFICATION_PATH = "/demo/myevent";
    String NOTIFICATION_TITLE = "title";
    String NOTIFICATION_CONTENT = "content";
    String ACTION_DISMISS = "com.ext.demo.DISMISS";
    String NOTIFICATION_TIMESTAMP = "timestamp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = ((EditText) findViewById(R.id.et)).getText().toString();

                sendNotification(s);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    private String now() {
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(this);
        return dateFormat.format(new Date());
    }

    private void sendNotification(String s) {
        if (mGoogleApiClient.isConnected()) {
            PutDataMapRequest dataMapRequest = PutDataMapRequest.create(NOTIFICATION_PATH);
            // Make sure the data item is unique. Usually, this will not be required, as the payload
            // (in this case the title and the content of the notification) will be different for almost all
            // situations. However, in this example, the text and the content are always the same, so we need
            // to disambiguate the data item by adding a field that contains teh current time in milliseconds.
            dataMapRequest.getDataMap().putDouble(NOTIFICATION_TIMESTAMP, System.currentTimeMillis());
            dataMapRequest.getDataMap().putString(NOTIFICATION_TITLE, s);
            dataMapRequest.getDataMap().putString(NOTIFICATION_CONTENT, s + " 007");
            PutDataRequest putDataRequest = dataMapRequest.asPutDataRequest();
            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest);
        } else {
            Log.e(TAG, "No connection to wearable available!");
        }
    }
}
