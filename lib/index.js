"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
var _exportNames = {
  getLatLong: true,
  getAddress: true,
  initSDK: true
};
exports.initSDK = exports.getLatLong = exports.getAddress = exports["default"] = void 0;

var _reactNative = require("react-native");

var _interface = require("./interface");

Object.keys(_interface).forEach(function (key) {
  if (key === "default" || key === "__esModule") return;
  if (Object.prototype.hasOwnProperty.call(_exportNames, key)) return;
  if (key in exports && exports[key] === _interface[key]) return;
  Object.defineProperty(exports, key, {
    enumerable: true,
    get: function get() {
      return _interface[key];
    }
  });
});
var eventEmitter = new _reactNative.NativeEventEmitter(_reactNative.NativeModules.CarefreeSearchAmap);
/**
 * 地址转经纬度
 */

var getLatLong = function getLatLong(address) {
  if (_reactNative.Platform.OS === 'ios') {
    return new Promise(function (resolve, reject) {
      _reactNative.NativeModules.CarefreeSearchAmap.getLatLong(address);

      eventEmitter.addListener('GetLatLong', function (info) {
        if (!info.errCode) {
          resolve(info);
        } else {
          reject(info);
        }
      });
    });
  } else {
    return _reactNative.NativeModules.CarefreeSearchAmap.getLatLong(address);
  }
};
/**
 * 经纬度转地址
 */


exports.getLatLong = getLatLong;

var getAddress = function getAddress(point) {
  if (_reactNative.Platform.OS === 'ios') {
    return new Promise(function (resolve, reject) {
      _reactNative.NativeModules.CarefreeSearchAmap.getAddress(point);

      eventEmitter.addListener('GetAddress', function (info) {
        if (!info.errCode) {
          resolve(info);
        } else {
          reject(info);
        }
      });
    });
  } else {
    return _reactNative.NativeModules.CarefreeSearchAmap.getAddress(point);
  }
};
/**
 * 设置apiKey
 */


exports.getAddress = getAddress;

var initSDK = function initSDK(apiKey) {
  return _reactNative.NativeModules.CarefreeSearchAmap.initSDK(apiKey);
};

exports.initSDK = initSDK;
var _default = _reactNative.NativeModules.CarefreeSearchAmap;
exports["default"] = _default;
//# sourceMappingURL=index.js.map