import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
const eventEmitter = new NativeEventEmitter(NativeModules.CarefreeSearchAmap);
class CarefreeSearchAmap {
  /**
   * 设置apiKey
   */
  static initSDK = (apiKey) => {
    return NativeModules.CarefreeSearchAmap.initSDK(apiKey);
  };
  /**
   * 经纬度转地址
   */
  static getAddress = (point) => {
    if (Platform.OS === 'ios') {
      return new Promise((resolve, reject) => {
        NativeModules.CarefreeSearchAmap.getAddress(point.latitude, point.longitude);
        eventEmitter.addListener('GetAddress', (info) => {
          if (!info.errCode) {
            resolve(info);
          }
          else {
            reject(info);
          }
        });
        eventEmitter.addListener('AddressOrLatLongError', (info) => {
          reject(info);
        });
      });
    }
    else {
      return NativeModules.CarefreeSearchAmap.getAddress(point);
    }
  };
  /**
   * 地址转经纬度
   */
  static getLatLong = (address) => {
    if (Platform.OS === 'ios') {
      return new Promise((resolve, reject) => {
        NativeModules.CarefreeSearchAmap.getLatLong(address);
        eventEmitter.addListener('GetLatLong', (info) => {
          if (!info.errCode) {
            resolve(info);
          }
          else {
            reject(info);
          }
        });
        eventEmitter.addListener('AddressOrLatLongError', (info) => {
          reject(info);
        });
      });
    }
    else {
      return NativeModules.CarefreeSearchAmap.getLatLong(address);
    }
  };
}
export default CarefreeSearchAmap;
