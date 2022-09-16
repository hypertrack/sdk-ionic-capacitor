# hypertrack-capacitor-plugin

![GitHub](https://img.shields.io/github/license/hypertrack/quickstart-ios.svg)
![Android SDK](https://img.shields.io/badge/Android%20SDK-6.2.2-brightgreen.svg)
[![iOS SDK](https://img.shields.io/badge/iOS%20SDK-4.12.4-brightgreen.svg)](https://cocoapods.org/pods/HyperTrack)

The hypertrack-capacitor-plugin provides simple methods for manage devices and trips by using HyperTrack SDK.

## Install global dependencies
The base requirements are [Node](https://nodejs.org/en/) v8.6.0 or later, and NPM version 5.6.0 or later (which is usually automatically installed with the required version of Node).

```
npm install -g @capacitor/core @capacitor/cli
```

## Build plugin

```bash
clone the repo git@github.com:hypertrack/sdk-ionic-capacitor.git
cd sdk-ionic-capacitor/
npm i
npm run verify:android
npm run build
```

## Add plugin to Ionic app as a local dependency

```bash
cd ionic-app/
npm i <local-dir>/sdk-ionic-capacitor
npx cap sync
```
## iOS

Apple requires privacy descriptions and background modes to be specified in `Info.plist` for location information:

- `NSLocationAlwaysAndWhenInUseUsageDescription` (`Privacy - Location Always And When In Use Usage Description`)
- `NSLocationWhenInUseUsageDescription` (`Privacy - Location When In Use Usage Description`)
- `NSMotionUsageDescription` (`Privacy - Motion Usage Description`)
- `UIBackgroundModes` 
  * location
  * remote-notification

Read about [Configuring `Info.plist`](https://capacitorjs.com/docs/ios/configuration#configuring-infoplist) in the [iOS Guide](https://capacitorjs.com/docs/ios) for more information on setting iOS permissions in Xcode

## Android
In order to use this plugin, please update the Gradle, AndroidManifest.xml:

In ```../android/build.gradle``` update

```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            name 'hypertrack'
            url  'https://s3-us-west-2.amazonaws.com/m2.hypertrack.com/'
        }
    }
}
```

In ```../android/app/src/main/AndroidManifest.xml``` update ```android:exported="true"```

```
 <activity
      android:configChanges="orientation|keyboardHidden|keyboard|screenSize|locale|smallestScreenSize|screenLayout|uiMode"
      android:name="io.ionic.starter.MainActivity"
      android:label="@string/title_activity_main"
      android:theme="@style/AppTheme.NoActionBarLaunch"
      android:launchMode="singleTask"
      android:exported="true">

      <intent-filter>
          <action android:name="android.intent.action.MAIN" />
          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
</activity>
```
### Variables

This plugin will use the following project variables (defined in your app's `variables.gradle` file):

- `minSdkVersion = 24`
- `compileSdkVersion = 31`
- `targetSdkVersion = 31`

## Example

```typescript
import { HyperTrack } from 'hypertrack-capacitor-plugin'

const getHyperTrackInstance = async () => {
  try {
      const result = await HyperTrack.ininitialize('YOUR-PUBLISHABLE-KEY-HERE');
      console.log(result.hyperTrackInstance);
  } catch (error) {
      console.log(error);
  }
};
```

## API

* [`enableDebugLogging()`](#enabledebuglogging)
* [`initialize(...)`](#initialize)
* [`start()`](#start)
* [`stop()`](#stop)
* [`requestPermissionsIfNecessary()`](#requestpermissionsifnecessary)
* [`allowMockLocations()`](#allowmocklocations)
* [`getDeviceId()`](#getdeviceid)
* [`getAvailability()`](#getavailability)
* [`setAvailability(...)`](#setavailability)
* [`isTracking()`](#istracking)
* [`addTrackingListener()`](#addtrackinglistener)
* [`removeTrackingListener()`](#removetrackinglistener)
* [`addAvailabilityListener()`](#addavailabilitylistener)
* [`removeAvailabilityListener()`](#removeavailabilitylistener)
* [`syncDeviceSettings()`](#syncdevicesettings)
* [`setDeviceName(...)`](#setdevicename)
* [`addGeotag(...)`](#addgeotag)
* [`setDeviceMetadata(...)`](#setdevicemetadata)
* [`setTrackingNotificationProperties(...)`](#settrackingnotificationproperties)
* [`getBlockers()`](#getblockers)
* [`resolveBlocker(...)`](#resolveblocker)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [`addListener('trackingStateChange', ...)`](#addlistenertrackingstatechange)
* [`addListener('availabilityStateChange', ...)`](#addlisteneravailabilitystatechange)

### enableDebugLogging()

```typescript
enableDebugLogging() => Promise<void>
```

Enables debug log in native HyperTrack SDK.

--------------------


### initialize(...)

```typescript
initialize(options: { publishableKey: string }) => Promise<HyperTrackSdkInstance>
```

Entry point into SDK.

Initializes SDK. Also resolves SDK instance that could be used to query deviceId or set
various data.

| Param         | Type                                     | Description                                                                              |
| ------------- | ---------------------------------------- | ---------------------------------------------------------------------------------------- |
| **`options`** | { publishableKey: string } | key-value pais of publishableKey, account-specific secret from the HyperTrack dashborad. |

**Returns:** <code>Promise&lt;<a href="#hypertracksdkinstance">HyperTrackSdkInstance</a>&gt;</code>

--------------------


### start()

```typescript
start() => Promise<void>
```

Start tracking.

--------------------


### stop()

```typescript
stop() => Promise<void>
```

Stop tracking.

--------------------


### requestPermissionsIfNecessary()

```typescript
requestPermissionsIfNecessary() => Promise<void>
```

Pops up permission request dialog, if permissions weren't granted before or does nothing otherwise.

--------------------


### allowMockLocations()

```typescript
allowMockLocations() => Promise<void>
```

Allows injecting false locations into the SDK, which ignores them by default.

--------------------


### getDeviceId()

```typescript
getDeviceId() => Promise<{ deviceId: string; }>
```

Resolves device ID that could be used to identify the device.

**Returns:** <code>Promise&lt;{ deviceId: string; }&gt;</code>

--------------------


### getAvailability()

```typescript
getAvailability() => Promise<{ status: string; }>
```

Resolves device's availability for nearby search.

**Returns:** <code>Promise&lt;{ status: string; }&gt;</code>

--------------------


### setAvailability(...)

```typescript
setAvailability(options: { isAvailable: boolean; }) => Promise<void>
```

Sets device's availability for nearby search.

| Param         | Type                                   |
| ------------- | -------------------------------------- |
| **`options`** | <code>{ isAvailable: boolean; }</code> |

--------------------


### isTracking()

```typescript
isTracking() => Promise<{ status: boolean; }>
```

Reflects tracking intent.

**Returns:** <code>Promise&lt;{ status: boolean; }&gt;</code>

--------------------


### addTrackingListener()

```typescript
addTrackingListener() => Promise<void>
```

Allows tracking hypertrack sdk  listener.

--------------------


### removeTrackingListener()

```typescript
removeTrackingListener() => Promise<void>
```

Stops tracking hypertrack sdk  listener.

--------------------


### addAvailabilityListener()

```typescript
addAvailabilityListener() => Promise<void>
```

Allows availability hypertrack sdk listener.

--------------------


### removeAvailabilityListener()

```typescript
removeAvailabilityListener() => Promise<void>
```

Stops availability hypertrack sdk listener.

--------------------


### syncDeviceSettings()

```typescript
syncDeviceSettings() => Promise<void>
```

Synchronizes tracking state with platform model. This method is used to
harden platform2device communication channel.

--------------------


### setDeviceName(...)

```typescript
setDeviceName(options: { name: string; }) => Promise<void>
```

Set device name

| Param         | Type                           | Description                        |
| ------------- | ------------------------------ | ---------------------------------- |
| **`options`** | <code>{ name: string; }</code> | key-value pais of name properties. |

--------------------


### addGeotag(...)

```typescript
addGeotag(options: { metadata: Record<string, unknown>; coordinates?: { latitude: number; longitude: number; }; }) => Promise<void>
```

Adds special marker-like object to device timeline.

| Param         | Type                                                                                                                                    | Description                                          |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------- |
| **`options`** | <code>{ metadata: <a href="#record">Record</a>&lt;string, unknown&gt;; coordinates?: { latitude: number; longitude: number; }; }</code> | key-value pais of metadata & coordinates properties. |

--------------------


### setDeviceMetadata(...)

```typescript
setDeviceMetadata(options: Record<string, unknown>) => Promise<void>
```

Use this to set additional properties, like segments, teams etc.

| Param         | Type                                                             | Description                   |
| ------------- | ---------------------------------------------------------------- | ----------------------------- |
| **`options`** | <code><a href="#record">Record</a>&lt;string, unknown&gt;</code> | key-value pais of properties. |

--------------------


### setTrackingNotificationProperties(...)

```typescript
setTrackingNotificationProperties(options: { title: string; message: string; }) => Promise<void>
```

Updates title and text in persistent notification, that appears when tracking is active.

| Param         | Type                                             | Description                                   |
| ------------- | ------------------------------------------------ | --------------------------------------------- |
| **`options`** | <code>{ title: string; message: string; }</code> | key-value pais of title & message properties. |

--------------------


### addListener('trackingStateChange', ...)

```typescript
addListener(eventName: 'trackingStateChange', listenerFunc: StateChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Adds listener for tracking state change

| Param              | Type                                                                |
| ------------------ | ------------------------------------------------------------------- |
| **`eventName`**    | <code>'trackingStateChange'</code>                                  |
| **`listenerFunc`** | <code><a href="#statechangelistener">StateChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('availabilityStateChange', ...)

```typescript
addListener(eventName: 'availabilityStateChange', listenerFunc: StateChangeListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Adds listener for availability state change

| Param              | Type                                                                |
| ------------------ | ------------------------------------------------------------------- |
| **`eventName`**    | <code>'availabilityStateChange'</code>                              |
| **`listenerFunc`** | <code><a href="#statechangelistener">StateChangeListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### getBlockers()

```typescript
getBlockers() => Promise<{ blockers: Blocker[]; }>
```

A blocker is an obstacle that needs to be resolved to achieve reliable tracking.

**Returns:** <code>Promise&lt;{ blockers: Blocker[]; }&gt;</code>

--------------------


### resolveBlocker(...)

```typescript
resolveBlocker(options: { code: string; }) => Promise<void>
```

An action that navigates user to the dedicated settings menu.

| Param         | Type                           |
| ------------- | ------------------------------ |
| **`options`** | <code>{ code: string; }</code> |

--------------------


### Interfaces


#### HyperTrackSdkInstance

| Method                                | Signature                                                                                                                                                                                                                                             | Description                                                                                                           |
| ------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| **start**                             | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Start tracking.                                                                                                       |
| **stop**                              | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Stop tracking.                                                                                                        |
| **requestPermissionsIfNecessary**     | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Pops up permission request dialog, if permissions weren't granted before or does nothing otherwise.                   |
| **allowMockLocations**                | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Allows injecting false locations into the SDK, which ignores them by default.                                         |
| **getDeviceId**                       | () =&gt; Promise&lt;{ deviceId: string; }&gt;                                                                                                                                                                                                         | Resolves device ID that could be used to identify the device.                                                         |
| **getAvailability**                   | () =&gt; Promise&lt;{ status: string; }&gt;                                                                                                                                                                                                           | Resolves device's availability for nearby search.                                                                     |
| **setAvailability**                   | (options: { isAvailable: boolean; }) =&gt; Promise&lt;void&gt;                                                                                                                                                                                        | Sets device's availability for nearby search.                                                                         |
| **isTracking**                        | () =&gt; Promise&lt;{ status: boolean; }&gt;                                                                                                                                                                                                          | Reflects tracking intent.                                                                                             |
| **addTrackingListener**               | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Allows tracking hypertrack sdk listener.                                                                              |
| **removeTrackingListener**            | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Stops tracking hypertrack sdk listener.                                                                               |
| **addAvailabilityListener**           | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Allows availability hypertrack sdk listener.                                                                          |
| **removeAvailabilityListener**        | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Stops availability hypertrack sdk listener.                                                                           |
| **syncDeviceSettings**                | () =&gt; Promise&lt;void&gt;                                                                                                                                                                                                                          | Synchronizes tracking state with platform model. This method is used to harden platform2device communication channel. |
| **setDeviceName**                     | (options: { name: string; }) =&gt; Promise&lt;void&gt;                                                                                                                                                                                                | Set device name                                                                                                       |
| **addGeotag**                         | (options: { metadata: <a href="#record">Record</a>&lt;string, unknown&gt;; coordinates?: { latitude: number; longitude: number; }; }) =&gt; Promise&lt;void&gt;                                                                                       | Adds special marker-like object to device timeline.                                                                   |
| **setDeviceMetadata**                 | (options: <a href="#record">Record</a>&lt;string, unknown&gt;) =&gt; Promise&lt;void&gt;                                                                                                                                                              | Use this to set additional properties, like segments, teams etc.                                                      |
| **setTrackingNotificationProperties** | (options: { title: string; message: string; }) =&gt; Promise&lt;void&gt;                                                                                                                                                                              | Updates title and text in persistent notification, that appears when tracking is active.                              |
| **addListener**                       | (eventName: 'trackingStateChange', listenerFunc: <a href="#statechangelistener">StateChangeListener</a>) =&gt; Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a>     | Adds listener for tracking state change                                                                               |
| **addListener**                       | (eventName: 'availabilityStateChange', listenerFunc: <a href="#statechangelistener">StateChangeListener</a>) =&gt; Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a> | Adds listener for availability state change                                                                           |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### State

| Prop       | Type             | Description                |
| ---------- | ---------------- | -------------------------- |
| **`info`** | <code>any</code> | The state of the tracking. |


#### Blocker

| Prop                        | Type                       | Description                                                                                          |
| --------------------------- | -------------------------- | ---------------------------------------------------------------------------------------------------- |
| **`userActionTitle`**       | <code>string</code>        | Recommended name for a user action, that needs to be performed to resolve the blocker.               |
| **`userActionCTA`**         | <code>string</code>        | Recommended name for a button, that will navigate user to the place where he can resolve the blocker |
| **`userActionExplanation`** | <code>string</code>        | User action explanation                                                                              |
| **`code`**                  | <code>string</code>        | <a href="#blocker">Blocker</a> code                                                                  |
| **`resolve`**               | <code>() =&gt; void</code> | An action that navigates user to the dedicated settings menu.                                        |


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{
 [P in K]: T;
 }</code>


#### StateChangeListener

<code>(state: <a href="#state">State</a>): void</code>

## Dashboard

Once your app is running, go to the [dashboard](https://dashboard.hypertrack.com/devices) where you can see a list of all your devices and their live location with ongoing activity on the map.

## Documentation

You can find API references in our [docs](https://www.hypertrack.com/docs/references/#references-sdks-ios). There is also a full in-code reference for all SDK methods.

## Support
Join our [Slack community](https://join.slack.com/t/hypertracksupport/shared_invite/enQtNDA0MDYxMzY1MDMxLTdmNDQ1ZDA1MTQxOTU2NTgwZTNiMzUyZDk0OThlMmJkNmE0ZGI2NGY2ZGRhYjY0Yzc0NTJlZWY2ZmE5ZTA2NjI) for instant responses. You can also email us at help@hypertrack.com.
</docgen-api>