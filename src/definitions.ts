export interface HyperTrackSdkPlugin {
  /** Enables debug log in native HyperTrack SDK. */
  enableDebugLogging(): Promise<void>;
  /**
   * Entry point into SDK.
   *
   * Initializes SDK. Also resolves SDK instance that could be used to query deviceId or set
   * various data.
   *
   * @param options key-value pais of publishableKey, account-specific secret from the HyperTrack dashborad.
   * @see {@link https://dashboard.hypertrack.com/setup}.
   */
  initialize(options: { publishableKey: string }): Promise<HyperTrackSdkInstance>;
  /** Start tracking. */
  start(): Promise<void>;
  /** Stop tracking. */
  stop(): Promise<void>;
  /** Pops up permission request dialog, if permissions weren't granted before or does nothing otherwise. */
  requestPermissionsIfNecessary(): Promise<void>;
  /** Allows injecting false locations into the SDK, which ignores them by default. */
  allowMockLocations(): Promise<void>;
  /** Resolves device ID that could be used to identify the device. */
  getDeviceId(): Promise<{ deviceId: string }>;
  /** Resolves device's availability for nearby search. */
  getAvailability(): Promise<{ status: string }>;
  /** Sets device's availability for nearby search. */
  setAvailability(options: { isAvailable: boolean }): Promise<void>;
  /** Reflects tracking intent. */
  isTracking(): Promise<{ status: boolean }>;
  /** Allows tracking hypertrack sdk  listener. */
  addTrackingListener(): Promise<void>;
  /** Stops tracking hypertrack sdk  listener. */
  removeTrackingListener(): Promise<void>;
  /** Allows availability hypertrack sdk listener. */
  addAvailabilityListener(): Promise<void>;
  /** Stops availability hypertrack sdk listener. */
  removeAvailabilityListener(): Promise<void>;
  /**
   * Synchronizes tracking state with platform model. This method is used to
   * harden platform2device communication channel.
   */
  syncDeviceSettings(): Promise<void>;
  /**
   * Set device name
   * 
   * @param options key-value pais of name properties.
   */
  setDeviceName(options: { name: string }): Promise<void>;
  /**
   * Adds special marker-like object to device timeline.
   * 
   * @param options key-value pais of metadata & coordinates properties.
   */
  addGeotag(options: { metadata: Record<string, unknown>, coordinates?: { latitude: number, longitude: number } }): Promise<void>;
  /**
   * Use this to set additional properties, like segments, teams etc.
   *
   * @param options key-value pais of properties.
   */
  setDeviceMetadata(options: Record<string, unknown>): Promise<void>;
  /**
   * Updates title and text in persistent notification, that appears when tracking is active.
   *
   * @param options key-value pais of title & message properties.
   */
  setTrackingNotificationProperties(options: { title: string, message: string }): Promise<void>;
  /**
   * Adds listener for tracking state change
   * 
   * @param eventName 
   * @param listenerFunc 
   */
  addListener(eventName: 'trackingStateChange', listenerFunc: StateChangeListener): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Adds listener for availability state change
   * 
   * @param eventName 
   * @param listenerFunc 
   */
  addListener(eventName: 'availabilityStateChange', listenerFunc: StateChangeListener): Promise<PluginListenerHandle> & PluginListenerHandle;
  /** A blocker is an obstacle that needs to be resolved to achieve reliable tracking.*/
  getBlockers(): Promise<{ blockers: Blocker[] }>;
  /** An action that navigates user to the dedicated settings menu. */
  resolveBlocker(options: { code: string }): Promise<void>;
}

// Interfaces for Hypertrack Sdk Instance 
export interface HyperTrackSdkInstance {
  /** Start tracking. */
  start(): Promise<void>;
  /** Stop tracking. */
  stop(): Promise<void>;
  /** Pops up permission request dialog, if permissions weren't granted before or does nothing otherwise. */
  requestPermissionsIfNecessary(): Promise<void>;
  /** Allows injecting false locations into the SDK, which ignores them by default. */
  allowMockLocations(): Promise<void>;
  /** Resolves device ID that could be used to identify the device. */
  getDeviceId(): Promise<{ deviceId: string }>;
  /** Resolves device's availability for nearby search. */
  getAvailability(): Promise<{ status: string }>;
  /** Sets device's availability for nearby search. */
  setAvailability(options: { isAvailable: boolean }): Promise<void>;
  /** Reflects tracking intent. */
  isTracking(): Promise<{ status: boolean }>;
  /** Allows tracking hypertrack sdk  listener. */
  addTrackingListener(): Promise<void>;
  /** Stops tracking hypertrack sdk  listener. */
  removeTrackingListener(): Promise<void>;
  /** Allows availability hypertrack sdk listener. */
  addAvailabilityListener(): Promise<void>;
  /** Stops availability hypertrack sdk listener. */
  removeAvailabilityListener(): Promise<void>;
  /**
   * Synchronizes tracking state with platform model. This method is used to
   * harden platform2device communication channel.
   */
  syncDeviceSettings(): Promise<void>;
  /**
   * Set device name
   * 
   * @param options key-value pais of name properties.
   */
  setDeviceName(options: { name: string }): Promise<void>;
  /**
   * Adds special marker-like object to device timeline.
   * 
   * @param options key-value pais of metadata & coordinates properties.
   */
  addGeotag(options: { metadata: Record<string, unknown>, coordinates?: { latitude: number, longitude: number } }): Promise<void>;
  /**
   * Use this to set additional properties, like segments, teams etc.
   *
   * @param options key-value pais of properties.
   */
  setDeviceMetadata(options: Record<string, unknown>): Promise<void>;
  /**
   * Updates title and text in persistent notification, that appears when tracking is active.
   *
   * @param options key-value pais of title & message properties.
   */
  setTrackingNotificationProperties(options: { title: string, message: string }): Promise<void>;
  /**
   * Adds listener for tracking state change
   * 
   * @param eventName 
   * @param listenerFunc 
   */
  addListener(eventName: 'trackingStateChange', listenerFunc: StateChangeListener): Promise<PluginListenerHandle> & PluginListenerHandle;
  /**
   * Adds listener for availability state change
   * 
   * @param eventName 
   * @param listenerFunc 
   */
  addListener(eventName: 'availabilityStateChange', listenerFunc: StateChangeListener): Promise<PluginListenerHandle> & PluginListenerHandle;
}
export interface State {
  /**
   * The state of the tracking.
   *
   */
  info: any;
}

// Interfaces for PluginListenerHandle
export interface PluginListenerHandle {
  remove: () => Promise<void>;
}

export interface Blocker {
  /** Recommended name for a user action, that needs to be performed to resolve the blocker. */
  userActionTitle: string;
  /** Recommended name for a button, that will navigate user to the place where he can resolve the blocker */
  userActionCTA: string;
  /** User action explanation */
  userActionExplanation: string;
  /** Blocker code */
  code: string;
  /** An action that navigates user to the dedicated settings menu. */
  resolve: () => void;
}

export declare type StateChangeListener = (state: State) => void;
