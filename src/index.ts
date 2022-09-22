import { NativeModules } from 'react-native'
import { Point, Address } from './interface'
export * from './interface'
const { CarefreeSearchAmap } = NativeModules

/**
 * 地址转经纬度
 */
export const getLatLong = (address: string): Promise<Point> => {
  return CarefreeSearchAmap.getLatLong(address)
}

/**
 * 经纬度转地址
 */
export const getAddress = (point: Point): Promise<Address> => {
  return CarefreeSearchAmap.getAddress(point)
}

/**
 * 设置apiKey
 */
export const initSDK = (apiKey: string): Promise<boolean | string> => {
  return CarefreeSearchAmap.initSDK(apiKey)
}

export default CarefreeSearchAmap
