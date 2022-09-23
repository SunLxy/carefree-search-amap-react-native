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
