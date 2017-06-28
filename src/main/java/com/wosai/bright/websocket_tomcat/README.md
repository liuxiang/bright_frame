**参考**
http://blog.csdn.net/cquptcmj/article/details/53455828

- 部署
tomcat 部署含`MyWebSocket.java`的工程

- 测试
web项目,访问页面index.html(index_websocketClient.html)
`http://localhost:8080/websocket_tomcat/index.html`

- 快速测试:(浏览器控制台直接运行代码)
```$xslt
// 访问目标:ws://localhost:8080/<proName>/<websocketName>
var websocket = new WebSocket("ws://localhost:8080/websocket/websocket");
websocket.onopen = function(event) {console.log('连接开启')};
websocket.onmessage = function () { console.log('server response:'+event.data)};
websocket.send('Message');// 待连接完成后,才可调用
```

# 注意
- 依赖jar包的引入方式
```text
<!-- websocket(不可用,需要项目引入当前运行的tomcatlib中添加websocket-api) -->
<!--<dependency>-->
<!--<groupId>org.apache.tomcat</groupId>-->
<!--<artifactId>tomcat-websocket-api</artifactId>-->
<!--<version>8.5.12</version>-->
<!--</dependency>-->
```
- 请求过滤器的权限控制shiro
```text
/websocket = anon
```