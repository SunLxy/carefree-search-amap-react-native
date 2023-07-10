import { NativeModules, NativeEventEmitter, Platform } from 'react-native';

const eventEmitter = new NativeEventEmitter(NativeModules.CarefreeSearchAmap);

const handleData = async (result, other = {}) => {
  if (result && [-3, "-3"].includes(result.errCode)) {
    return Promise.reject(result.errInfo)
  }
  return Promise.resolve({ ...result, ...other })
}

class RNSearchAmap {
  /**
   * 设置apiKey
   */
  static initSDK = (apiKey) => {
    return NativeModules.CarefreeSearchAmap.initSDK(apiKey);
  };
  /**
   * 经纬度转地址
   */
  static getAddress = async (point) => {
    if (Platform.OS === 'ios') {
      return new Promise((resolve, reject) => {
        NativeModules.CarefreeSearchAmap.getAddress(point.latitude, point.longitude);
        eventEmitter.addListener('GetAddress', (info) => {
          resolve({ ...info, latitude: point.latitude, longitude: point.longitude });
        });
        eventEmitter.addListener('AddressOrLatLongError', (info) => {
          reject(info);
        });
      });
    }
    else {
      return handleData(await NativeModules.CarefreeSearchAmap.getAddress(point), point);
    }
  };
  /**
   * 地址转经纬度
   */
  static getLatLong = async (address) => {
    if (Platform.OS === 'ios') {
      return new Promise((resolve, reject) => {
        NativeModules.CarefreeSearchAmap.getLatLong(address);
        eventEmitter.addListener('GetLatLong', (info) => {
          resolve(info);
        });
        eventEmitter.addListener('AddressOrLatLongError', (info) => {
          reject(info);
        });
      });
    }
    else {
      return handleData(await NativeModules.CarefreeSearchAmap.getLatLong(address));
    }
  };
}
export default RNSearchAmap;
