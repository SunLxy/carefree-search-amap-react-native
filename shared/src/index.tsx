/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, { useEffect, useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Platform
} from 'react-native';

import CarefreeSearchAmap from 'carefree-search-amap-react-native';

function App(): JSX.Element {

  const [state, setState] = useState({
    address: '上海市青浦区e通世界北区',
    location: '',
    message: '--',
  })

  const componentDidMount = async () => {
    try {
      console.log('222');
      const result = await CarefreeSearchAmap.initSDK(
        Platform.select({
          ios: '7847002b4f7fa42578df07d8cf9b0e41',
          android: 'bf2300da74937053d31bae65e0367e14',
        }),
      );
      console.log('result', result);
      if (result === true) {
        getInfo(state.address);
      }
    } catch (err) {
      console.log('错误信息----》', err);
    }
  }
  useEffect(() => {
    componentDidMount()
  }, [])

  const getInfo = async (address: string) => {
    const result2 = await CarefreeSearchAmap.getLatLong(address);
    console.log('result2', result2);
    // 31.200345847881664 121.26299297628496
    const result3 = await CarefreeSearchAmap.getAddress({
      latitude: 31.200345847881664,
      longitude: 121.26299297628496,
    });
    console.log('result3', result3);
    setState({
      ...state,
      location: JSON.stringify(result2),
      message: JSON.stringify(result3),
    })
  }

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>☆CarefreeSearchAmap example☆</Text>
      <View
        style={{
          flexDirection: 'row',
          borderWidth: 1,
          borderColor: '#ccc',
          alignItems: 'center',
        }}>
        <TextInput
          style={{ flex: 1 }}
          value={state.address}
          onChangeText={value => setState({ ...state, address: value })}
        />
        <TouchableOpacity
          style={{
            width: 45,
            alignItems: 'center',
            justifyContent: 'center',
          }}
          onPress={() => {
            getInfo(state.address);
          }}>
          <Text>查询</Text>
        </TouchableOpacity>
      </View>
      <Text style={styles.instructions}>
        {state.address}经纬度: {state.location}
      </Text>
      <Text style={styles.welcome}>
        {state.address}经纬度转换获取地址信息
      </Text>
      <Text style={styles.instructions}>{state.message}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

export default App;
