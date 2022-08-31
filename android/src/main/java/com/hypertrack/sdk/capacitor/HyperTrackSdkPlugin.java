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

import org.json.JSONArray;
import org.json.JSONObject;

@CapacitorPlugin(name = "HyperTrackSdk")
public class HyperTrackSdkPlugin extends Plugin implements TrackingStateObserver.OnTrackingStateChangeListener, AvailabilityStateObserver.OnAvailabilityStateChangeListener {

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
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void start(PluginCall call) {
        implementation.print("start called");
        try {
            implementation.start();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void stop(PluginCall call) {
        implementation.print("stop called");
        try {
            implementation.stop();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void enableDebugLogging(PluginCall call) {
        implementation.print("enableDebugLogging called");
        try {
            implementation.enableDebugLogging();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void requestPermissionsIfNecessary(PluginCall call) {
        implementation.print("requestPermissionsIfNecessary called");
        try {
            implementation.requestPermissionsIfNecessary();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void allowMockLocations(PluginCall call) {
        implementation.print("allowMockLocations called");
        try {
            implementation.hyperTrackAllowMockLocations();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void getDeviceId(PluginCall call) {
        implementation.print("getDeviceId called");
        try {
            String deviceId = implementation.getDeviceId();
            JSObject result = new JSObject();
            result.put("deviceId", implementation.print(deviceId));
            call.resolve(result);
        } catch (Exception e) {
            call.reject(e.toString(), e);
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
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod(returnType = PluginMethod.RETURN_CALLBACK)
    public void getLatestLocation(PluginCall call) {
        implementation.print("getLatestLocation called");
        try {
            JSONObject latestLocation = implementation.getLatestLocation().getJSONObject("location");
            JSObject result = new JSObject();
            result.put("location", latestLocation);
            call.resolve(result);
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void addGeotag(PluginCall call) {
        implementation.print("addGeotag called");
        try {
            String geoTagData = call.getData().getString("metadata");
            String expectedLocation = call.getData().getString("coordinates");
            implementation.addGeotag(geoTagData, expectedLocation);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void setDeviceMetadata(PluginCall call) {
        implementation.print("setDeviceMetadata called");
        try {
            JSObject deviceMetaJsonObject = call.getData();
            implementation.setDeviceMetadata(deviceMetaJsonObject);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void syncDeviceSettings(PluginCall call) {
        implementation.print("syncDeviceSettings called");
        try {
            implementation.syncDeviceSettings();
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void getAvailability(PluginCall call) {
        implementation.print("getAvailability called");
        try {
            Object availability = implementation.getAvailability();
            JSObject result = new JSObject();
            result.put("status", availability);
            call.resolve(result);
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void setAvailability(PluginCall call) {
        implementation.print("setAvailability called");
        try {
            Boolean isAvailable = call.getBoolean("isAvailable");
            implementation.setAvailability(isAvailable);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void isTracking(PluginCall call) {
        implementation.print("isTracking called");
        try {
            boolean isTracking = implementation.isTracking();
            JSObject result = new JSObject();
            result.put("status", isTracking);
            call.resolve(result);
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void addTrackingListener(PluginCall call) {
        implementation.print("addTrackingListener called");
        try {
            implementation.addTrackingListener(this);
            implementation.print("Tracker listener added successfully.");
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void removeTrackingListener(PluginCall call) {
        implementation.print("removeTrackingListener called");
        try {
            implementation.removeTrackingListener(this);
            implementation.print("Tracker listener removed successfully.");
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void setTrackingNotificationProperties(PluginCall call) {
        implementation.print("setTrackingNotificationProperties called");
        try {
            String title = call.getData().getString("title");
            String body = call.getData().getString("message");
            implementation.setTrackingNotificationProperties(title, body);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void addAvailabilityListener(PluginCall call) {
        implementation.print("addAvailabilityListener called");
        try {
            implementation.addAvailabilityListener(this);
            implementation.print("Availability listener added successfully.");
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void removeAvailabilityListener(PluginCall call) {
        implementation.print("removeAvailabilityListener called");
        try {
            implementation.removeAvailabilityListener(this);
            implementation.print("Availability listener removed successfully.");
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void getBlockers(PluginCall call) {
        implementation.print("getBlockers called");
        try {
            JSONArray blockers = implementation.getBlockers();
            JSObject result = new JSObject();
            result.put("blockers", blockers);
            call.resolve(result);
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @PluginMethod
    public void resolveBlocker(PluginCall call) {
        implementation.print("resolveBlocker called");
        String code = call.getString("code");
        try {
            implementation.resolveBlocker(code);
            call.resolve();
        } catch (Exception e) {
            call.reject(e.toString(), e);
        }
    }

    @Override
    public void onError(TrackingError trackingError) {
        JSObject result = new JSObject();
        result.put("error", trackingError.toString());
        notifyListeners("trackingStateChange", result);
    }

    @Override
    public void onTrackingStart() {
        JSObject result = new JSObject();
        result.put("status", "start");
        notifyListeners("trackingStateChange", result);
    }

    @Override
    public void onTrackingStop() {
        JSObject result = new JSObject();
        result.put("status", "stop");
        notifyListeners("trackingStateChange", result);
    }

    @Override
    public void onError(AvailabilityError availabilityError) {
        JSObject result = new JSObject();
        result.put("error", availabilityError.toString());
        notifyListeners("availabilityStateChange", result);
    }

    @Override
    public void onAvailable() {
        JSObject result = new JSObject();
        result.put("availability", true);
        notifyListeners("availabilityStateChange", result);
    }

    @Override
    public void onUnavailable() {
        JSObject result = new JSObject();
        result.put("availability", false);
        notifyListeners("availabilityStateChange", result);
    }
}
