import Foundation
import Capacitor
import HyperTrack

@objc(HyperTrackCapacitorPlugin)
public class HyperTrackCapacitorPlugin: CAPPlugin {
    
    private let eventTracking = "onTrackingChanged"
    private let eventAvailability = "onAvailabilityChanged"
    private let eventErrors = "onError"
    
    private var isTrackingSubscription: HyperTrack.Cancellable!
    private var availabilitySubscription: HyperTrack.Cancellable!
    private var errorsSubscription: HyperTrack.Cancellable!
    
    @objc func initialize(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.initializeSDK(
                call.options as! Dictionary<String, Any>
            ).map({ (result:SuccessResult) in
                initListeners()
                return result
            }),
            method: .initialize,
            call
        )
    }
    
    @objc func getDeviceId(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getDeviceID(),
            method: .getDeviceID,
            call
        )
    }
    
    @objc func getLocation(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getLocation(),
            method: .getLocation,
            call
        )
    }
    
    @objc func startTracking(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.startTracking(),
            method: .startTracking,
            call
        )
    }
    
    @objc func stopTracking(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.stopTracking(),
            method: .stopTracking,
            call
        )
    }
    
    @objc func setAvailability(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setAvailability(
                call.options as! Dictionary<String, Any>
            ),
            method: .setAvailability,
            call
        )
    }
    
    @objc func setName(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setName(
                call.options as! Dictionary<String, Any>
            ),
            method: .setName,
            call
        )
    }
    
    @objc func setMetadata(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setMetadata(
                call.options as! Dictionary<String, Any>
            ),
            method: .setMetadata,
            call
        )
    }
    
    @objc func isTracking(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.isTracking(),
            method: .isTracking,
            call
        )
    }
    
    @objc func isAvailable(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.isAvailable(),
            method: .isAvailable,
            call
        )
    }
    
    @objc func addGeotag(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.addGeotag(
                call.options as! Dictionary<String, Any>
            ),
            method: .addGeotag,
            call
        )
    }
    
    @objc func sync(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.sync(),
            method: .sync,
            call
        )
    }
    
    @objc func onSubscribedToTracking(_ call: CAPPluginCall) {
        sendTrackingEvent(isTracking: sdkInstance.isTracking)
    }
    
    @objc func onSubscribedToAvailability(_ call: CAPPluginCall) {
        sendAvailabilityEvent(availability: sdkInstance.availability)
    }
    
    @objc func onSubscribedToErrors(_ call: CAPPluginCall) {
        sendErrorsEvent(sdkInstance.errors)
    }
    
    private func initListeners() {
        isTrackingSubscription = sdkInstance.subscribeToIsTracking(callback: { isTracking in
            self.sendTrackingEvent(isTracking: isTracking)
        })
        availabilitySubscription = sdkInstance.subscribeToAvailability(callback: { availability in
            self.sendAvailabilityEvent(availability: availability)
        })
        errorsSubscription = sdkInstance.subscribeToErrors { errors in
            self.sendErrorsEvent(errors)
        }
    }
    
    private func sendTrackingEvent(isTracking: Bool) {
        notifyListeners(eventTracking, data: serializeIsTracking(isTracking))
    }
    private func sendAvailabilityEvent(availability: HyperTrack.Availability) {
        notifyListeners(eventAvailability, data: serializeIsAvailable(availability))
    }
    
    private func sendErrorsEvent(_ errors: Set<HyperTrack.HyperTrackError>) {
        notifyListeners(eventErrors, data: serializeErrorsForPlugin(serializeErrors(errors)))
    }
                                            
    private func serializeErrorsForPlugin(_ errors: Array<Dictionary<String, Any>>) -> Dictionary<String, Any> {
        return ["errors": errors]
    }
    
}

private func sendAsPromise(
    _ result: Result<SuccessResult, FailureResult>,
    method: SDKMethod,
    _ call: CAPPluginCall
) {
    switch result {
    case .success(let success):
        switch (success) {
        case .void:
            call.resolve([:])
        case .dict(let value):
            call.resolve(value)
        }
    case .failure(let failure):
        switch(failure) {
        case .error(let message):
            call.reject(
                "\(method.rawValue): \(message)",
                nil,
                nil
            )
        case .fatalError(let message):
            preconditionFailure(message)
        }
    }
}
