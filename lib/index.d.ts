import { Point, Address } from './interface';
export * from './interface';
declare const CarefreeSearchAmap: any;
/**
 * 地址转经纬度
 */
export declare const getLatLong: (address: string) => Promise<Point>;
/**
 * 经纬度转地址
 */
export declare const getAddress: (point: Point) => Promise<Address>;
/**
 * 设置apiKey
 */
export declare const initSDK: (apiKey: string) => Promise<boolean | string>;
export default CarefreeSearchAmap;
