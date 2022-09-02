import { DeviceId } from "./data_types/internal/DeviceId";
import { DeviceName } from "./data_types/internal/DeviceName";
import { IsAvailable } from "./data_types/internal/IsAvailable";
import { IsTracking } from "./data_types/internal/IsTracking";
import { LocationResponse } from "./data_types/internal/LocationResponse";

export interface HyperTrackCapacitorPlugin {
  initialize(sdkInitParams: {}): Promise<HyperTrackCapacitorPlugin>;
  startTracking(): Promise<void>;
  stopTracking(): Promise<void>;
  getDeviceId(): Promise<DeviceId>;
  /** Sets device's availability for nearby search. */
  setAvailability(isAvailable: IsAvailable): Promise<void>;
  /** Reflects tracking intent. */
  isTracking(): Promise<IsTracking>;
  isAvailable(): Promise<IsAvailable>;
  setName(name: DeviceName): Promise<void>;
  setMetadata(data: Object): Promise<void>;
  sync(): Promise<void>;
  getLocation(): Promise<LocationResponse>;
  addGeotag(data: Object): Promise<LocationResponse>;

  onSubscribedToTracking(): Promise<void>
  onSubscribedToAvailability(): Promise<void>
  onSubscribedToErrors(): Promise<void>

  addListener(eventName: 'onTrackingChanged', listenerFunc: (info: any) => void): Promise<Subscription> & Subscription;
  addListener(eventName: 'onAvailabilityChanged', listenerFunc: (info: any) => void): Promise<Subscription> & Subscription;
  addListener(eventName: 'onError', listenerFunc: (info: any) => void): Promise<Subscription> & Subscription;
}

export interface Subscription {
  remove: () => Promise<void>;
}
