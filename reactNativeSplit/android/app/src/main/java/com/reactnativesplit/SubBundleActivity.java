package com.reactnativesplit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.reactnativesplit.RNUtil;

/**
 * Created by yangguang01 on 2017/8/11.
 */

public abstract class SubBundleActivity extends ReactActivity {
    private BroadcastReceiver mReceiver;

    private final String TAG ="SubBundleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "yangg onCreate");
        super.onCreate(savedInstanceState);
        intiReceiver();
    }

    private void intiReceiver(){
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                String name = intent.getStringExtra("name");
                if(action.equals("reload")){
                    recreateContext(name);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("reload");
        this.registerReceiver(mReceiver, filter);
    }

    public void recreateContext(String name){
        if(name != null && !getMainComponentName().toLowerCase().equals(name)) {
            return;
        }
        SubBundleDelegate delegate = (SubBundleDelegate)RNUtil.getDelegate(this);
        delegate.recreateContext();
    }

    @Override
    protected ReactActivityDelegate createReactActivityDelegate() {
        return new SubBundleDelegate(this, getMainComponentName());
    }


    protected @Nullable
    ReactRootView.ReactRootViewEventListener getEventListener(){return null;}

    public void onScriptsLoaded(){
        Log.e(TAG, "yangg onScriptsLoaded");
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            this.unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }

    protected abstract String getMainComponentName();
    protected abstract String getScriptPath();

}
