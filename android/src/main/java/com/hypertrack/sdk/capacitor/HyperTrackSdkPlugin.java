package com.hypertrack.sdk.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "HyperTrackSdk")
public class HyperTrackSdkPlugin extends Plugin {

    private HyperTrackSdk implementation = new HyperTrackSdk();

    @PluginMethod
    public void initialize(PluginCall call) {
        String publishableKey = call.getString("publishableKey");
        implementation.print(publishableKey);
        try {
            implementation.initialize(publishableKey);
            JSObject ret = new JSObject();
            ret.put("value",true);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void start(PluginCall call) {
        try{
            implementation.Start();
            JSObject ret = new JSObject();
            ret.put("value",true);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }

    @PluginMethod
    public void stop(PluginCall call) {
        try{
            implementation.Stop();
            JSObject ret = new JSObject();
            ret.put("value",true);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.getMessage());
        }

    }

    @PluginMethod
    public void enableDebugLogging(PluginCall call) {
        implementation.print("enableDebugLogging called");
        try {
            implementation.enableDebugLogging();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.getMessage());
        }
    }
}
