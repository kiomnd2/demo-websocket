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
      <div style="padding: 15px">
        <md-field>
          <label>Text</label>
          <md-input :disabled="connected" v-model="message" @keyup.enter.prevent="sendMessage"></md-input>
        </md-field>
      </div>
      <div>
        <md-dialog-alert
          :md-active.sync="active"
          :md-content="receiveMessage" />
      </div>
    </div>
</template>

<script>
export default {
  name: 'App',
  data () {
    return {
      state: 'md-default',
      ws: null,
      connected: false,
      message: '',
      receiveMessage: '',
      active: false,
      delay: 1000
    }
  },
  methods: {
    init () {
      this.ws = new WebSocket('ws://localhost:9999/websocket')
      this.onOpen()
      this.onClose()
      this.onMessage()
    },
    onOpen () {
      this.ws.onopen = (message) => {
        clearTimeout(this.reconnecting())
        console.log('connected!!')
        this.state = 'md-primary'
        console.log(message)
        this.connected = false
      }
    },
    onClose () {
      this.ws.onclose = (message) => {
        console.log('closed')
        this.reconnecting()
        this.state = 'md-default'
        console.log(message)
        this.connected = true
      }
    },
    onMessage () {
      this.ws.onmessage = (dat) => {
        console.log('receive: ', dat.data)
        this.active = true
        this.receiveMessage = dat.data
      }
    },
    reconnecting () {
      return setTimeout(() => {
        console.log('연결을 재시도 합니다')
        this.init()
      }, 5000)
    },
    sendMessage () {
      console.log('send: ', this.message)
      this.ws.send(this.message)
      this.message = ''
    }
  },
  created () {
    this.init()
  }
}
</script>

<style>
</style>
