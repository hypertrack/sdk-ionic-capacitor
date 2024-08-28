import { Result } from '../Result';
import type { LocationErrorInternal } from './LocationErrorInternal';
import type { IsInsideGeofence } from './IsInsideGeofence';

export type OrderInternal = {
  orderHandle: string;
  isInsideGeofence: Result<IsInsideGeofence, LocationErrorInternal>;
  index: number;
};
