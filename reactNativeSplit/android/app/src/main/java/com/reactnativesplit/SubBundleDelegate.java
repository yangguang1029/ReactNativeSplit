package com.reactnativesplit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.reactnativesplit.RNUtil;

import javax.annotation.Nullable;

/**
 * Created by yangguang01 on 2017/8/11.
 */

public class SubBundleDelegate extends ReactActivityDelegate {

    private String mParams = null;

    private final String TAG = "SubBundleDelegate";

    public String getParams() {
        return mParams;
    }

    public void setParams(String params) {
        this.mParams = params;
    }

    private SubBundleActivity getActivity(){
        return (SubBundleActivity)RNUtil.getActivity(this);
    }

    public SubBundleDelegate(Activity activity, @Nullable String mainComponentName) {
        super(activity, mainComponentName);
    }

    private String getMainComponentName(){
        return getActivity().getMainComponentName();
    }

    private String getScriptPath(){
        return getActivity().getScriptPath();
    }

    @Nullable
    @Override
    protected Bundle getLaunchOptions() {
        if(mParams != null) {
            Bundle bundle =new Bundle();
            bundle.putString("params",mParams);
            return bundle;
        }
        return null;
    }

    public void recreateContext(){
        Log.e(TAG, "yangg recreateContext");
        ReactRootView view = RNUtil.getRootView(this);
        view.unmountReactApplication();
        ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        manager.detachRootView(view);
        ViewGroup vg = (ViewGroup)(view.getParent());
        vg.removeView(view);

        RNUtil.clearLoadedScript(getScriptPath());
        loadApp(getMainComponentName());
    }

    @Override
    protected void loadApp(String appKey) {
        Log.e(TAG, "yangg loadApp");
        ReactRootView view = createRootView();
        RNUtil.setRootView(this, view);
        ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        RNUtil.setReactInstanceManager(view, manager);
        RNUtil.setJsModuleName(view, getMainComponentName());
        view.setEventListener(getActivity().getEventListener());
        RNUtil.setLaunchOptions(view, getLaunchOptions());
        getActivity().setContentView(view);

        if (manager.getCurrentReactContext() == null) {

            if(manager.hasStartedCreatingInitialContext()){ //既然已经开始加载common了，就坐等加载完
                Log.e(TAG, "yangg loadApp hasStartedCreatingInitialContext");
            }else{
                Log.e(TAG, "yangg loadApp will create");
                manager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                    @Override
                    public void onReactContextInitialized(ReactContext reactContext) {
                        loadScriptAsync();
                    }
                });
                manager.createReactContextInBackground();
            }
        }else {
            Log.e(TAG, "yangg loadApp current context not  null");
            loadScriptAsync();
        }
    }

    private void loadScriptAsync() {
        Log.e(TAG, "yangg loadScriptAsync");
        ReactRootView view = RNUtil.getRootView(this);
        RNUtil.setViewAttached(view, true);
        ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        SubBundleActivity activity = getActivity();
        manager.onHostResume(activity, (DefaultHardwareBackBtnHandler)activity);
        Callback cb = new Callback() {
            @Override
            public void invoke(Object... args) {
                SubBundleDelegate.this.onScriptLoaded();
            }
        };
        ReactApplication app = (ReactApplication) activity.getApplication();
        RNUtil.loadScriptInBackground(app, getScriptPath(), getMainComponentName(), cb);
    }

    private void onScriptLoaded(){
        Log.e(TAG, "yangg onScriptLoaded");
        ReactRootView view = RNUtil.getRootView(this);
        ReactInstanceManager manager = getReactNativeHost().getReactInstanceManager();
        manager.attachRootView(view);
        getActivity().onScriptsLoaded();
    }

    @Override
    protected void onDestroy() {
        ReactRootView view = RNUtil.getRootView(this);
        if (view != null) {
            view.unmountReactApplication();
            RNUtil.setRootView(this, null);
        }
    }
}
