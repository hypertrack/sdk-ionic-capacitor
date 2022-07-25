package com.hypertrack.sdk.capacitor;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "HyperTrackSdk")
public class HyperTrackSdkPlugin extends Plugin {

    private final HyperTrackSdk implementation = new HyperTrackSdk();

    @PluginMethod
    public void initialize(PluginCall call) {
        String publishableKey = call.getString("publishableKey");
        implementation.print(publishableKey);
        try {
            implementation.initialize(publishableKey);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void start(PluginCall call) {
        implementation.print("start called");
        try{
            implementation.start();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void stop(PluginCall call) {
        implementation.print("stop called");
        try{
            implementation.stop();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void enableDebugLogging(PluginCall call) {
        implementation.print("enableDebugLogging called");
        try {
            implementation.enableDebugLogging();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }
}
