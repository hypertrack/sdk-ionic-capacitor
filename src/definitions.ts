export interface HyperTrackSdkPlugin {
  enableDebugLogging():Promise<void>;
  initialize(options: { publishableKey: string }): Promise<void>;
  start():Promise<void>;
  stop():Promise<void>;
}