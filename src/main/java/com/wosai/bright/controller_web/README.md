# web应用访问controller控制器
- 浏览器环境
    - 此后端分离出的web前端(angular/vue)
    - 此后端中的普通静态页,需要访问后端
        - webapp/*.html
        - webapp/static/**/*.html
    - web app/hybrid app(浏览器环境的手机客户端)

- 统一前缀:`/webService/模块名/**`