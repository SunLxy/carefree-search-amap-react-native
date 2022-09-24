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

const setApiKey =async ()=>{
  try{
    const result = await CarefreeSearchAmap.initSDK("07976cdaf75c89e7a455f8dd3f3ec56e")
    if(result){
      console.log("设置apiKey成功")
    }
  }catch(err){
    console.log(err)
  }
}

```

**getAddress**

经纬度转地址

```js
import CarefreeSearchAmap from 'carefree-search-amap-react-native';

const getAddress =async ()=>{
  try{
    const result = await CarefreeSearchAmap.getAddress({latitude:11.123456,longitude:3.123456})
    if(result&&result.errCode===1000){
      console.log("打印参数--->",result)
    }else{
      console.log(result.errInfo)
    }
  }catch(err){
    console.log(err)
  }
}

```

**getLatLong**

地址转经纬度

```js
import CarefreeSearchAmap from 'carefree-search-amap-react-native';

const getLatLong =async ()=>{
  try{
    const result = await CarefreeSearchAmap.getLatLong("北京天安门广场")
    if(result&&result.errCode===1000){
      console.log("打印参数--->",result)
    }else{
      console.log(result.errInfo)
    }
  }catch(err){
    console.log(err)
  }
}

```

### 返回参数说明

|字段|类型|说明|
|---|---|---|
|errCode|`number`|错误码|
|errInfo|`string`|错误信息|
|latitude|`number`|纬度|
|longitude|`number`|经度|
|country|`string`|国家名称|
|postcode|`string`|国家简码|
|province|`string`|省名称、直辖市的名称|
|city|`string`|城市名称|
|cityCode|`string`|城市编码|
|district|`string`|区（县）名称|
|adCode| `string`|区域编码|
|address|`string`|详细地址|
|building|`string`|建筑物名称|
|neighborhood|`string`|社区名称|
|township|`string`|乡镇名称|
|towncode|`string`|乡镇街道编码|
|streetNumber|`string`|门牌号码|
|street|`string`|门牌信息中的街道名称|

### 错误码(errCode)

|错误码|说明|
|-----|----|
| -1  | 没有搜索到相关数据   |
| -2  | 搜索失败,请检查网络连接   |
| -3  |  查询报错信息  |
| -4  | 未设置apiKey   |
| 1000  | 查询成功   |