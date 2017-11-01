package com.reactnativesplit;

/**
 * Created by yangguang01 on 2017/9/28.
 */

public class SubAActivity extends SubBundleActivity{


    @Override
    protected String getMainComponentName() {
        return "suba";
    }

    @Override
    protected String getScriptPath() {
        return "suba.bundle";
    }
}
