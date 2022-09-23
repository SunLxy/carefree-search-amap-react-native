import { NativeModules, NativeEventEmitter, Platform } from 'react-native'
import { Point, Address } from './interface'
export * from './interface'
const { CarefreeSearchAmap } = NativeModules
const eventEmitter = new NativeEventEmitter(NativeModules.CarefreeSearchAmap)

/**
 * 地址转经纬度
 */
export const getLatLong = (address: string): Promise<Point> => {
  if (Platform.OS === 'ios') {
    return new Promise((resolve, reject) => {
      CarefreeSearchAmap.getLatLong(address)
      eventEmitter.addListener('GetLatLong', (info) => {
        if (!info.errCode) {
          resolve(info)
        } else {
          reject(info)
        }
      })
    })
  } else {
    return CarefreeSearchAmap.getLatLong(address)
  }
}

/**
 * 经纬度转地址
 */
export const getAddress = (point: Point): Promise<Address> => {
  if (Platform.OS === 'ios') {
    return new Promise((resolve, reject) => {
      CarefreeSearchAmap.getAddress(point)
      eventEmitter.addListener('GetAddress', (info) => {
        if (!info.errCode) {
          resolve(info)
        } else {
          reject(info)
        }
      })
    })
  } else {
    return CarefreeSearchAmap.getAddress(point)
  }
}

/**
 * 设置apiKey
 */
export const initSDK = (apiKey: string): Promise<boolean | string> => {
  return CarefreeSearchAmap.initSDK(apiKey)
}

export default CarefreeSearchAmap
