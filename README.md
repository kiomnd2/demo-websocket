# demo-websocket
> 간단한 웹소켓 푸싱 서버, 클라이언트 입니다.


### 1. 기능
* 클라이언트는 서버와 연결될 때까지 5초마다 재시도 합니다
* 클라이언트는 서버와 연결 후 텍스트틀 입력할 수 있습니다.
  * 입력된 텍스트는 같은 서버로 에코 합니다.
* Http로 서버에 message 를 입력하면 웹소캣으로 연결된 서버에 푸싱 메세지를 보냅니다


### 2. 실행방법
#### server
* src/main/java/com/kiomnd2/websocket/WebSoocket.java 를 컴파일 후 실행합니다

#### client
* cd src/front
* npm run serve
