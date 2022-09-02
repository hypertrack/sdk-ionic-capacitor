#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(HyperTrackCapacitorPlugin, "HyperTrackCapacitorPlugin",
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDeviceId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getLocation, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setAvailability, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(addGeotag, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(sync, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToAvailability, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToErrors, CAPPluginReturnPromise);
)
