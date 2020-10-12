module.exports = {
  devServer: {
    port: 9000,
    proxy: {
      '/websocket': {
        target: 'ws://localhost:8080',
        ws: true,
      }
    }
  },
};
