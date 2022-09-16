#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(HyperTrackSdkPlugin, "HyperTrackSdk",
           CAP_PLUGIN_METHOD(enableDebugLogging, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAvailability, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setDeviceName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stop, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(start, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(allowMockLocations, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(syncDeviceSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setDeviceMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addGeotag, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addTrackingListener, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeTrackingListener, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addAvailabilityListener, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(removeAvailabilityListener, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
)
