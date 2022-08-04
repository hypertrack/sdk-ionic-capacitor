import { registerPlugin } from '@capacitor/core';

import type { HyperTrackInit, HyperTrackSdkInstance, HyperTrackSdkPlugin } from './definitions';

const HyperTrackSdk = registerPlugin<HyperTrackSdkPlugin>('HyperTrackSdk', {
  web: () => import('./web').then(m => new m.HyperTrackSdkWeb()),
});

const HyperTrack: HyperTrackInit = HyperTrackSdk;
const HyperTrackInstance: HyperTrackSdkInstance = HyperTrackSdk;

export * from './definitions';

export { HyperTrack, HyperTrackInstance };
