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
      // map.putString("errInfo", "搜索失败,请检查网络连接");
      map.putString("errInfo", "搜索失败");
    }
    if (type == -4) {
      map.putDouble("errCode", -4);
      map.putString("errInfo", "未设置apiKey,请先设置apiKey");
    }
    return map;
  }

  /**提示信息*/
  private WritableMap getSDKTip(int type) {
    WritableMap map = Arguments.createMap();
    map.putDouble("rCode", type);
    if (type == 1000) {
      map.putString("errInfo", "请求正常");
    }
    if (type == 1001) {
      map.putString("errInfo", "开发者签名未通过");
    }
    if (type == 1002) {
      map.putString("errInfo", "用户Key不正确或过期");
    }
    if (type == 1003) {
      map.putString("errInfo", "没有权限使用相应的接口");
    }
    if (type == 1008) {
      map.putString("errInfo", "Key鉴权未通过");
    }
    if (type == 1009) {
      map.putString("errInfo", "请求Key与绑定平台不符");
    }
    if (type == 1012) {
      map.putString("errInfo", "权限不足，服务请求被拒绝");
    }
    if (type == 1013) {
      map.putString("errInfo", "该Key被删除");
    }
    if (type == 1100) {
      map.putString("errInfo", "引擎服务响应错误");
    }
    if (type == 1101) {
      map.putString("errInfo", "引擎返回数据异常");
    }
    if (type == 1102) {
      map.putString("errInfo", "高德服务端请求链接超时");
    }
    if (type == 1103) {
      map.putString("errInfo", "读取服务结果返回超时");
    }
    if (type == 1200) {
      map.putString("errInfo", "请求参数非法");
    }
    if (type == 1201) {
      map.putString("errInfo", "请求条件中，缺少必填参数");
    }
    if (type == 1202) {
      map.putString("errInfo", "服务请求协议非法");
    }
    if (type == 1203) {
      map.putString("errInfo", "服务端未知错误");
    }
    if (type == 1800) {
      map.putString("errInfo", "服务端新增错误");
    }
    if (type == 1801) {
      map.putString("errInfo", "协议解析错误");
    }
    if (type == 1802) {
      map.putString("errInfo", "socket 连接超时 - SocketTimeoutException");
    }
    if (type == 1803) {
      map.putString("errInfo", "url异常 - MalformedURLException");
    }
    if (type == 1804) {
      map.putString("errInfo", "未知主机 - UnKnowHostException");
    }
    if (type == 1806) {
      map.putString("errInfo", "http或socket连接失败 - ConnectionException");
    }
    if (type == 1900) {
      map.putString("errInfo", "未知错误");
    }
    if (type == 1901) {
      map.putString("errInfo", "参数无效");
    }
    if (type == 1902) {
      map.putString("errInfo", "IO 操作异常 - IOException");
    }
    if (type == 1903) {
      map.putString("errInfo", "空指针异常 - NullPointException");
    }
    if (type == 2000) {
      map.putString("errInfo", "Tableid格式不正确");
    }
    if (type == 2001) {
      map.putString("errInfo", "数据ID不存在");
    }
    if (type == 2002) {
      map.putString("errInfo", "云检索服务器维护中");
    }
    if (type == 2003) {
      map.putString("errInfo", "Key对应的tableID不存在");
    }
    if (type == 2100) {
      map.putString("errInfo", "找不到对应的userid信息");
    }
    if (type == 2101) {
      map.putString("errInfo", "App Key未开通“附近”功能");
    }
    if (type == 2200) {
      map.putString(
        "errInfo",
        "在开启自动上传功能的同时对表进行清除或者开启单点上传的功能"
      );
    }
    if (type == 2201) {
      map.putString("errInfo", "USERID");
    }
    if (type == 2202) {
      map.putString("errInfo", "NearbyInfo对象为空");
    }
    if (type == 2203) {
      map.putString("errInfo", "两次单次上传的间隔低于7秒");
    }
    if (type == 2204) {
      map.putString("errInfo", "Point为空，或与前次上传的相同");
    }
    if (type == 3000) {
      map.putString(
        "errInfo",
        "规划点（包括起点、终点、途经点）不在中国陆地范围内"
      );
    }
    if (type == 3001) {
      map.putString("errInfo", "规划点（包括起点、终点、途经点）附近搜不到路");
    }
    if (type == 3002) {
      map.putString("errInfo", "路线计算失败，通常是由于道路连通关系导致");
    }
    if (type == 3003) {
      map.putString("errInfo", "步行算路起点、终点距离过长导致算路失败。");
    }
    if (type == 4000) {
      map.putString("errInfo", "短串分享认证失败");
    }
    if (type == 4001) {
      map.putString("errInfo", "短串请求失败");
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
            try{
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
                  map.putString("adCode", destPoint.getAdcode());
                  map.putString("building", destPoint.getBuilding());
                  map.putString("city", destPoint.getCity());
                  // map.putString("cityCode", destPoint.getCitycode());
                  map.putString("country", destPoint.getCountry());
                  map.putString("district", destPoint.getDistrict());
                  map.putString("address", destPoint.getFormatAddress());
                  map.putString("neighborhood", destPoint.getNeighborhood());
                  map.putString("province", destPoint.getProvince());
                  map.putString("postcode", destPoint.getPostcode());
                  map.putString("level", destPoint.getLevel());
                  map.putDouble("errCode", rCode);
                  map.putDouble("rCode", rCode);
                  map.putMap("sdkMessage", getSDKTip(rCode));
                  promise.resolve(map);
                } else {
                  WritableMap tips = getTip(-1);
                  tips.putDouble("rCode", rCode);
                  tips.putMap("sdkMessage", getSDKTip(rCode));
                  promise.resolve(tips);
                }
              } else {
                WritableMap tips = getTip(-2);
                tips.putDouble("rCode", rCode);
                tips.putMap("sdkMessage", getSDKTip(rCode));
                promise.resolve(tips);
              }
            }catch(Exception err){
                WritableMap tips = getTip(-3);
                tips.putString("errInfo", err.getMessage());
                tips.putDouble("errCode", -3);
                promise.resolve(tips);
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
            try{
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
                  map.putDouble("rCode", rCode);
                  map.putMap("sdkMessage", getSDKTip(rCode));
                  promise.resolve(map);
                } else {
                  WritableMap tips = getTip(-1);
                  tips.putDouble("rCode", rCode);
                  tips.putMap("sdkMessage", getSDKTip(rCode));
                  promise.resolve(tips);
                }
              } else {
                WritableMap tips = getTip(-2);
                tips.putDouble("rCode", rCode);
                tips.putMap("sdkMessage", getSDKTip(rCode));
                promise.resolve(tips);
              }
            }catch(Exception err){
                WritableMap tips = getTip(-3);
                tips.putString("errInfo", err.getMessage());
                tips.putDouble("errCode", -3);
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
