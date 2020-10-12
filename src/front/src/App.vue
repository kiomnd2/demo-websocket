<template>
  <div>

  </div>
</template>

<script>
import websocket from './websocket/ws.js';

export default {
  name: 'App',
  created() {
      websocket.onopen = function(message) {
        console.log("connected !! ", message);
      };
  }

}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
