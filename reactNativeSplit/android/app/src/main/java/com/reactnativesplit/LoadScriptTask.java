package com.reactnativesplit;

import android.content.Context;
import android.os.AsyncTask;

import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;

/**
 * Created by yangguang01 on 2017/8/16.
 */

public  class LoadScriptTask extends AsyncTask<Void, Void, Void> {

    private Callback mcallback = null;
    private ReactApplication mApplication;
    private String mAssetPath;
    private String mComponentName;
    private boolean mIsUpdate = false;

    public LoadScriptTask(ReactApplication application, String assetPath,
                            String componentName,
                            Callback cb) {
        mcallback = cb;
        mApplication = application;
        mAssetPath = assetPath;
        mComponentName = componentName;
    }

    @Override
    protected Void doInBackground(Void... params) {
        CatalystInstance instance = RNUtil.getCatalystInstance(mApplication.getReactNativeHost());
        Context context = (Context)mApplication;
        RNUtil.loadScript(context,
                instance,
                mAssetPath,
                mComponentName);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(mcallback != null){
            mcallback.invoke();
        }
    }
}