package com.weiwei.nativemoduleexample;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;


/**
 * Created by pingguo on 2017/5/10.
 */

public class BGNativeExampleModule extends ReactContextBaseJavaModule {

    protected static final String TAG = BGNativeExampleModule.class.getSimpleName();
    private static final String TestEventName = "TestEventName";
    private Timer timer;
    public BGNativeExampleModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        //开启定时器
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                WritableMap params = Arguments.createMap();
                params.putString("name", "Jack");
                reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit(TestEventName, params);
            }
        };
        timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }
    @Override
    public String getName() {
        return "BGNativeModuleExample";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("BGModuleName", "BGNativeModuleExample");
        constants.put(TestEventName, TestEventName);
        return constants;
    }

    @ReactMethod
    public void testPrint(String name, ReadableMap info){
        Log.i(TAG, name);
        Log.i(TAG, info.toString());
    }

    @ReactMethod
    public void getNativeClass(Callback callback){
        callback.invoke("BGNativeExampleModule");
    }

    @ReactMethod
    public void testPromises(Boolean isResolve, Promise promise){
        if(isResolve){
            promise.resolve(isResolve.toString());
        }
        else{
            promise.reject("reject", isResolve.toString());

        }
    }
}
