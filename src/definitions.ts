export interface HyperTrackSdkPlugin {
  enableDebugLogging():Promise<void>;
  initialize(options: { publishableKey: string }): Promise<Record<string,any>>;
  start():Promise<void>;
  stop():Promise<void>;
}
