package com.hypertrack.sdk.capacitor;

import android.util.Log;
import com.hypertrack.sdk.HyperTrack;
import com.hypertrack.sdk.TrackingError;
import com.hypertrack.sdk.TrackingStateObserver;

public class HyperTrackSdk  implements TrackingStateObserver.OnTrackingStateChangeListener{

    private static final String TAG = "HyperTrackPlugin";
    private HyperTrack sdkInstance;

    public String print(String value) {
        Log.i(TAG, value);
        return value;
    }

    @Override
    public void onError(TrackingError trackingError) {
        //TO DO
    }

    @Override
    public void onTrackingStart() {
        print("Tracking is started");
    }

    @Override
    public void onTrackingStop() {
        print("Tracking is stopped");
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
                sdkInstance = HyperTrack.getInstance(publishableKey);
                return sdkInstance;
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}