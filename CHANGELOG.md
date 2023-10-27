# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2023-10-27

We are excited to announce the release of HyperTrack Ionic Capacitor SDK 2.0.0, a major update to our location tracking SDK. This release ensures highest tracking performance, reduces deployed app sizes and comes with an improved API to simplify the integrations. We highly recommend upgrading, but please note that there are a few breaking changes.

### Changed

- Updated HyperTrack Android SDK to [7.0.6](https://github.com/hypertrack/sdk-android/releases/tag/7.0.1)
- Added Android SDK plugins (`location-services-google` and `push-service-firebase`)
- Updated HyperTrack iOS SDK to [5.0.4](https://github.com/hypertrack/sdk-ios/releases/tag/5.0.4)
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

- Updated HyperTrack iOS SDK to 4.16.1

## [1.0.2] - 2023-06-15

### Changed

- HyperTrack SDK Android updated to 6.4.2

## [1.0.1] - 2023-06-01

### Changed

- HyperTrack SDK iOS updated to 4.16.0

## [1.0.0] - 2023-02-10

### Added

- Initial release

[2.0.0] https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/2.0.0
[1.0.3] https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.3
[1.0.2] https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.2
[1.0.1] https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.1
[1.0.0] https://github.com/hypertrack/sdk-ionic-capacitor/releases/tag/1.0.0
