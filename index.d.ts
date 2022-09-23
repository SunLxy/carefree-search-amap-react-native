export interface Point {
  /**错误码*/
  errCode?: number | string
  /**纬度*/
  latitude: number
  /**经度*/
  longitude: number
}
export interface Address {
  /**错误码*/
  errCode?: number | string
  /**区（县）的编码*/
  adCode: string
  /**建筑物名称*/
  building: string
  /**城市名称*/
  city: string
  /**城市编码*/
  cityCode: string
  /**国家名称*/
  country: string
  /**区（县）名称*/
  district: string
  /**地址*/
  address: string
  /**社区名称*/
  neighborhood: string
  /**省名称、直辖市的名称 */
  province: string
  /**乡镇街道编码*/
  towncode: string
  /**乡镇名称*/
  township: string
  /**门牌号码*/
  streetNumber?: string
  /**门牌信息中的街道名称*/
  street?: string
}
declare class CarefreeSearchAmap {
  /**
   * 设置apiKey
   */
  static initSDK: (apiKey: string) => Promise<boolean | string>
  /**
   * 经纬度转地址
   */
  static getAddress: (point: Point) => Promise<Address>
  /**
   * 地址转经纬度
   */
  static getLatLong: (address: string) => Promise<Point>
}
export default CarefreeSearchAmap
