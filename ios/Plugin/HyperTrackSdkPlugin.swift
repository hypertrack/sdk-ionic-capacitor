import Foundation
import Capacitor
import HyperTrack
/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(HyperTrackSdkPlugin)
public class HyperTrackSdkPlugin: CAPPlugin {
    private let implementation = HyperTrackSdk()
    
    private var startedTrackingNotificationObserver: Any? = nil
    private var stoppedTrackingNotificationObserver: Any? = nil
    private var didEncounterUnrestorableErrorNotificationObserver: Any? = nil
    private var didEncounterRestorableErrorNotificationObserver: Any? = nil
    private var becameAvailableNotificationObserver: Any? = nil
    private var becameUnavailableNotificationObserver: Any? = nil
    
    @objc func addTrackingListener() {
        startedTrackingNotificationObserver = NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.startTracking),
            name: HyperTrack.startedTrackingNotification,
            object: nil)
        
        stoppedTrackingNotificationObserver =  NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.stopTracking),
            name: HyperTrack.stoppedTrackingNotification,
            object: nil
        )
        
        didEncounterUnrestorableErrorNotificationObserver = NotificationCenter.default.addObserver(
            self,
            selector: #selector(trackingError(notification:)),
            name: HyperTrack.didEncounterUnrestorableErrorNotification,
            object: nil
        )
        didEncounterRestorableErrorNotificationObserver = NotificationCenter.default.addObserver(
            self,
            selector: #selector(trackingError(notification:)),
            name: HyperTrack.didEncounterRestorableErrorNotification,
            object: nil
        )
    }
    
    @objc func removeTrackingListener() {
        NotificationCenter.default.removeObserver(startedTrackingNotificationObserver!)
        NotificationCenter.default.removeObserver(stoppedTrackingNotificationObserver!)
        NotificationCenter.default.removeObserver(didEncounterUnrestorableErrorNotificationObserver!)
        NotificationCenter.default.removeObserver(didEncounterRestorableErrorNotificationObserver!)
    }
    
    @objc func addAvailabilityListener() {
        becameAvailableNotificationObserver = NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.available),
            name: HyperTrack.becameAvailableNotification,
            object: nil)
        
        becameUnavailableNotificationObserver =  NotificationCenter.default.addObserver(
            self,
            selector: #selector(self.unavailable),
            name: HyperTrack.becameUnavailableNotification,
            object: nil
        )
    }
    
    @objc func removeAvailabilityListener() {
        NotificationCenter.default.removeObserver(becameAvailableNotificationObserver!)
        NotificationCenter.default.removeObserver(becameUnavailableNotificationObserver!)
    }
    
    @objc private func trackingError(notification: Notification) {
        if let trackingError = notification.hyperTrackTrackingError() {
            if let restorableError = trackingError.restorableError {
                notifyListeners("trackingStateChange", data: ["error" : restorableError.localizedDescription])
            }
            if let unrestorableError = trackingError.unrestorableError {
                notifyListeners("trackingStateChange", data: ["error" : unrestorableError.localizedDescription])
            }
        }
    }
    
    @objc private func startTracking() {
        notifyListeners("trackingStateChange", data: ["status" : "start"])
    }
    
    @objc private func stopTracking() {
        notifyListeners("trackingStateChange", data: ["status" : "stop"])
    }
    
    @objc private func available() {
        notifyListeners("availabilityStateChange", data: ["availability" : true])
    }
    
    @objc private func unavailable() {
        notifyListeners("availabilityStateChange", data: ["availability" : false])
    }
    
    @objc func initialize(_ call: CAPPluginCall) {
        let publishableKey = call.getString("publishableKey") ?? ""
        do {
            try implementation.initialize(publishableKey: publishableKey)
            call.resolve()
        } catch let error as NSError {
            call.reject(error.localizedDescription, nil, error)
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func addGeotag(_ call: CAPPluginCall) {
        let tag = call.getString("metadata") ?? ""
        do {
            try implementation.addGeotag(tag: tag)
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func setDeviceMetadata(_ call: CAPPluginCall) {
        let metaData = call.getString("metaData") ?? ""
        do {
            try implementation.setDeviceMetadata(metaData: metaData)
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func isTracking(_ call: CAPPluginCall) {
        do {
            let status = try implementation.isTracking()
            call.resolve(["status":status])
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func syncDeviceSettings(_ call: CAPPluginCall) {
        do {
            try implementation.syncDeviceSettings()
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func allowMockLocations(_ call: CAPPluginCall) {
        do {
            try implementation.allowMockLocations()
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func start(_ call: CAPPluginCall) {
        do {
            try implementation.start()
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func stop(_ call: CAPPluginCall) {
        do {
            try implementation.stop()
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func setDeviceName(_ call: CAPPluginCall) {
        let deviceName = call.getString("name") ?? ""
        do {
            try implementation.setDeviceName(deviceName: deviceName)
            call.resolve()
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func getAvailability(_ call: CAPPluginCall) {
        do {
            let status = try implementation.availability()
            call.resolve(["status":status])
        } catch {
            call.reject(error.localizedDescription, nil, error)
        }
    }
    
    @objc func enableDebugLogging(_ call: CAPPluginCall) {
        implementation.enableDebugLogging()
        call.resolve()
    }
}
