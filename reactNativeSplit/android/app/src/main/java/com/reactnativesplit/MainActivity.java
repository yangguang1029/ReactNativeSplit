package com.reactnativesplit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;

public class MainActivity extends Activity {

    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        createContext();

        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SubAActivity.class));
            }
        });

        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SubBActivity.class));
            }
        });
    }

    private ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getApplication()).getReactNativeHost();
    }

    private void createContext(){
        Log.e(TAG, "yangg start createContext");
        final ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        if (!manager.hasStartedCreatingInitialContext()) {
            manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                @Override
                public void onReactContextInitialized(ReactContext context) {
                    MainActivity.this.onContextInitialized();
                }
            });
            manager.createReactContextInBackground();
        }
    }
    private void onContextInitialized(){
        Log.e(TAG, "yangg onContextInitialized");
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
                manager.onHostResume(MainActivity.this, null);
            }
        });

    }

}
