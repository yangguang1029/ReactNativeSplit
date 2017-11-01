package com.reactnativesplit;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.CatalystInstanceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yangguang01 on 2017/7/21.
 */

public class RNUtil {
    private static Set<String> sLoadedScript = new HashSet<>();

    public static ReactActivityDelegate getDelegate(ReactActivity activity) {
        try {
            Field field = ReactActivity.class.getDeclaredField("mDelegate");
            field.setAccessible(true);
            return (ReactActivityDelegate) field.get(activity);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Activity getActivity(ReactActivityDelegate delegate) {
        try {
            Field field = ReactActivityDelegate.class.getDeclaredField("mActivity");
            field.setAccessible(true);
            return (Activity) field.get(delegate);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ReactRootView getRootView(ReactActivityDelegate delegate) {
        try {
            Field field = ReactActivityDelegate.class.getDeclaredField("mReactRootView");
            field.setAccessible(true);
            return (ReactRootView) field.get(delegate);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setRootView(ReactActivityDelegate delegate, ReactRootView rootView) {
        try {
            Field field = ReactActivityDelegate.class.getDeclaredField("mReactRootView");
            field.setAccessible(true);
            field.set(delegate, rootView);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setJsModuleName(ReactRootView rootView, String moduleName) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mJSModuleName");
            field.setAccessible(true);
            field.set(rootView, moduleName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setLaunchOptions(ReactRootView rootView, Bundle options) {
//        try {
//            Field field = ReactRootView.class.getDeclaredField("mLaunchOptions");
//            field.setAccessible(true);
//            field.set(rootView, options);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    public static void setViewAttached(ReactRootView rootView, boolean bAttached) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mIsAttachedToInstance");
            field.setAccessible(true);
            field.set(rootView, bAttached);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void setReactInstanceManager(ReactRootView rootView, ReactInstanceManager manager) {
        try {
            Field field = ReactRootView.class.getDeclaredField("mReactInstanceManager");
            field.setAccessible(true);
            field.set(rootView, manager);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static CatalystInstance getCatalystInstance(ReactNativeHost host) {
        ReactInstanceManager manager = host.getReactInstanceManager();
        if (manager == null) {
            Log.e("RNUtils", " manager is null");
            return null;
        }

        ReactContext context = manager.getCurrentReactContext();
        if (context == null) {
            Log.e("RNUtils", " context is null");
            return null;
        }
        return context.getCatalystInstance();
    }

    public static void clearLoadedScript(String bundlePath) {
        sLoadedScript.remove(bundlePath);
    }

    public static void loadScriptInBackground(ReactApplication application, String bundlepath, String productName, Callback cb){
        Log.e("RNN", " loadScriptInBackground bundlepath:"+bundlepath);
        LoadScriptTask task = new LoadScriptTask(application, bundlepath, productName, cb);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void loadScript(Context context,
                                  CatalystInstance instance,
                                  String bundlepath,
                                  String productname){

        Log.e("RNN", "yangg bundlepath:"+bundlepath);
        loadScriptFromAsset(context,instance,bundlepath);
    }

    @WorkerThread
    private static void loadScriptFromAsset(Context context,
                                    CatalystInstance instance,
                                    String bundlepath) {
        if (sLoadedScript.contains(bundlepath)) {
            Log.e("RNN", "yangg bundle already loaded return: "+bundlepath);
            return;
        }

        String source = "assets://" + bundlepath;
        Log.e("RNN", "yangg loadScriptFromAsset:"+source);
        try {
        Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromAssets",
                AssetManager.class,
                String.class,
                boolean.class);
        method.setAccessible(true);
        method.invoke(instance, context.getAssets(), source, false);
        sLoadedScript.add(bundlepath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    @WorkerThread
    private static void loadScriptFromFile(Context context,
                                           CatalystInstance instance,
                                           String bundlepath) {
       if (sLoadedScript.contains(bundlepath)) {
           Log.e("RNN", "yangg bundle already loaded return: "+bundlepath);
           return;
       }
        Log.e("RNN", "yangg loadScriptFromFile:"+bundlepath);
        try {
            Method method = CatalystInstanceImpl.class.getDeclaredMethod("loadScriptFromFile",
                    String.class,
                    String.class,
                    boolean.class);
            method.setAccessible(true);
            method.invoke(instance, bundlepath, bundlepath, false);
            sLoadedScript.add(bundlepath);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
