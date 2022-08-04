import { WebPlugin } from '@capacitor/core';

import type {  Callback, HyperTrackSdkPlugin } from './definitions';

export class HyperTrackSdkWeb extends WebPlugin implements HyperTrackSdkPlugin {

  async getDeviceId(): Promise<{ deviceId: string }> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async getAvailability(): Promise<{ status: string }> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async isTracking(): Promise<{ status: boolean }> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async setAvailability(_options: { isAvailable: boolean }): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async hyperTrackRequestPermissions(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async hyperTrackAllowMockLocations(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async removeTrackingListener(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async addAvailabilityListener(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async removeAvailabilityListener(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async syncDeviceSettings(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async setDeviceName(_options: { name: string }): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async setDeviceMetadata(_options: Record<string, unknown>): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async setTrackingNotificationProperties(_options: { title: string, message: string }): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async addTrackingListener(): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async addGeotag(_options: { metadata: Record<string, unknown>, coordinates?: { latitude: number, longitude: number } | undefined }): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }
  
  async initialize(_options: { publishableKey: string }): Promise<void> {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async  enableDebugLogging():Promise<void>  {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async  start():Promise<void>  {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  async  stop():Promise<void>  {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

  getLatestLocation(_callback: Callback): void {
    throw this.unimplemented('Hypertrack is not available for the browser');
  }

}
