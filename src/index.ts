import { registerPlugin } from '@capacitor/core';

import type { HyperTrackSdkPlugin } from './definitions';

const HyperTrack = registerPlugin<HyperTrackSdkPlugin>('HyperTrackSdk', {
  web: () => import('./web').then(m => new m.HyperTrackSdkWeb()),
});

export * from './definitions';
export { HyperTrack };
