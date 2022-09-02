import type { IsAvailable } from './data_types/internal/IsAvailable';
import type { IsTracking } from './data_types/internal/IsTracking';
import type { LocationResponse } from './data_types/internal/LocationResponse';
import type { Location } from './data_types/Location';
import type { LocationError } from './data_types/LocationError';
import type { SdkInitParams } from './data_types/SdkInitParams';
import type { DeviceId } from './data_types/internal/DeviceId';
import type { Geotag } from './data_types/internal/Geotag';
import type { DeviceName } from './data_types/internal/DeviceName';

import { registerPlugin } from '@capacitor/core';
import { HyperTrackCapacitorPlugin, Subscription } from './definitions';
import { HyperTrackError } from './data_types/HyperTrackError';
import { HyperTrackErrorInternal } from './data_types/internal/HyperTrackErrorInternal';

const hyperTrackPlugin = registerPlugin<HyperTrackCapacitorPlugin>('HyperTrackCapacitorPlugin', {
  // web: () => import('./web').then(m => new m.HyperTrackSdkWeb()),
});

export default class HyperTrack {
  constructor() {
    return this;
  }

  /**
   * Creates an SDK instance
   * 
   * @param publishableKey publishable key provided in HyperTrack’s dashboard setup page
   * @param sdkInitParams
   * @returns HyperTrack instance
   */
  static async initialize(
    publishableKey: string,
    sdkInitParams: SdkInitParams = {}
  ) {
    try {
      await hyperTrackPlugin.initialize({
        publishableKey,
        loggingEnabled: sdkInitParams.loggingEnabled ?? false,
        allowMockLocations: sdkInitParams.allowMockLocations ?? false,
        requireBackgroundTrackingPermission: sdkInitParams.requireBackgroundTrackingPermission ?? false,
      });
      return new HyperTrack();
    } catch (error: any) {
      throw new Error(error.message);
    }
  }

  /**
   * Returns a string that is used to uniquely identify the device
   */
  getDeviceId(): Promise<string> {
    return hyperTrackPlugin.getDeviceId().then(
      (deviceId: DeviceId) => deviceId.value
    );
  }

  /**
   * Reflects availability of the device for the Nearby search
   *
   * @returns true when is available or false when unavailable
   */
  isAvailable(): Promise<boolean> {
    return hyperTrackPlugin.isAvailable().then(
      (isAvailable: IsAvailable) => isAvailable.value
    );
  }

  /**
   * Sets the availability of the device for the Nearby search
   *
   * @param availability true when is available or false when unavailable
   */
  setAvailability(isAvailable: boolean) {
    hyperTrackPlugin.setAvailability({
      type: 'isAvailable',
      value: isAvailable
    } as IsAvailable);
  }

  /**
   * Reflects the tracking intent for the device
   * 
   * @return {boolean} Whether the user's movement data is getting tracked or not.
   */
  isTracking(): Promise<boolean> {
    return hyperTrackPlugin.isTracking().then(
      (isTracking: IsTracking) => isTracking.value
    );
  }

  /**
   * Syncs the device state with the HyperTrack servers
   */
  sync() {
    hyperTrackPlugin.sync();
  }

  /**
   * Expresses an intent to start location tracking for the device
   */
  startTracking() {
    hyperTrackPlugin.startTracking();
  }

  /**
   * Stops location tracking immediately
   */
  stopTracking() {
    hyperTrackPlugin.stopTracking();
  }

  /**
   * Reflects the current location of the user or an outage reason
   */
  getLocation(): Promise<LocationError | Location> {
    return hyperTrackPlugin.getLocation().then(
      (locationResponse: LocationResponse) => {
        return this.deserializeLocationResponse(locationResponse);
      }
    );
  }

  /**
   * Sets the name for the device
   * @param {string} name
   */
  setName(name: string) {
    hyperTrackPlugin.setName({
      type: 'deviceName',
      value: name
    } as DeviceName)
  }

  /**
   * Sets the metadata for the device
   * @param {Object} data - Metadata JSON
   */
  setMetadata(data: Object) {
    hyperTrackPlugin.setMetadata(data);
  }

  /**
   * Adds a new geotag
   * @param {Object} data - Geotad data JSON
   * @returns current location if success or LocationError if failure
   */
  addGeotag(data: Object): Promise<LocationError | Location> {
    return hyperTrackPlugin.addGeotag({
      data
    } as Geotag).then(
      (locationResponse: LocationResponse) => {
        return this.deserializeLocationResponse(locationResponse);
      }
    );
  }

  /**
   * Subscribe to tracking intent changes
   *
   * @param subscriptionFunction (isTracking: boolean) => void
   * @returns Promise<Subscription>
   */
  subscribeToTracking(subscriptionFunction: (isTracking: boolean) => void): Promise<Subscription> {
    const result = hyperTrackPlugin.addListener("onTrackingChanged", (info: IsTracking) => subscriptionFunction(info.value))
    hyperTrackPlugin.onSubscribedToTracking();
    console.log("onSubscribedToTracking")
    return result
  }

  /**
   * Subscribe to availability changes
   *
   * @param subscriptionFunction (isAvailable: boolean) => void
   * @returns Promise<Subscription>
   */
  subscribeToAvailability(subscriptionFunction: (isAvailable: boolean) => void): Promise<Subscription> {
    const result =  hyperTrackPlugin.addListener("onAvailabilityChanged", (info: IsAvailable) => subscriptionFunction(info.value))
    hyperTrackPlugin.onSubscribedToAvailability();
    console.log("onSubscribedToAvailability")
    return result
  }

  /**
   * Subscribe to tracking errors
   *
   * @param subscriptionFunction (errors: HyperTrackError[]) => void
   * @returns Promise<Subscription>
   */
  subscribeToErrors(subscriptionFunction: (errors: HyperTrackError[]) => void): Promise<Subscription> {
    const result =  hyperTrackPlugin.addListener("onError", (info: Errors) => {
      subscriptionFunction(this.deserializeHyperTrackErrors(info.errors));
    })
    hyperTrackPlugin.onSubscribedToErrors();
    return result
  }

  /** @ignore */
  private deserializeHyperTrackErrors(errors: HyperTrackErrorInternal[]): HyperTrackError[] {
    return errors.map((error: HyperTrackErrorInternal) => HyperTrackError[error.value as keyof typeof HyperTrackError])
  }

  /** @ignore */
  private deserializeLocationResponse(response: LocationResponse): Location | LocationError {
    switch(response.type) {
      case "success":
        return response.value
      case "failure":
        switch(response.value.type) {
          case "notRunning":
          case "starting":
            return response.value
          case "errors":
            return {
              type: "errors",
              value: this.deserializeHyperTrackErrors(response.value.value)
            }
        }
    };
  }
}

type Errors = {
  "errors": HyperTrackErrorInternal[]
}
