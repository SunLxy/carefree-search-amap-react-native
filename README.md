# carefree-search-amap-react-native

React Native 高德地图搜索模块，支持 Android/iOS。进行地址和经纬度互转
### Install

```bash
npm install carefree-search-amap-react-native
```

### API

**initSDK**

设置高德key

```js
import CarefreeSearchAmap from 'carefree-search-amap-react-native';

CarefreeSearchAmap.initSDK("07976cdaf75c89e7a455f8dd3f3ec56e")

```


**getAddress**

经纬度转地址

```js
import CarefreeSearchAmap from 'carefree-search-amap-react-native';

CarefreeSearchAmap.getAddress({latitude:11.123456,longitude:3.123456})
```

**getLatLong**

地址转经纬度

```js
import CarefreeSearchAmap from 'carefree-search-amap-react-native';

CarefreeSearchAmap.getLatLong("北京天安门广场")

```

### 错误码(errCode)

|错误码|说明|
|-----|----|
| -1  | 没有搜索到相关数据   |
| -2  | 搜索失败,请检查网络连接   |
| -3  |  查询报错信息  |
| -4  | 未设置apiKey   |
| 1000  | 查询成功   |