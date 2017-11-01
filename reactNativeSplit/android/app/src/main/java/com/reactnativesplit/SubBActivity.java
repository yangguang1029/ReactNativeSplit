package com.reactnativesplit;

/**
 * Created by yangguang01 on 2017/9/28.
 */

public class SubBActivity extends SubBundleActivity{


    @Override
    protected String getMainComponentName() {
        return "subb";
    }

    @Override
    protected String getScriptPath() {
        return "subb.bundle";
    }
}