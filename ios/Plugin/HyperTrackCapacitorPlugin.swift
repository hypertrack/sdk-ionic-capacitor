import Capacitor
import Foundation
import HyperTrack

/// HyperTrack Capacitor Plugin
///
/// The HypertrackSdkIonicCapacitor object name is generated from the JS package name
/// of the plugin.
@objc(HyperTrackCapacitorPlugin)
public class HyperTrackCapacitorPlugin: CAPPlugin {
    private let eventErrors = "errors"
    private let eventIsTracking = "isTracking"
    private let eventIsAvailable = "isAvailable"
    private let eventLocate = "locate"
    private let eventLocation = "location"

    private var errorsSubscription: HyperTrack.Cancellable!
    private var isTrackingSubscription: HyperTrack.Cancellable!
    private var isAvailableSubscription: HyperTrack.Cancellable!
    private var locationSubscription: HyperTrack.Cancellable!

    private var locateSubscription: HyperTrack.Cancellable? = nil
    
    required override init(bridge: CAPBridgeProtocol, pluginId: String, pluginName: String) {
        super.init(bridge: bridge, pluginId: pluginId, pluginName: pluginName)
        initListeners()
    }

    @objc func addGeotag(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.addGeotag(
                call.options as! [String: Any]
            ),
            method: .addGeotag,
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
                call.options as! [String: Any]
            ),
            method: .setAvailability,
            call
        )
    }

    @objc func setName(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setName(
                call.options as! [String: Any]
            ),
            method: .setName,
            call
        )
    }

    @objc func setMetadata(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setMetadata(
                call.options as! [String: Any]
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

    @objc func onSubscribedToErrors(_: CAPPluginCall) {
        sendErrorsEvent(HyperTrack.errors)
    }

    @objc func onSubscribedToIsTracking(_: CAPPluginCall) {
        sendIsTrackingEvent(isTracking: HyperTrack.isTracking)
    }

    @objc func onSubscribedToIsAvailable(_: CAPPluginCall) {
        sendIsAvailableEvent(availability: HyperTrack.isAvailable)
    }

    @objc func onSubscribedToLocate(_: CAPPluginCall) {
        locateSubscription?.cancel()
        locateSubscription = HyperTrack.subscribeToLocate { locateResult in
            sendLocateEvent(locateResult)
        }
    }

    @objc func onSubscribedToLocation(_: CAPPluginCall) {
        sendLocationEvent(HyperTrack.location)
    }

    private func initListeners() {
        errorsSubscription = HyperTrack.subscribeToErrors { errors in
            self.sendErrorsEvent(errors)
        }
        isTrackingSubscription = HyperTrack.subscribeToIsTracking(callback: { isTracking in
            self.sendIsTrackingEvent(isTracking: isTracking)
        })
        isAvailableSubscription = HyperTrack.subscribeToAvailability(callback: { availability in
            self.sendIsAvailableEvent(availability: availability)
        })
        locationSubscription = HyperTrack.subscribeToLocation { location in
            self.sendLocationEvent(location)
        }
    }

    private func sendIsTrackingEvent(isTracking: Bool) {
        notifyListeners(eventTracking, data: serializeIsTracking(isTracking))
    }

    private func sendIsAvailableEvent(availability: HyperTrack.Availability) {
        notifyListeners(eventAvailability, data: serializeIsAvailable(availability))
    }

    private func sendErrorsEvent(_ errors: Set<HyperTrack.HyperTrackError>) {
        notifyListeners(eventErrors, data: serializeErrorsForPlugin(serializeErrors(errors)))
    }

    private func serializeErrorsForPlugin(_ errors: [[String: Any]]) -> [String: Any] {
        return ["errors": errors]
    }
}

private func sendAsPromise(
    _ result: Result<SuccessResult, FailureResult>,
    method: SDKMethod,
    _ call: CAPPluginCall
) {
    switch result {
    case let .success(success):
        switch success {
        case .void:
            call.resolve([:])
        case let .dict(value):
            call.resolve(value)
        }
    case let .failure(failure):
        switch failure {
        case let .error(message):
            call.reject(
                "\(method.rawValue): \(message)",
                nil,
                nil
            )
        case let .fatalError(message):
            preconditionFailure(message)
        }
    }
}
