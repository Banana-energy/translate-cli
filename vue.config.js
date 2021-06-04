const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const name = '翻译平台'

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  productionSourceMap: false,
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  },
  css: {
    loaderOptions: {
      postcss: {
        plugins: [
          require('postcss-plugin-px2rem')({
            rootValue: 25.6,
            mobileRootValue: 21.33,
            unitPrecision: 5,
            exclude: /node_modules/i,
            replace: true,
            mediaQuery: false,
            minPixelValue: 3
          })
        ]
      }
    }
  }
}
