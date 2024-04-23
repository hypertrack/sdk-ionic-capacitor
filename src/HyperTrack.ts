import { HyperTrackError } from './data_types/HyperTrackError';
import type { IsAvailable } from './data_types/internal/IsAvailable';
import type { IsTracking } from './data_types/internal/IsTracking';
import type { Location } from './data_types/Location';
import type { LocationError } from './data_types/LocationError';
import type { DeviceId } from './data_types/internal/DeviceId';
import type { GeotagData } from './data_types/internal/GeotagData';
import type { Name } from './data_types/internal/Name';
import type { HyperTrackErrorInternal } from './data_types/internal/HyperTrackErrorInternal';
import type { LocationWithDeviation } from './data_types/LocationWithDeviation';
import type { LocationErrorInternal } from './data_types/internal/LocationErrorInternal';
import type { Result } from './data_types/Result';
import type { LocationInternal } from './data_types/internal/LocationInternal';
import type { LocationWithDeviationInternal } from './data_types/internal/LocationWithDeviationInternal';
import type { Metadata } from './data_types/internal/Metadata';
import type { OrderHandle } from './data_types/internal/OrderHandle';
import { registerPlugin } from '@capacitor/core';
import { Subscription } from './Subscription';
import { Errors, HyperTrackCapacitorPlugin } from './HyperTrackCapacitorPlugin';
import { OrderStatus } from './data_types/OrderStatus';

export const EVENT_ERRORS = 'errors';
export const EVENT_IS_AVAILABLE = 'isAvailable';
export const EVENT_IS_TRACKING = 'isTracking';
export const EVENT_LOCATE = 'locate';
export const EVENT_LOCATION = 'location';

const hyperTrackPlugin = registerPlugin<HyperTrackCapacitorPlugin>('HyperTrackCapacitorPlugin', {
  // web: () => import('./web').then(m => new m.HyperTrackSdkWeb()),
});

export default class HyperTrack {
  private static locateSubscription: Subscription | undefined;

  /**
   * Adds a new geotag
   *
   * @param {string} orderHandle - Order handle
   * @param {OrderStatus} orderStatus - Order status
   * @param {Object} data - Geotag data JSON
   * @returns current location if success or LocationError if failure
   */
  static async addGeotag(
    orderHandle: string,
    orderStatus: OrderStatus,
    data: Object,
  ): Promise<Result<Location, LocationError>>;

  /**
   * Adds a new geotag with expected location
   *
   * @param {string} orderHandle - Order handle
   * @param {OrderStatus} orderStatus - Order status
   * @param {Object} data - Geotag data JSON
   * @param {Location} expectedLocation - Expected location
   * @returns location with deviation if success or LocationError if failure
   */
  static async addGeotag(
    orderHandle: string,
    orderStatus: OrderStatus,
    data: Object,
    expectedLocation: Location,
  ): Promise<Result<LocationWithDeviation, LocationError>>;

  /**
   * @deprecated
   * Adds a new geotag
   *
   * @param {Object} data - Geotag data JSON
   * @returns current location if success or LocationError if failure
   */
  static async addGeotag(data: Object): Promise<Result<Location, LocationError>>;

  /**
   * @deprecated
   * Adds a new geotag with expected location
   *
   * @param {Object} data - Geotag data JSON
   * @param {Location} expectedLocation - Expected location
   * @returns location with deviation if success or LocationError if failure
   */
  static async addGeotag(
    data: Object,
    expectedLocation: Location,
  ): Promise<Result<LocationWithDeviation, LocationError>>;

  static async addGeotag(...args: any[]): Promise<Result<Location | LocationWithDeviation, LocationError>> {
    if (
      args.length === 3 &&
      typeof args[0] === 'string' &&
      HyperTrack.isOrderStatus(args[1]) &&
      typeof args[2] === 'object'
    ) {
      // addGeotag(orderHandle: string, orderStatus: OrderStatus, data: Object)
      return hyperTrackPlugin
        .addGeotag({
          orderHandle: {
            type: 'orderHandle',
            value: args[0],
          } as OrderHandle,
          orderStatus: args[1],
          data: args[2],
          expectedLocation: undefined,
        } as GeotagData)
        .then((locationResponse: Result<LocationInternal, LocationErrorInternal>) => {
          return this.deserializeLocationResponse(locationResponse);
        });
    }
    if (
      args.length === 4 &&
      typeof args[0] === 'string' &&
      HyperTrack.isOrderStatus(args[1]) &&
      typeof args[2] === 'object' &&
      HyperTrack.isLocation(args[3])
    ) {
      // addGeotag(orderHandle: string, orderStatus: OrderStatus, data: Object, expectedLocation: Location)
      return hyperTrackPlugin
        .addGeotag({
          orderHandle: {
            type: 'orderHandle',
            value: args[0],
          } as OrderHandle,
          orderStatus: args[1],
          data: args[2],
          expectedLocation: {
            type: 'location',
            value: {
              latitude: args[3].latitude,
              longitude: args[3].longitude,
            },
          } as LocationInternal,
        } as GeotagData)
        .then((locationResponse: Result<LocationWithDeviationInternal, LocationErrorInternal>) => {
          return this.deserializeLocationWithDeviationResponse(locationResponse);
        });
    }
    if (args.length === 1 && typeof args[0] === 'object') {
      // addGeotag(data: Object)
      return hyperTrackPlugin
        .addGeotag({
          data: args[0],
          expectedLocation: undefined,
        } as GeotagData)
        .then((locationResponse: Result<LocationInternal, LocationErrorInternal>) => {
          return this.deserializeLocationResponse(locationResponse);
        });
    }
    if (args.length === 2 && typeof args[0] === 'object' && HyperTrack.isLocation(args[1])) {
      // addGeotag(data: Object, expectedLocation: Location)
      return hyperTrackPlugin
        .addGeotag({
          data: args[0],
          expectedLocation: {
            type: 'location',
            value: {
              latitude: args[1].latitude,
              longitude: args[1].longitude,
            },
          } as LocationInternal,
        } as GeotagData)
        .then((locationResponse: Result<LocationWithDeviationInternal, LocationErrorInternal>) => {
          return this.deserializeLocationWithDeviationResponse(locationResponse);
        });
    }
    throw new Error(`Invalid addGeotag() arguments: ${JSON.stringify(args)}`);
  }

  /**
   * Returns a string that is used to uniquely identify the device
   *
   * @returns {string} Device ID
   */
  static async getDeviceId(): Promise<string> {
    return hyperTrackPlugin.getDeviceId().then((deviceId: DeviceId) => deviceId.value);
  }

  /**
   * Returns a list of errors that blocks SDK from tracking
   *
   * @returns {HyperTrackError[]} List of errors
   */
  static async getErrors(): Promise<HyperTrackError[]> {
    return hyperTrackPlugin.getErrors().then((errors: Errors) => {
      return this.deserializeHyperTrackErrors(errors.errors);
    });
  }

  /**
   * Reflects availability of the device for the Nearby search
   *
   * @returns true when is available or false when unavailable
   */
  static async getIsAvailable(): Promise<boolean> {
    return hyperTrackPlugin.getIsAvailable().then((isAvailable: IsAvailable) => isAvailable.value);
  }

  /**
   * Reflects the tracking intent for the device
   *
   * @returns {boolean} Whether the user's movement data is getting tracked or not.
   */
  static async getIsTracking(): Promise<boolean> {
    return hyperTrackPlugin.getIsTracking().then((isTracking: IsTracking) => isTracking.value);
  }

  /**
   * Reflects the current location of the user or an outage reason
   */
  static async getLocation(): Promise<Result<Location, LocationError>> {
    return hyperTrackPlugin.getLocation().then((locationResponse: Result<LocationInternal, LocationErrorInternal>) => {
      return this.deserializeLocationResponse(locationResponse);
    });
  }

  /**
   * Gets the metadata that is set for the device
   *
   * @returns {Object} Metadata JSON
   */
  static async getMetadata(): Promise<Object> {
    return hyperTrackPlugin.getMetadata().then((metadata: Metadata) => {
      return this.deserializeMetadata(metadata);
    });
  }

  /**
   * Gets the name that is set for the device
   *
   * @returns {string} Device name
   */
  static async getName(): Promise<string> {
    return hyperTrackPlugin.getName().then((name: Name) => {
      return this.deserializeName(name);
    });
  }

  /**
   * Requests one-time location update and returns the location once it is available, or error.
   *
   * Only one locate subscription can be active at a time. If you re-subscribe, the old Subscription
   * will be automaticaly removed.
   *
   * This method will start location tracking if called, and will stop it when the location is received or
   * the subscription is cancelled. If any other tracking intent is present (e.g. isAvailable is set to `true`),
   * the tracking will not be stopped.
   *
   * @param callback
   * @returns Subscription
   * @example
   * ```js
   * const subscription = HyperTrack.locate(location => {
   *  ...
   * })
   *
   * // to unsubscribe
   * subscription.remove()
   * ```
   */
  static locate(callback: (locateResult: Result<Location, HyperTrackError[]>) => void) {
    // this call doesn't work on iOS for some reason
    this.locateSubscription?.remove();
    this.locateSubscription = hyperTrackPlugin.addListener(
      EVENT_LOCATE,
      (location: Result<LocationInternal, HyperTrackErrorInternal[]>) => {
        callback(this.deserializeLocateResponse(location));
        // so we remove the subscription here (locate should return only one event)
        this.locateSubscription?.remove();
      },
    );
    hyperTrackPlugin.onSubscribedToLocate();
    return this.locateSubscription;
  }

  /**
   * Sets the availability of the device for the Nearby search
   *
   * @param availability true when is available or false when unavailable
   */
  static async setIsAvailable(isAvailable: boolean) {
    hyperTrackPlugin.setIsAvailable({
      type: 'isAvailable',
      value: isAvailable,
    } as IsAvailable);
  }

  /**
   * Sets the tracking intent for the device
   *
   * @param {boolean} isTracking
   */
  static async setIsTracking(isTracking: boolean): Promise<void> {
    hyperTrackPlugin.setIsTracking({
      type: 'isTracking',
      value: isTracking,
    } as IsTracking);
  }

  /**
   * Sets the metadata for the device
   *
   * @param {Object} data - Metadata JSON
   */
  static async setMetadata(data: Object) {
    hyperTrackPlugin.setMetadata({
      type: 'metadata',
      value: data,
    });
  }

  /**
   * Sets the name for the device
   *
   * @param {string} name
   */
  static async setName(name: string) {
    hyperTrackPlugin.setName({
      type: 'name',
      value: name,
    } as Name);
  }

  /**
   * Subscribe to tracking errors
   *
   * @param listener
   * @returns Subscription
   * @example
   * ```js
   * const subscription = HyperTrack.subscribeToErrors(errors => {
   *   errors.forEach(error => {
   *     // ... error
   *   })
   * })
   *
   * // later, to stop listening
   * subscription.remove()
   * ```
   */
  static subscribeToErrors(listener: (errors: HyperTrackError[]) => void): Subscription {
    const result = hyperTrackPlugin.addListener(EVENT_ERRORS, (info: Errors) => {
      listener(this.deserializeHyperTrackErrors(info.errors));
    });
    hyperTrackPlugin.onSubscribedToErrors();
    return result;
  }

  /**
   * Subscribe to availability changes
   *
   * @param listener
   * @returns Subscription
   * @example
   * ```js
   * const subscription = HyperTrack.subscribeToIsAvailable(isAvailable => {
   *   if (isAvailable) {
   *     // ... ready to go
   *   }
   * })
   *
   * // later, to stop listening
   * subscription.remove()
   * ```
   */
  static subscribeToIsAvailable(listener: (isAvailable: boolean) => void): Subscription {
    const result = hyperTrackPlugin.addListener(EVENT_IS_AVAILABLE, (isAvailable: IsAvailable) => {
      listener(isAvailable.value);
    });
    hyperTrackPlugin.onSubscribedToIsAvailable();
    return result;
  }

  /**
   * Subscribe to tracking intent changes
   *
   * @param listener
   * @returns Subscription
   * @example
   * ```js
   * const subscription = HyperTrack.subscribeToIsTracking(isTracking => {
   *   if (isTracking) {
   *     // ... ready to go
   *   }
   * })
   *
   * // later, to stop listening
   * subscription.remove()
   * ```
   */
  static subscribeToIsTracking(listener: (isTracking: boolean) => void): Subscription {
    const result = hyperTrackPlugin.addListener(EVENT_IS_TRACKING, (isTracking: IsTracking) => {
      listener(isTracking.value);
    });
    hyperTrackPlugin.onSubscribedToIsTracking();
    return result;
  }

  /**
   * Subscribe to location changes
   *
   * @param listener
   * @returns Subscription
   * @example
   * ```js
   * const subscription = HyperTrack.subscribeToLocation(location => {
   *   ...
   * })
   *
   * // later, to stop listening
   * subscription.remove()
   * ```
   */
  static subscribeToLocation(listener: (location: Result<Location, LocationError>) => void) {
    const result = hyperTrackPlugin.addListener(
      EVENT_LOCATION,
      (location: Result<LocationInternal, LocationErrorInternal>) => {
        listener(this.deserializeLocationResponse(location));
      },
    );
    hyperTrackPlugin.onSubscribedToLocation();
    return result;
  }

  /** @ignore */
  private static deserializeHyperTrackErrors(errors: HyperTrackErrorInternal[]): HyperTrackError[] {
    let res = errors.map((error: HyperTrackErrorInternal) => {
      if (error.type !== 'error') {
        throw new Error('Invalid error type');
      }
      return Object.keys(HyperTrackError).find(
        (key) => HyperTrackError[key as keyof typeof HyperTrackError] === error.value,
      ) as HyperTrackError;
    });
    return res;
  }

  /** @ignore */
  private static deserializeLocateResponse(
    response: Result<LocationInternal, HyperTrackErrorInternal[]>,
  ): Result<Location, HyperTrackError[]> {
    switch (response.type) {
      case 'success':
        return {
          type: 'success',
          value: response.value.value,
        };
      case 'failure':
        return {
          type: 'failure',
          value: this.deserializeHyperTrackErrors(response.value),
        };
    }
  }

  /** @ignore */
  private static deserializeLocationError(locationError: LocationErrorInternal): LocationError {
    switch (locationError.type) {
      case 'notRunning':
      case 'starting':
        return locationError;
      case 'errors':
        return {
          type: 'errors',
          value: this.deserializeHyperTrackErrors(locationError.value),
        };
    }
  }

  /** @ignore */
  private static deserializeLocationResponse(
    response: Result<LocationInternal, LocationErrorInternal>,
  ): Result<Location, LocationError> {
    switch (response.type) {
      case 'success':
        return {
          type: 'success',
          value: response.value.value,
        };
      case 'failure':
        return {
          type: 'failure',
          value: this.deserializeLocationError(response.value),
        };
    }
  }

  /** @ignore */
  private static deserializeLocationWithDeviationResponse(
    response: Result<LocationWithDeviationInternal, LocationErrorInternal>,
  ): Result<LocationWithDeviation, LocationError> {
    switch (response.type) {
      case 'success':
        const locationWithDeviationInternal: LocationWithDeviationInternal = response.value;
        const locationInternal: LocationInternal = locationWithDeviationInternal.value.location;

        return {
          type: 'success',
          value: {
            location: locationInternal.value,
            deviation: locationWithDeviationInternal.value.deviation,
          } as LocationWithDeviation,
        };
      case 'failure':
        return {
          type: 'failure',
          value: this.deserializeLocationError(response.value),
        };
    }
  }

  /** @ignore */
  private static deserializeMetadata(metadata: Metadata): Object {
    if (metadata.type !== 'metadata') {
      throw new Error(`Invalid metadata: ${JSON.stringify(metadata)}`);
    }
    return metadata.value;
  }

  /** @ignore */
  private static deserializeName(name: Name): string {
    if (name.type !== 'name') {
      throw new Error(`Invalid name: ${JSON.stringify(name)}`);
    }
    return name.value;
  }

  /** @ignore */
  private static isLocation(obj: Location): obj is Location {
    return (
      'latitude' in obj && typeof obj.latitude === 'number' && 'longitude' in obj && typeof obj.longitude === 'number'
    );
  }

  /** @ignore */
  private static isOrderStatus(obj: OrderStatus): obj is OrderStatus {
    return 'type' in obj && obj.type.startsWith('orderStatus');
  }
}
