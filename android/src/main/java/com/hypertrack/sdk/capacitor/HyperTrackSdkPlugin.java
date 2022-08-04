package com.hypertrack.sdk.capacitor;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.hypertrack.sdk.AvailabilityError;
import com.hypertrack.sdk.AvailabilityStateObserver;
import com.hypertrack.sdk.TrackingError;
import com.hypertrack.sdk.TrackingStateObserver;

import org.json.JSONObject;

@CapacitorPlugin(name = "HyperTrackSdk")
public class HyperTrackSdkPlugin extends Plugin  implements TrackingStateObserver.OnTrackingStateChangeListener, AvailabilityStateObserver.OnAvailabilityStateChangeListener {

    private final HyperTrackSdk implementation = new HyperTrackSdk();

    @PluginMethod
    public void initialize(PluginCall call) {
        implementation.print("initialize called");
        try {
            String publishableKey = call.getString("publishableKey");
            implementation.print(publishableKey);
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
            implementation.setTrackingNotificationProperties("Notification","Tracking has been started...");
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
            implementation.setTrackingNotificationProperties("Notification","Tracking has been stopped...");
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

    @PluginMethod
    public void hyperTrackRequestPermissions(PluginCall call) {
        implementation.print("hyperTrackRequestPermissions called");
        try {
            implementation.hyperTrackRequestPermissions();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void hyperTrackAllowMockLocations(PluginCall call) {
        implementation.print("hyperTrackAllowMockLocations called");
        try {
           implementation.hyperTrackAllowMockLocations();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void getDeviceId(PluginCall call) {
        implementation.print("getDeviceId called");
        try {
            String deviceId = implementation.getDeviceId();
            JSObject ret = new JSObject();
            ret.put("deviceId", implementation.print(deviceId));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void setDeviceName(PluginCall call) {
        implementation.print("setDeviceName called");
        String deviceName = call.getString("name");
        try {
            implementation.setDeviceName(deviceName);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void getLatestLocation(PluginCall call) {
        implementation.print("getLatestLocation called");
        try {
            JSONObject latestLocation = implementation.getLatestLocation().getJSONObject("location");
            JSObject ret = new JSObject();
            ret.put("location",latestLocation);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public void addGeotag(PluginCall call) {
        implementation.print("addGeotag called");
        try {
            String geoTagData = call.getData().getString("metadata");
            String expectedLocation = call.getData().getString("coordinates");
            implementation.addGeotag(geoTagData,expectedLocation);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void setDeviceMetadata(PluginCall call) {
        implementation.print("setDeviceMetadata called");
        try {
            JSObject deviceMetaJsonObject = call.getData();
            implementation.setDeviceMetadata(deviceMetaJsonObject);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void syncDeviceSettings(PluginCall call) {
        implementation.print("syncDeviceSettings called");
        try {
            implementation.syncDeviceSettings();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void getAvailability(PluginCall call) {
        implementation.print("getAvailability called");
        try {
            Object availability = implementation.getAvailability();
            JSObject ret = new JSObject();
            ret.put("status",availability);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void setAvailability(PluginCall call) {
        implementation.print("setAvailability called");
        try {
            Boolean isAvailable = call.getBoolean("isAvailable");
            implementation.setAvailability(isAvailable);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void isTracking(PluginCall call) {
        implementation.print("isTracking called");
        try {
            boolean isTracking = implementation.isTracking();
            JSObject ret = new JSObject();
            ret.put("status", isTracking);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void  addTrackingListener(PluginCall call) {
        implementation.print("addTrackingListener called");
        try {
            implementation.addTrackingListener(this);
            JSObject ret = new JSObject();
            ret.put("value", "listener added");
            notifyListeners("trackingStateChange", ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void  removeTrackingListener(PluginCall call) {
        implementation.print("removeTrackingListener called");
        try {
            implementation.removeTrackingListener(this);
            JSObject ret = new JSObject();
            ret.put("value", "listener removed");
            notifyListeners("trackingStateChange", ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void  setTrackingNotificationProperties(PluginCall call) {
        implementation.print("setTrackingNotificationProperties called");
        try {
            String title = call.getData().getString("title");
            String body = call.getData().getString("message");
            implementation.setTrackingNotificationProperties(title,body);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void  addAvailabilityListener(PluginCall call) {
        implementation.print("addAvailabilityListener called");
        try {
            implementation.addAvailabilityListener(this);
            JSObject ret = new JSObject();
            ret.put("value", "Availability listener added");
            notifyListeners("availabilityStateChange", ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @PluginMethod
    public  void  removeAvailabilityListener(PluginCall call) {
        implementation.print("removeAvailabilityListener called");
        try {
            implementation.removeAvailabilityListener(this);
            JSObject ret = new JSObject();
            ret.put("value", "Availability listener removed");
            notifyListeners("availabilityStateChange", ret);
        } catch (Exception e) {
            call.reject(e.toString(),e);
        }
    }

    @Override
    public void onError(TrackingError trackingError) {
        JSObject ret = new JSObject();
        ret.put("error", trackingError.toString());
        notifyListeners("trackingStateChange", ret);
    }

    @Override
    public void onTrackingStart() {
        JSObject ret = new JSObject();
        ret.put("status", "start");
        notifyListeners("trackingStateChange",ret);
    }

    @Override
    public void onTrackingStop() {
        JSObject ret = new JSObject();
        ret.put("status", "stop");
        notifyListeners("trackingStateChange",ret);
    }

    @Override
    public void onError(AvailabilityError availabilityError) {
        JSObject ret = new JSObject();
        ret.put("error", availabilityError.toString());
        notifyListeners("availabilityStateChange", ret);
    }

    @Override
    public void onAvailable() {
        JSObject ret = new JSObject();
        ret.put("availability",true);
        notifyListeners("availabilityStateChange",ret);
    }

    @Override
    public void onUnavailable() {
        JSObject ret = new JSObject();
        ret.put("availability",false);
        notifyListeners("availabilityStateChange",ret);
    }
}
