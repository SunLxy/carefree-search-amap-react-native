import { NativeModules } from 'react-native'
const { CarefreeSearchAmap } = NativeModules

export const getLatlong = (
  address: string,
): Promise<{ latitude: number; longitude: number }> => {
  return CarefreeSearchAmap.getLatlong(address)
}

export default CarefreeSearchAmap
