import {
  EVENT_ERRORS,
  EVENT_IS_AVAILABLE,
  EVENT_IS_TRACKING,
  EVENT_LOCATE,
  EVENT_LOCATION,
  EVENT_ORDERS,
} from './HyperTrack';
import { Subscription } from './Subscription';
import { Result } from './data_types/Result';
import { AllowMockLocation } from './data_types/internal/AllowMockLocation';
import { DeviceId } from './data_types/internal/DeviceId';
import { HyperTrackErrorInternal } from './data_types/internal/HyperTrackErrorInternal';
import { IsAvailable } from './data_types/internal/IsAvailable';
import { IsInsideGeofence } from './data_types/internal/IsInsideGeofence';
import { IsTracking } from './data_types/internal/IsTracking';
import { LocationErrorInternal } from './data_types/internal/LocationErrorInternal';
import { LocationInternal } from './data_types/internal/LocationInternal';
import { Metadata } from './data_types/internal/Metadata';
import { Name } from './data_types/internal/Name';
import { OrderHandle } from './data_types/internal/OrderHandle';
import { OrdersInternal } from './data_types/internal/OrdersInternal';
import { WorkerHandle } from './data_types/internal/WorkerHandle';

export type Errors = {
  errors: HyperTrackErrorInternal[];
};

export interface HyperTrackCapacitorPlugin {
  addGeotag(...args: any[]): Promise<any>;

  getAllowMockLocation(): Promise<AllowMockLocation>;

  getDeviceId(): Promise<DeviceId>;

  getErrors(): Promise<Errors>;

  getIsAvailable(): Promise<IsAvailable>;

  getIsTracking(): Promise<IsTracking>;

  getLocation(): Promise<Result<LocationInternal, LocationErrorInternal>>;

  getMetadata(): Promise<Metadata>;

  getName(): Promise<Name>;

  getOrderIsInsideGeofence(orderHandle: OrderHandle): Promise<Result<IsInsideGeofence, LocationErrorInternal>>;

  getOrders(): Promise<OrdersInternal>;

  getWorkerHandle(): Promise<WorkerHandle>;

  setAllowMockLocation(allowMockLocation: AllowMockLocation): Promise<void>;

  setIsAvailable(isAvailable: IsAvailable): Promise<void>;

  setIsTracking(isTracking: IsTracking): Promise<void>;

  setMetadata(data: Metadata): Promise<void>;

  setName(name: Name): Promise<void>;

  setWorkerHandle(workerHandle: WorkerHandle): Promise<void>;

  onSubscribedToErrors(): Promise<void>;
  onSubscribedToIsAvailable(): Promise<void>;
  onSubscribedToIsTracking(): Promise<void>;
  onSubscribedToLocate(): Promise<void>;
  onSubscribedToLocation(): Promise<void>;
  onSubscribedToOrders(): Promise<void>;

  addListener(eventName: typeof EVENT_ERRORS, listenerFunc: (error: any) => void): Subscription;

  addListener(eventName: typeof EVENT_IS_AVAILABLE, listenerFunc: (isAvailable: IsAvailable) => void): Subscription;

  addListener(eventName: typeof EVENT_IS_TRACKING, listenerFunc: (isTracking: IsTracking) => void): Subscription;

  addListener(
    eventName: typeof EVENT_LOCATE,
    listenerFunc: (locateResult: Result<LocationInternal, HyperTrackErrorInternal[]>) => void,
  ): Subscription;

  addListener(
    eventName: typeof EVENT_LOCATION,
    listenerFunc: (location: Result<LocationInternal, LocationErrorInternal>) => void,
  ): Subscription;

  addListener(eventName: typeof EVENT_ORDERS, listenerFunc: (orders: OrdersInternal) => void): Subscription;
}
