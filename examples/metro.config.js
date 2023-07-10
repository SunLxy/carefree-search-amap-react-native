const { getDefaultConfig, mergeConfig } = require('@react-native/metro-config');

const path = require("path")

const comConfig = {
  "react-native-app-shared": "../shared/src/index.tsx",
  "carefree-search-amap-react-native": "../core/index.js"
}

const extraNodeModules = {};
const watchFolders = [];
Object.keys(comConfig).forEach((key) => {
  const filepath = comConfig[key];
  extraNodeModules[key] = path.resolve(process.cwd(), filepath);
  if (/(.(js|tsx|ts))$/.test(filepath)) {
    watchFolders.push(path.dirname(extraNodeModules[key]));
  }
});

/**
 * Metro configuration
 * https://facebook.github.io/metro/docs/configuration
 *
 * @type {import('metro-config').MetroConfig}
 */
const config = {
  watchFolders: [...watchFolders],
  resolver: {
    extraNodeModules: new Proxy(extraNodeModules, {
      get: (target, name) => {
        if (Object.keys(target).includes(name.toString()) && typeof target[name] === 'string') {
          return target[name]
        }
        return path.join(process.cwd(), `node_modules/${name.toString()}`)
      },
    })
  }
};
module.exports = mergeConfig(getDefaultConfig(__dirname), config);
