package com.hypertrack.sdk.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.hypertrack.sdk.HyperTrack;
@CapacitorPlugin(name = "HyperTrackSdk")
public class HyperTrackSdkPlugin extends Plugin {

    private HyperTrackSdk implementation = new HyperTrackSdk();
    private HyperTrack hyperTrack;
    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");
        JSObject ret = new JSObject();
        HyperTrack.enableDebugLogging();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
