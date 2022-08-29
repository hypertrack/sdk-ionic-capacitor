import { registerPlugin } from '@capacitor/core';

import type { Blocker, HyperTrackSdkInstance, HyperTrackSdkPlugin } from './definitions';

const HyperTrackSdk = registerPlugin<HyperTrackSdkPlugin>('HyperTrackSdk', {
  web: () => import('./web').then(m => new m.HyperTrackSdkWeb()),
});

const HyperTrackInstance: HyperTrackSdkInstance = HyperTrackSdk;

export * from './definitions';
export class HyperTrack {
  static enableDebugLogging(): void {
    HyperTrackSdk.enableDebugLogging();
  }

  static ininitialize(publishableKey: string): Promise<{ hyperTrackInstance: HyperTrackSdkInstance }> {
    return new Promise((resolve, reject) => {
      HyperTrackSdk.initialize({ publishableKey }).then(() => {
        resolve({ hyperTrackInstance: HyperTrackInstance });
      }).catch((err) => {
        reject(err);
      });
    });
  }

  static getBlockers(): Promise<Blocker[]> {
    return new Promise((resolve, reject) => {
      HyperTrackSdk.getBlockers().then((res) => {
        const blockers = res.blockers;
        blockers.forEach((blocker: Blocker) => {
          blocker.resolve = function () {
            HyperTrackSdk.resolveBlocker({ code: blocker.code });
          }
        });
        resolve(blockers);
      }).catch((err) => {
        reject(err);
      });
    })
  }
}