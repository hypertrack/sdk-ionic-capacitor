package com.hypertrack.sdk.capacitor;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.hypertrack.sdk.Availability;
import com.hypertrack.sdk.Blocker;
import com.hypertrack.sdk.HyperTrack;
import com.hypertrack.sdk.OutageReason;
import com.hypertrack.sdk.Result;
import com.hypertrack.sdk.ServiceNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HyperTrackSdk {

    private static final String TAG = "HyperTrackPlugin";
    private HyperTrack sdkInstance;

    public String print(String value) {
        Log.i(TAG, value);
        return value;
    }

    public void stop() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        if (!sdkInstance.isTracking()) {
            throw new IllegalStateException("Tracking wasn't started");
        }
        try {
            sdkInstance.stop();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public void start() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.start();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void enableDebugLogging() {
        try {
            HyperTrack.enableDebugLogging();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public HyperTrack initialize(String publishableKey) {
        if (publishableKey == null || publishableKey.length() == 0) {
            throw new IllegalArgumentException("Publishable Key is required");
        } else {
            try {
                if (sdkInstance == null) {
                    sdkInstance = HyperTrack.getInstance(publishableKey);
                }
                return sdkInstance;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    public JSONObject getLatestLocation() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            Result<Location, OutageReason> locationResult = sdkInstance.getLatestLocation();
            JSONObject latestLocation = createLocationResult(locationResult);
            return latestLocation;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addGeotag(String geoTagData, String coordinate) throws JSONException {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        if (geoTagData == null || geoTagData.length() == 0) {
            throw new IllegalStateException("Metadata is required.");
        }
        try {
            JSONObject tagMetaJsonObject = new JSONObject(geoTagData);
            Map<String, Object> payload = jsonToMap(tagMetaJsonObject);
            if (!(coordinate == null || coordinate.length() == 0)) {
                Location expectedLocation = getExpectedLocation(coordinate);
                sdkInstance.addGeotag(payload, expectedLocation);
            } else {
                sdkInstance.addGeotag(payload);
            }
            return;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setDeviceMetadata(JSObject deviceMetaJsonObject) throws JSONException {
        try {
            Map<String, Object> meta = jsonToMap(deviceMetaJsonObject);
            sdkInstance.setDeviceMetadata(meta);
            return;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setDeviceName(String deviceName) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.setDeviceName(deviceName);
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getDeviceId() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            String deviceId = sdkInstance.getDeviceID();
            return deviceId;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void requestPermissionsIfNecessary() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.requestPermissionsIfNecessary();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void hyperTrackAllowMockLocations() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.allowMockLocations();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void syncDeviceSettings() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.syncDeviceSettings();
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Object getAvailability() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            Availability availability = sdkInstance.getAvailability();
            return availability;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setAvailability(Boolean isAvailable) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            if (isAvailable) {
                sdkInstance.setAvailability(Availability.AVAILABLE);
            }
            if (!isAvailable) {
                sdkInstance.setAvailability(Availability.UNAVAILABLE);
            }
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean isTracking() {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            boolean isTracking = sdkInstance.isTracking();
            return isTracking;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addTrackingListener(HyperTrackSdkPlugin hyperTrackSdkPlugin) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.addTrackingListener(hyperTrackSdkPlugin);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void removeTrackingListener(HyperTrackSdkPlugin hyperTrackSdkPlugin) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.removeTrackingListener(hyperTrackSdkPlugin);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void removeAvailabilityListener(HyperTrackSdkPlugin hyperTrackSdkPlugin) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.removeAvailabilityListener(hyperTrackSdkPlugin);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void addAvailabilityListener(HyperTrackSdkPlugin hyperTrackSdkPlugin) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            sdkInstance.addAvailabilityListener(hyperTrackSdkPlugin);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setTrackingNotificationProperties(String title, String body) {
        if (sdkInstance == null) {
            throw new IllegalStateException("Sdk wasn't initialized");
        }
        try {
            ServiceNotificationConfig serviceNotificationConfig = new ServiceNotificationConfig.Builder().setContentTitle(title).setContentText(body).build();
            sdkInstance.setTrackingNotificationConfig(serviceNotificationConfig);
            return;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public JSONArray getBlockers() {
        try {
            Set<Blocker> blockersSet = HyperTrack.getBlockers();
            return parseBlockers(blockersSet);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean resolveBlocker(String blockerCode) {
        switch (blockerCode) {
            case "OL1":
                Blocker.LOCATION_PERMISSION_DENIED.resolve();
                print("Blocker" + blockerCode + "LOCATION_PERMISSION_DENIED resolved");
                break;
            case "OS1":
                Blocker.LOCATION_SERVICE_DISABLED.resolve();
                print("Blocker" + blockerCode + "LOCATION_SERVICE_DISABLED resolved");
                break;
            case "OA1":
                Blocker.ACTIVITY_PERMISSION_DENIED.resolve();
                print("Blocker" + blockerCode + "ACTIVITYResolveBlocker_PERMISSION_DENIED resolved");
                break;
            case "OL2":
                Blocker.BACKGROUND_LOCATION_DENIED.resolve();
                print("Blocker" + blockerCode + "BACKGROUND_LOCATION_DENIED resolved");
                break;
            default:
                throw new IllegalArgumentException("Unknown blocker code " + blockerCode);
        }
        return true;
    }

    private JSONObject createLocationResult(Result<Location, OutageReason> locationResult) {
        if (locationResult.isSuccess()) {
            return createLocationSuccessResult(locationResult.getValue());
        } else {
            return createOutageLocationResult(locationResult.getError());
        }
    }

    private JSONObject createLocationSuccessResult(Location location) {
        JSONObject serializedResult = new JSONObject();
        try {
            serializedResult.put("type", "location");
            serializedResult.put(
                    "location",
                    getLocationJson(location)
            );
            return serializedResult;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private JSONObject getLocationJson(Location location) {
        JSONObject json = new JSONObject();
        try {
            json.put("latitude", location.getLatitude());
            json.put("longitude", location.getLongitude());
            return json;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private JSONObject createOutageLocationResult(OutageReason outage) {
        JSONObject serializedResult = new JSONObject();
        try {
            serializedResult.put("type", "outage");
            serializedResult.put("outage", getOutageJson(outage));
            return serializedResult;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private JSONObject getOutageJson(OutageReason outage) {
        JSONObject json = new JSONObject();
        try {
            json.put("code", outage.ordinal());
            json.put("name", outage.name());
            return json;
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    private Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    private Location getExpectedLocation(String coordinate) throws JSONException {
        print("expected location argument " + coordinate);
        JSONObject locationJsonObject = new JSONObject(coordinate);
        Double latitude = locationJsonObject.getDouble("latitude");
        Double longitude = locationJsonObject.getDouble("longitude");
        Location expectedLocation = new Location(LocationManager.GPS_PROVIDER);
        expectedLocation.setLatitude(latitude);
        expectedLocation.setLongitude(longitude);
        return expectedLocation;
    }

    private JSONArray parseBlockers(Set<Blocker> blockers) {
        JSONArray result = new JSONArray();
        for (Blocker blocker : blockers) {
            try {
                JSONObject parsedBlockers = new JSONObject();
                parsedBlockers.put("userActionTitle", blocker.userActionTitle);
                parsedBlockers.put("userActionExplanation", blocker.userActionExplanation);
                parsedBlockers.put("userActionCTA", blocker.userActionCTA);
                parsedBlockers.put("code", blocker.code);
                result.put(parsedBlockers);
            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return result;
    }
}
