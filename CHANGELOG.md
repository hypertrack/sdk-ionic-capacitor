# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [4.0.0] - 2025-02-03

### Changed

- Updated HyperTrack SDK iOS to [5.11.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.11.0)
- Updated HyperTrack SDK Android to [7.11.0](https://github.com/hypertrack/sdk-android/releases/tag/7.11.0)

## [3.8.0] - 2025-01-17

### Added

- New `HyperTrack.getAllowMockLocation()` and `HyperTrack.setAllowMockLocation()` methods which can be used to allow mocking location data.
  - Check the [Test with mock locations](https://hypertrack.com/docs/mock-location) guide for more information.
  - Note: To avoid issues related to race conditions in your code use this API **only if** modifying the compiled `HyperTrackAllowMockLocation` AndroidManifest.xml/Info.plist value is insufficient for your needs.
    - Example: if for some reason you aren't able to recompile with `HyperTrackAllowMockLocation` set to `YES`/`true` for your prod app QA mock location tests and need to set up the value in runtime.

### Changed

- Updated HyperTrack SDK iOS to [5.10.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.10.0)
- Updated HyperTrack SDK Android to [7.10.0](https://github.com/hypertrack/sdk-android/releases/tag/7.10.0)

## [3.7.1] - 2024-12-05

### Changed

- Updated HyperTrack SDK iOS to [5.9.1](https://github.com/hypertrack/sdk-ios/releases/tag/5.9.1)
- Updated HyperTrack SDK Android to [7.9.1](https://github.com/hypertrack/sdk-android/releases/tag/7.9.1)

## [3.7.0] - 2024-11-22

### Changed

- Updated HyperTrack SDK iOS to [5.9.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.9.0)
- Updated HyperTrack SDK Android to [7.9.0](https://github.com/hypertrack/sdk-android/releases/tag/7.9.0)

## [3.6.3] - 2024-11-13

### Changed

- Updated HyperTrack SDK iOS to [5.8.1](https://github.com/hypertrack/sdk-ios/releases/tag/5.8.1)
- Updated HyperTrack SDK Android to [7.8.4](https://github.com/hypertrack/sdk-android/releases/tag/7.8.4)

## [3.6.2] - 2024-09-24

### Changed

- Updated HyperTrack SDK Android to [7.8.3](https://github.com/hypertrack/sdk-android/releases/tag/7.8.3)

## [3.6.1] - 2024-09-18

### Changed

- Updated HyperTrack SDK Android to [7.8.2](https://github.com/hypertrack/sdk-android/releases/tag/7.8.2)

## [3.6.0] - 2024-09-04

### Added

- Support for Motion & Activity detection
  - If your app asks for the Motion & Activity permission (for iOS) or the Activity Recognition permission (for Android) and the user grants it, you will have better activity detection in polylines

### Changed

- Updated HyperTrack SDK iOS to [5.8.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.8.0)
- Updated HyperTrack SDK Android to [7.8.0](https://github.com/hypertrack/sdk-android/releases/tag/7.8.0)

## [3.5.1] - 2024-08-28

### Fixed

- Serialization bug for `Order.isInsideGeofence` success case on iOS

## [3.5.0] - 2024-08-22

### Added

- Support for on-device geofencing via new `HyperTrack.orders.get("my_order_handle").isInsideGeofence` property
  - To learn more about how to best use this new feature see our guide here: [Verify shift presence before starting work](https://developer.hypertrack.com/docs/clock-in-out-tagging#verify-shift-presence-before-starting-work)

Example use for worker clock in:

```typescript
// check worker presence synchronously
let activeOrders = await HyperTrack.getOrders()
let currentOrder = activeOrders.get("current_order")
if (currentOrder !== undefined) { handlePresence(currentOrder) }
else { console.log("'current_order' not found") }

// or subscribe to the changes in orders to get the status updates
HyperTrack.subscribeToOrders(orders => {
  let let currentOrder = activeOrders.get("current_order")
  if (currentOrder !== undefined) { handlePresence(currentOrder) }
  else { console.log("'current_order' not found") }
})

// handle worker presence inside the order destination geofence
function handlePresence(isInsideGeofence: Result<boolean, LocationError>) {
  switch (isInsideGeofence.type) {
    case 'success':
      if (isInsideGeofence.value) {
        // allow worker to clock in for the shift
      } else {
        // "to clock in you must be at order destination"
      }
      break;
    case 'failure':
      // resolve errors to check for presence
      break;
  }
}
```

### Changed

- Updated HyperTrack SDK iOS to [5.7.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.7.0)
- Updated HyperTrack SDK Android to [7.7.0](https://github.com/hypertrack/sdk-android/releases/tag/7.7.0)

## [3.4.0] - 2024-06-13

### Added

- New `setWorkerHandle` and `getWorkerHandle` can be used to identify workers
  - We observed our customers identify worker devices via `HyperTrack.metadata`, so we decided to make it a first class citizen in our API.
  - If you previously used `metadata` to identify workers, we suggest using `workerHandle` for this purpose instead.

### Changed

- Updated HyperTrack SDK iOS to [5.6.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.6.0)
- Updated HyperTrack SDK Android to [7.6.0](https://github.com/hypertrack/sdk-android/releases/tag/7.6.0)

## [3.3.1] - 2024-05-24

### Changed

- Updated HyperTrack SDK Android to [7.5.5](https://github.com/hypertrack/sdk-android/releases/tag/7.5.5)

## [3.3.0] - 2024-05-13

### Added

- Now the plugin takes into account project's `kotlin_version` to align Kotlin versions.

### Changed

- Default Kotlin version is updated to `1.9.10`
- Updated HyperTrack SDK iOS to [5.5.4](https://github.com/hypertrack/sdk-ios/releases/tag/5.5.4)
- Updated HyperTrack SDK Android to [7.5.4](https://github.com/hypertrack/sdk-android/releases/tag/7.5.4)

### Removed

- `@capacitor/core` item in `peerDependencies`

## [3.2.2] - 2024-05-03

### Changed

- Updated HyperTrack SDK iOS to [5.5.3](https://github.com/hypertrack/sdk-ios/releases/tag/5.5.3)

## [3.2.1] - 2024-04-23

### Changed

- Updated HyperTrack SDK iOS to [5.5.2](https://github.com/hypertrack/sdk-ios/releases/tag/5.5.2)
- Updated HyperTrack SDK Android to [7.5.3](https://github.com/hypertrack/sdk-android/releases/tag/7.5.3)

## [3.2.0] - 2024-04-19

### Added

- New `addGeotag` method that have `orderHandle` and `orderStatus` parameters. You can use this API when users need to clock in/out of work in your app to honor their work time (see [Clock in/out Tagging](https://hypertrack.com/docs/clock-inout-tracking#add-clock-inout-events-to-a-shift-timeline) guide for more info)

### Fixed

- Added `kotlinOptions.jvmTarget` to fix the build error when updating to Gradle 8 and setting `compileOptions.targetCompatibility` to `17`

### Changed

- Updated HyperTrack SDK iOS to [5.5.1](https://github.com/hypertrack/sdk-ios/releases/tag/5.5.1)
- Updated HyperTrack SDK Android to [7.5.2](https://github.com/hypertrack/sdk-android/releases/tag/7.5.2)

## [3.1.3] - 2024-02-26

### Changed

- Updated HyperTrack SDK Android to [7.4.3](https://github.com/hypertrack/sdk-android/releases/tag/7.4.3)

## [3.1.2] - 2024-02-15

### Changed

- Updated HyperTrack SDK iOS to [5.4.1](https://github.com/hypertrack/sdk-ios/releases/tag/5.4.1)
- Updated HyperTrack SDK Android to [7.4.2](https://github.com/hypertrack/sdk-android/releases/tag/7.4.2)

## [3.1.1] - 2024-02-12

### Changed

- Updated HyperTrack SDK iOS to [5.4.1](https://github.com/hypertrack/sdk-ios/releases/tag/5.4.1)
- Updated HyperTrack SDK Android to [7.4.1](https://github.com/hypertrack/sdk-android/releases/tag/7.4.1)

## [3.1.0] - 2024-01-31

### Changed

- Updated HyperTrack SDK iOS to [5.4.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.4.0)
- Updated HyperTrack SDK Android to [7.4.0](https://github.com/hypertrack/sdk-android/releases/tag/7.4.0)

## [3.0.2] - 2024-01-18

### Changed

- Updated HyperTrack SDK Android to [7.2.0](https://github.com/hypertrack/sdk-android/releases/tag/7.2.0)
- Updated HyperTrack SDK iOS to [5.2.0](https://github.com/hypertrack/sdk-ios/releases/tag/5.2.0)

### Fixed

- Set `@capacitor/core` version to `^5.0.0` in `peerDependencies` to fix compatibility with older 5.x.x versions

## [3.0.1] - 2023-12-20

### Fixed

- Wrong `@capacitor/core` version in `peerDependencies`

## [3.0.0] - 2023-12-20

### Changed

- Updated Ionic to v5
- Updated HyperTrack SDK Android to [7.0.11](https://github.com/hypertrack/sdk-android/releases/tag/7.0.11)
- Updated HyperTrack SDK iOS to [5.0.8](https://github.com/hypertrack/sdk-ios/releases/tag/5.0.8)

## [2.0.2] - 2023-11-23

### Changed

- Updated HyperTrack SDK Android to [7.0.9](https://github.com/hypertrack/sdk-android/releases/tag/7.0.9)
- Updated HyperTrack SDK iOS to [5.0.7](https://github.com/hypertrack/sdk-ios/releases/tag/5.0.7)

## [2.0.1] - 2023-11-10

### Changed

- Updated HyperTrack SDK Android to [7.0.8](https://github.com/hypertrack/sdk-android/releases/tag/7.0.7)
- Updated HyperTrack SDK iOS to [5.0.6](https://github.com/hypertrack/sdk-ios/releases/tag/5.0.5)

## [2.0.0] - 2023-10-27

We are excited to announce the release of HyperTrack Ionic Capacitor SDK 2.0.0, a major update to our location tracking SDK. This release ensures highest tracking performance, reduces deployed app sizes and comes with an improved API to simplify the integrations. We highly recommend upgrading, but please note that there are a few breaking changes.

### Changed

- Updated HyperTrack SDK Android to [7.0.6](https://github.com/hypertrack/sdk-android/releases/tag/7.0.6)
- Added Android SDK plugins (`location-services-google` and `push-service-firebase`)
- Updated HyperTrack SDK iOS to [5.0.4](https://github.com/hypertrack/sdk-ios/releases/tag/5.0.4)
- The whole HyperTrack API is now static
- Changed the way to provide publishableKey (you need to add `HyperTrackPublishableKey` `meta-data` item to your `AndroidManifest.xml`)
- Renamed HyperTrackError types:
  - `gpsSignalLost` to `locationSignalLost`
  - `locationPermissionsDenied` to `permissionsLocationDenied`
  - `locationPermissionsInsufficientForBackground` to `permissionsLocationInsufficientForBackground`
  - `locationPermissionsNotDetermined` to `permissionsLocationNotDetermined`
  - `locationPermissionsProvisional` to `locationPermissionsProvisional`
  - `locationPermissionsReducedAccuracy` to `permissionsLocationReducedAccuracy`
  - `locationPermissionsRestricted` to `permissionsLocationRestricted`
- Renamed `isAvailable()` to `getIsAvailable()`
- Renamed `isTracking()` to `getIsTracking()`
- Renamed `setAvailability()` to `setIsAvailable(boolean)`
- Changed `startTracking()` and `stopTracking()` to `setIsTracking(boolean)`
- Renamed `subscribeToTracking()` to `subscribeToIsTracking()`
- Renamed `subscribeToAvailability()` to `subscribeToIsAvailable()`
- Changed `getLocation()` response to `Result<Location, LocationError>`

### Added

- `locate()` to ask for one-time user location
- `subscribeToLocation()` to subscribe to user location updates
- `getErrors()`
- `getName()`
- `getMetadata()`
- HyperTrackError types:
  - `noExemptionFromBackgroundStartRestrictions`
  - `permissionsNotificationsDenied`

### Removed

- `initialize()` method (the API is now static)
- `SdkInitParams` (the config now should be done with the `AndroidManifest` metadata and `Info.plist`)
- Motion Activity permissions are not required for tracking anymore
- HyperTrackError types:
  - `motionActivityPermissionsDenied`
  - `motionActivityServicesDisabled`
  - `motionActivityServicesUnavailable`
  - `motionActivityPermissionsRestricted`
  - `networkConnectionUnavailable`
- `sync()` method

## [1.0.3] - 2023-06-16

### Changed

- Updated HyperTrack SDK iOS to 4.16.1

## [1.0.2] - 2023-06-15

### Changed

- HyperTrack SDK Android updated to 6.4.2

## [1.0.1] - 2023-06-01

### Changed

- HyperTrack SDK iOS updated to 4.16.0

## [1.0.0] - 2023-02-10

### Added

- Initial release

[1.0.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.0
[1.0.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.1
[1.0.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.2
[1.0.3]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.3
[2.0.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/2.0.0
[2.0.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/2.0.1
[2.0.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/2.0.2
[3.0.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.0.0
[3.0.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.0.1
[3.0.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.0.2
[3.1.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.1.0
[3.1.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.1.1
[3.1.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.1.2
[3.1.3]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.1.3
[3.2.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.2.0
[3.2.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.2.1
[3.2.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.2.2
[3.3.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.3.0
[3.3.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.3.1
[3.4.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.4.0
[3.5.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.5.0
[3.5.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.5.1
[3.6.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.6.0
[3.6.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.6.1
[3.6.2]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.6.2
[3.6.3]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.6.3
[3.7.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.7.0
[3.7.1]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.7.1
[3.8.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/3.8.0
[4.0.0]: https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/4.0.0
