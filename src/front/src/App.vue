<template>
    <div class="page-container">
      <md-app md-mode="reveal">
        <md-app-toolbar :class="state">
          <span class="md-title">Websocket Test</span>
        </md-app-toolbar>
        <md-app-content>
          <div v-if="connected">
            <md-progress-bar md-mode="indeterminate"></md-progress-bar>
            <md-progress-bar class="md-accent" md-mode="indeterminate"></md-progress-bar>
          </div>
        </md-app-content>
      </md-app>
    </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      state: 'md-default',
      connected: false
    }
  },
  methods: {
    init () {
      const ws = new WebSocket('ws://localhost:9999/websocket')
      this.onOpen(ws)
      this.onClose(ws)
    },
    onOpen (ws) {
      ws.onopen = (message) => {
        clearTimeout(this.reconnecting())
        console.log('connected!!')
        this.state = 'md-primary'
        console.log(message)
        this.connected = false
      }
    },
    onClose (ws) {
      ws.onclose = (message) => {
        console.log('closed')
        this.reconnecting()
        this.state = 'md-default'
        console.log(message)
        this.connected = true
      }
    },
    reconnecting () {
      return setTimeout(() => {
        console.log('연결을 재시도 합니다')
        this.init()
      }, 5000)
    }cd
    ls
  },
  created () {
    this.init()
  }
}
</script>

<style>
</style>
