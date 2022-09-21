import Foundation
import HyperTrack

@objc public class HyperTrackSdk: NSObject {
    public var hyperTrackInstance: HyperTrack? = nil
    public let TAG = "Hypertrack Plugin"
    
    enum HTError: Error {
        case required(String)
        case null(String)
        case notInitialized(String)
    }
    
    public func initialize(publishableKey: String) throws -> Void {
        printLog(log: publishableKey)
        if publishableKey.isEmpty {
            throw HTError.required("publishableKey is required.")
        } else {
            let hypertrackPublishableKey = HyperTrack.PublishableKey(publishableKey)!
            let hyperTrack = HyperTrack.makeSDK(publishableKey:hypertrackPublishableKey)
            switch hyperTrack {
            case let .success(hyperTrack):
                hyperTrackInstance = hyperTrack
            case let .failure(fatalError):
                printLog(log: fatalError.localizedDescription)
                throw fatalError
            }
        }
    }
    
    public func addGeotag(tag:String) throws -> Void {
        printLog(log: tag)
        if let sdkInstance = hyperTrackInstance {
            if let eventMarker = HyperTrack.Metadata(rawValue:convertToDictionary(text: tag)!) {
                sdkInstance.addGeotag(eventMarker)
            } else {
                throw HTError.required("tag is required in [String:Any] format")
            }
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func setDeviceMetadata(metaData:String) throws -> Void {
        printLog(log: metaData)
        if let sdkInstance = hyperTrackInstance {
            if let metaDataDictionary = HyperTrack.Metadata(rawValue:convertToDictionary(text: metaData)!) {
                sdkInstance.setDeviceMetadata(metaDataDictionary)
            } else {
                throw HTError.required("Metadata is required in [String:Any] format")
            }
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func isTracking() throws -> Bool {
        if let sdkInstance = hyperTrackInstance {
            return sdkInstance.isTracking
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    func getDeviceId() throws -> String {
        if let sdkInstance = hyperTrackInstance {
            return sdkInstance.deviceID
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func syncDeviceSettings() throws -> Void {
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.syncDeviceSettings()
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func allowMockLocations() throws -> Void {
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.mockLocationsAllowed = true
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func start() throws -> Void {
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.start()
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func stop() throws -> Void {
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.stop()
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func setDeviceName(deviceName: String) throws -> Void {
        printLog(log: deviceName)
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.setDeviceName(deviceName)
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func availability() throws -> HyperTrack.Availability {
        if let sdkInstance = hyperTrackInstance {
            return sdkInstance.availability
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func setAvailability(isAvailable:Bool) throws -> Void {
        if let sdkInstance = hyperTrackInstance {
            sdkInstance.availability = (isAvailable == true) ? HyperTrack.Availability.available : HyperTrack.Availability.unavailable
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func isRunning() throws -> Bool {
        if let sdkInstance = hyperTrackInstance {
            return sdkInstance.isRunning;
        } else {
            throw HTError.notInitialized("Sdk wasn't initialized.")
        }
    }
    
    public func enableDebugLogging() -> Void {
        HyperTrack.isLoggingEnabled = true
    }
    
    private func convertToDictionary(text: String) -> [String: Any]? {
        if let data = text.data(using: .utf8) {
            do {
                return try JSONSerialization.jsonObject(with: data, options: []) as? [String: Any]
            } catch {
                printLog(log:error.localizedDescription)
            }
        }
        return nil
    }
    
    public func printLog(log:String) {
        print(TAG+" "+log)
    }
}
