import Capacitor
import Foundation
import HyperTrack

/// HyperTrack Capacitor Plugin
///
/// The HypertrackSdkIonicCapacitor is a Swift module name to access HyperTrackSDKWrapper
/// methods.
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
    
    override public func load() {
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

    @objc func getErrors(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getErrors().map { errors in
                switch errors {
                case .void:
                    preconditionFailure("Unexpected void result")
                case let .dict(value):
                    preconditionFailure("Unexpected dict result: \(value)")
                case let .array(errors):
                    return .dict(serializeErrorsForPlugin(errors as! [[String: Any]]))
                }
            },
            method: .getErrors,
            call
        )
    }

    @objc func getIsAvailable(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getIsAvailable(),
            method: .getIsAvailable,
            call
        )
    }

    @objc func getIsTracking(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getIsTracking(),
            method: .getIsTracking,
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

    @objc func getMetadata(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getMetadata(),
            method: .getMetadata,
            call
        )
    }

    @objc func getName(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.getName(),
            method: .getName,
            call
        )
    }

    @objc func setIsAvailable(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setIsAvailable(
                call.options as! [String: Any]
            ),
            method: .setIsAvailable,
            call
        )
    }

    @objc func setIsTracking(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setIsTracking(
                call.options as! [String: Any]
            ),
            method: .setIsTracking,
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

    @objc func setName(_ call: CAPPluginCall) {
        sendAsPromise(
            HypertrackSdkIonicCapacitor.setName(
                call.options as! [String: Any]
            ),
            method: .setName,
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
        sendIsAvailableEvent(isAvailable: HyperTrack.isAvailable)
    }

    @objc func onSubscribedToLocate(_: CAPPluginCall) {
        locateSubscription?.cancel()
        locateSubscription = HyperTrack.locate { locateResult in
            self.sendLocateEvent(locateResult)
        }
    }

    @objc func onSubscribedToLocation(_: CAPPluginCall) {
        sendLocationEvent(HyperTrack.location)
    }

    private func initListeners() {
        errorsSubscription = HyperTrack.subscribeToErrors { errors in
            self.sendErrorsEvent(errors)
        }
        isAvailableSubscription = HyperTrack.subscribeToIsAvailable { isAvailable in
            self.sendIsAvailableEvent(isAvailable: isAvailable)
        }
        isTrackingSubscription = HyperTrack.subscribeToIsTracking { isTracking in
            self.sendIsTrackingEvent(isTracking: isTracking)
        }
        locationSubscription = HyperTrack.subscribeToLocation { location in
            self.sendLocationEvent(location)
        }
    }

    private func sendErrorsEvent(_ errors: Set<HyperTrack.Error>) {
        notifyListeners(eventErrors, data: serializeErrorsForPlugin(serializeErrors(errors)))
    }

    private func sendIsAvailableEvent(isAvailable: Bool) {
        notifyListeners(eventIsAvailable, data: serializeIsAvailable(isAvailable))
    }

    private func sendIsTrackingEvent(isTracking: Bool) {
        notifyListeners(eventIsTracking, data: serializeIsTracking(isTracking))
    }

    private func sendLocateEvent(_ locateResult: Result<HyperTrack.Location, Set<HyperTrack.Error>>) {
        notifyListeners(eventLocate, data: serializeLocateResult(locateResult))
    }

    private func sendLocationEvent(_ locationResult: Result<HyperTrack.Location, HyperTrack.Location.Error>) {
        notifyListeners(eventLocation, data: serializeLocationResult(locationResult))
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
        case .array:
            preconditionFailure("Arrays params are not supported in Capacitor")
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
