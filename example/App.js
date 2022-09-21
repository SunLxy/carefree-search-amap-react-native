/**
 * Sample React Native App
 *
 * adapted from App.js generated by the following command:
 *
 * react-native init example
 *
 * https://github.com/facebook/react-native
 */

import React, { Component } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  TouchableOpacity,
} from 'react-native';
import CarefreeSearchAmap from '../src';

export default class App extends Component {
  state = {
    address: '上海市青浦区e通世界北区',
    location: '',
    message: '--',
    isLoading: false,
  };
  async componentDidMount() {
    try {
      const result = await CarefreeSearchAmap.initSDK(
        '07976cdaf75c89e7a455f8dd3f3ec56e',
      );
      if (result) {
        this.setState({
          isLoading: result,
        });
        this.getInfo(this.state.address);
      }
    } catch (err) {
      console.log('错误信息----》', err);
    }
  }
  async getInfo(address) {
    const result2 = await CarefreeSearchAmap.getLatlong(address);
    console.log('result2', result2);
    const result3 = await CarefreeSearchAmap.getAddress(result2);
    console.log('result3', result3);
    this.setState({
      location: JSON.stringify(result2),
      message: JSON.stringify(result3),
    });
  }

  render() {
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
            value={this.state.address}
            onChangeText={value => {
              this.setState({ address: value });
            }}
          />
          <TouchableOpacity
            style={{
              width: 45,
              alignItems: 'center',
              justifyContent: 'center',
            }}
            onPress={() => {
              this.getInfo(this.state.address);
            }}>
            <Text>查询</Text>
          </TouchableOpacity>
        </View>
        <Text style={styles.instructions}>
          {this.state.address}经纬度: {this.state.location}
        </Text>
        <Text style={styles.welcome}>
          {this.state.address}经纬度转换获取地址信息
        </Text>
        <Text style={styles.instructions}>{this.state.message}</Text>
      </View>
    );
  }
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
