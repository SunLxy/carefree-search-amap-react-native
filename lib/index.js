"use strict";

var _interopRequireDefault = require("@babel/runtime/helpers/interopRequireDefault")["default"];

Object.defineProperty(exports, "__esModule", {
  value: true
});
var _exportNames = {};
exports["default"] = void 0;

var _createClass2 = _interopRequireDefault(require("@babel/runtime/helpers/createClass"));

var _classCallCheck2 = _interopRequireDefault(require("@babel/runtime/helpers/classCallCheck"));

var _defineProperty2 = _interopRequireDefault(require("@babel/runtime/helpers/defineProperty"));

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
var CarefreeSearchAmap = /*#__PURE__*/(0, _createClass2["default"])(function CarefreeSearchAmap() {
  (0, _classCallCheck2["default"])(this, CarefreeSearchAmap);
});
(0, _defineProperty2["default"])(CarefreeSearchAmap, "initSDK", function (apiKey) {
  return _reactNative.NativeModules.CarefreeSearchAmap.initSDK(apiKey);
});
(0, _defineProperty2["default"])(CarefreeSearchAmap, "getAddress", function (point) {
  if (_reactNative.Platform.OS === 'ios') {
    return new Promise(function (resolve, reject) {
      _reactNative.NativeModules.CarefreeSearchAmap.getAddress(point.latitude, point.longitude);

      eventEmitter.addListener('GetAddress', function (info) {
        if (!info.errCode) {
          resolve(info);
        } else {
          reject(info);
        }
      });
      eventEmitter.addListener('AddressOrLatLongError', function (info) {
        reject(info);
      });
    });
  } else {
    return _reactNative.NativeModules.CarefreeSearchAmap.getAddress(point);
  }
});
(0, _defineProperty2["default"])(CarefreeSearchAmap, "getLatLong", function (address) {
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
      eventEmitter.addListener('AddressOrLatLongError', function (info) {
        reject(info);
      });
    });
  } else {
    return _reactNative.NativeModules.CarefreeSearchAmap.getLatLong(address);
  }
});
var _default = CarefreeSearchAmap;
exports["default"] = _default;
//# sourceMappingURL=index.js.map