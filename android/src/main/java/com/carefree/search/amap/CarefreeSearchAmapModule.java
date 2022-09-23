// CarefreeSearchAmapModule.java

package com.carefree.search.amap;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.ServiceSettings;
import com.amap.api.services.geocoder.*;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

public class CarefreeSearchAmapModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;

  /* 搜索实例 */
  private GeocodeSearch mGeocodeSearch;

  public CarefreeSearchAmapModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  /**
   * 初始化sdk
   * @param apiKey
   */
  @ReactMethod
  public void initSDK(String apiKey, final Promise promise) {
    try {
      ServiceSettings.updatePrivacyAgree(reactContext, true);
      ServiceSettings.updatePrivacyShow(reactContext, true, true);
      // 获取实例
      ServiceSettings serviceSettings = ServiceSettings.getInstance();
      serviceSettings.setApiKey(apiKey);
      /* 初始化地址编码搜索 */
      mGeocodeSearch = new GeocodeSearch(reactContext.getApplicationContext());
      promise.resolve(true);
    } catch (Exception err) {
      promise.reject(err.getMessage());
    }
  }

  @Override
  public String getName() {
    return "CarefreeSearchAmap";
  }

  /**提示信息*/
  private WritableMap getTip(int type) {
    WritableMap map = Arguments.createMap();
    if (type == -1) {
      map.putDouble("errCode", -1);
      map.putString("errInfo", "没有搜索到相关数据");
    }
    if (type == -2) {
      map.putDouble("errCode", -2);
      map.putString("errInfo", "搜索失败,请检查网络连接");
    }
    if (type == -4) {
      map.putDouble("errCode", -4);
      map.putString("errInfo", "未设置apiKey,请先设置apiKey");
    }
    return map;
  }

  /**
   * 地址转换经纬度
   * @param address
   */
  @ReactMethod
  public void getLatLong(String address, final Promise promise) {
    if (mGeocodeSearch == null) {
      promise.resolve(getTip(-4));
      return;
    }
    try {
      // 第一个参数表示地址，第二个参数表示查询城市010，中文或者中文全拼，citycode、adcode
      GeocodeQuery query = new GeocodeQuery(address, "");
      // 设置同步地理编码请求
      mGeocodeSearch.getFromLocationNameAsyn(query);
      mGeocodeSearch.setOnGeocodeSearchListener(
        new GeocodeSearch.OnGeocodeSearchListener() {

          @Override
          public void onRegeocodeSearched(
            RegeocodeResult regeocodeResult,
            int i
          ) {}

          /**
           * 地理编码查询回调
           */
          @Override
          public void onGeocodeSearched(GeocodeResult result, int rCode) {
            if (rCode == 1000) {
              if (
                result != null &&
                result.getGeocodeAddressList() != null &&
                result.getGeocodeAddressList().size() > 0
              ) {
                GeocodeAddress destPoint = result
                  .getGeocodeAddressList()
                  .get(0);
                WritableMap map = Arguments.createMap();
                map.putDouble(
                  "latitude",
                  destPoint.getLatLonPoint().getLatitude()
                );
                map.putDouble(
                  "longitude",
                  destPoint.getLatLonPoint().getLongitude()
                );
                map.putDouble("errCode", rCode);
                promise.resolve(map);
              } else {
                WritableMap tips = getTip(-1);
                tips.putDouble("errCode", rCode);
                // ToastUtil.show(mContext, "对不起，没有搜索到相关数据！");
                promise.resolve(tips);
              }
            } else {
              WritableMap tips = getTip(-2);
              tips.putDouble("errCode", rCode);
              promise.resolve(tips);
              //   ToastUtil.show(mContext, "搜索失败,请检查网络连接！");
            }
          }
        }
      );
    } catch (Exception err) {
      WritableMap tips = getTip(-3);
      tips.putString("errInfo", err.getMessage());
      tips.putDouble("errCode", -3);
      promise.resolve(tips);
    }
  }

  /**
   * 经纬度转换地址
   * @param point
   */
  @ReactMethod
  public void getAddress(ReadableMap point, final Promise promise) {
    if (mGeocodeSearch == null) {
      promise.resolve(getTip(-4));
      return;
    }
    try {
      LatLonPoint latLonPoint = new LatLonPoint(
        point.getDouble("latitude"),
        point.getDouble("longitude")
      );

      // GeocodeSearch mGeocodeSearch = new GeocodeSearch(reactContext);
      // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
      RegeocodeQuery query = new RegeocodeQuery(
        latLonPoint,
        100,
        GeocodeSearch.AMAP
      );
      // 设置同步逆地理编码请求
      mGeocodeSearch.getFromLocationAsyn(query);
      mGeocodeSearch.setOnGeocodeSearchListener(
        new GeocodeSearch.OnGeocodeSearchListener() {

          /**
           * 逆地理编码回调
           */
          @Override
          public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
            if (rCode == 1000) {
              if (
                result != null &&
                result.getRegeocodeAddress() != null &&
                result.getRegeocodeAddress().getFormatAddress() != null
              ) {
                RegeocodeAddress location = result.getRegeocodeAddress();
                WritableMap map = Arguments.createMap();
                map.putString("adCode", location.getAdCode());
                map.putString("building", location.getBuilding());
                map.putString("city", location.getCity());
                map.putString("cityCode", location.getCityCode());
                map.putString("country", location.getCountry());
                map.putString("district", location.getDistrict());
                map.putString("address", location.getFormatAddress());
                map.putString("neighborhood", location.getNeighborhood());
                map.putString("province", location.getProvince());
                if (location.getStreetNumber() != null) {
                  map.putString(
                    "streetNumber",
                    location.getStreetNumber().getNumber()
                  );
                  map.putString(
                    "street",
                    location.getStreetNumber().getStreet()
                  );
                }
                map.putString("towncode", location.getTowncode());
                map.putString("township", location.getTownship());
                map.putDouble("errCode", rCode);

                promise.resolve(map);
              } else {
                WritableMap tips = getTip(-1);
                tips.putDouble("errCode", rCode);
                promise.resolve(tips);
              }
            } else {
              WritableMap tips = getTip(-2);
              tips.putDouble("errCode", rCode);
              promise.resolve(tips);
            }
          }

          /**
           * 地理编码查询回调
           */
          @Override
          public void onGeocodeSearched(GeocodeResult result, int rCode) {}
        }
      );
    } catch (Exception err) {
      WritableMap tips = getTip(-3);
      tips.putString("errInfo", err.getMessage());
      tips.putDouble("errCode", -3);
      promise.resolve(tips);
    }
  }
}
