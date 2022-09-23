import { Point, Address } from './interface';
export * from './interface';
declare class CarefreeSearchAmap {
    /**
     * 设置apiKey
     */
    static initSDK: (apiKey: string) => Promise<boolean | string>;
    /**
     * 经纬度转地址
     */
    static getAddress: (point: Point) => Promise<Address>;
    /**
     * 地址转经纬度
     */
    static getLatLong: (address: string) => Promise<Point>;
}
export default CarefreeSearchAmap;
