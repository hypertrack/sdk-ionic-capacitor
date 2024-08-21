#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(HyperTrackCapacitorPlugin, "HyperTrackCapacitorPlugin",
           CAP_PLUGIN_METHOD(addGeotag, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getDeviceId, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getErrors, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getIsAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getIsTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getLocation, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getOrders, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getWorkerHandle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToErrors, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToIsAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToIsTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToLocate, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToLocation, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSubscribedToOrders, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setIsAvailable, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setIsTracking, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setMetadata, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setName, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setWorkerHandle, CAPPluginReturnPromise);
)
