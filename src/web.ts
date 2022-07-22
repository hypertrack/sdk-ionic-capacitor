import { WebPlugin } from '@capacitor/core';

import type { HyperTrackSdkPlugin } from './definitions';

export class HyperTrackSdkWeb extends WebPlugin implements HyperTrackSdkPlugin {
  
  async initialize(options: { publishableKey: string }): Promise<Record<string,any>> {
    throw new Error('Not supported on web');
  }

  async  enableDebugLogging():Promise<void>  {
    throw new Error('Not supported on web');
  }

  async  start():Promise<void>  {
    throw new Error('Not supported on web');
  }

  async  stop():Promise<void>  {
    throw new Error('Not supported on web');
  }

}
