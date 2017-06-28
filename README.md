# 项目说明
自建`SSM`脚手架(Maven),`借鉴github,oschina社区热门框架`,集成最优秀的`mybatis工具(Mapper,PageHelper)`
框架简单目标是使用当下流行优秀的套件组合,适合新项目的快速开发,精小稳定。
另一面,适应`前后端分离`的服务端框架.

# 技术组成
- 核心框架：Spring Framework 4.1
- 安全框架：Apache Shiro 1.2
- 视图框架：Spring MVC 4.1
- 服务端验证：Hibernate Validator 5.2
- 布局框架：SiteMesh 2.4
- 工作流引擎：Activiti 5.21
- 任务调度：Spring Task 4.1
- 持久层框架：MyBatis 3.2
- 数据库连接池：Alibaba Druid 1.0
- 缓存框架：Ehcache 2.6、Redis
- 日志管理：SLF4J 1.7、Log4j
- 工具类：Apache Commons、Jackson 2.2、Xstream 1.4、Dozer 5.3、POI 3.9

- Mybatis工具
    - Mapper
    - Mybatis_PageHelper
    - MyBatis Generator

- 前端(从此项目中分离出来)`前后端分离`
    - angular / Vue + Element / React / jquery + Bootstrap
    
# 搭建初始,使用
- Maven pom.xml初始化`dependencies`
- 数据库初始化`country.sql`,连接地址更新`config.properties`
- tomcat部署`bright:war exploded`
- 代码更新,刷新服务：`Build > Build Artifact > bright:war exploded`
- 首页预览
    - 数据访问测试 demo
    - 权限控制测试 home.html
    - 一.API接口访问(返回json)
    - 二.动静态访问各位置页面差异

![](http://7xnbs3.com1.z0.glb.clouddn.com/17-3-10/93450546-file_1489145987589_11b12.png)
![](http://7xnbs3.com1.z0.glb.clouddn.com/17-3-10/54366383-file_1489145789988_15fb7.png)

# 项目使用的Oracle数据库
- 更新驱动和配置`config.properties`
```properties
jdbc.driver=oracle.jdbc.driver.OracleDriver
jdbc.url=jdbc:oracle:thin:@//192.168.3.198:1521/orcl
```
- 更新实体类,主键. (如:`Country`)
```java
/**
 * 主键_mysql
 */
//    @Id
//    @Column(name = "Id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;

/**
 * 主键_oracle
 */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select seq_country.nextval from dual")
private Integer id;
```
- mybatis保证oracle序列先执行`ORDER=BEFORE`
```xml
<!-- @MyBatisDao注解的接口(Mapper<Entity>类支持) -->
<bean id="mapperScannerConfigurer" class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.wosai.bright.mapper"/>
    <!-- 3.2.2版本新特性，markerInterface可以起到mappers配置的作用，详细情况需要看Marker接口类 -->
    <property name="markerInterface" value="com.wosai.bright.common.MyMapper"/>
    <!-- 通用Mapper通过属性注入进行配置，默认不配置时会注册Mapper<T>接口-->
    <property name="properties">
        <value>
            mappers=tk.mybatis.mapper.common.Mapper
            ORDER=BEFORE
        </value>
    </property>
</bean>
```

# maven打包
- 下载,安装`maven` http://maven.apache.org/download.cgi
- 配置环境变量`path` F:\Tool\Maven\apache-maven-3.3.9\bin
- 依赖环境变量`JAVA_HOME` C:\Program Files\Java\jdk1.8.0_91
- 测试`mvn -v`
- 打包`mvn package`
- war包部署测试(可选)`target/bright.war`

# 框架借鉴
- [`ThinkGem / JeeSite`](https://git.oschina.net/thinkgem/jeesite)
- [` 人人开源 / renren-security`](https://git.oschina.net/babaio/renren-security)
- [`abel533/Mybatis-Spring`](https://github.com/abel533/Mybatis-Spring)
    - [`abel533/Mapper`](https://github.com/abel533/Mapper)
    - [`abel533 / Mybatis_PageHelper`](http://git.oschina.net/free/Mybatis_PageHelper)

- 其它
    - [`iBase4J / iBase4J`](https://git.oschina.net/iBase4J/iBase4J)
    - [`青苗 / SpringWind`](https://git.oschina.net/juapk/SpringWind)
    - [baichengzhou/SpringMVC-Mybatis-Shiro-redis-0.2](https://github.com/baichengzhou/SpringMVC-Mybatis-Shiro-redis-0.2)
    
# 查阅
- MyBatis Mapper相关
    - [MyBatis相关工具](http://www.mybatis.tk/)
    - [如何使用通用Mapper](http://git.oschina.net/free/Mapper2/blob/master/wiki/mapper/2.Use.md)
    - [使用Mapper专用的MyBatis Generator插件](http://git.oschina.net/free/Mapper2/blob/master/wiki/mapper/5.UseMBG.md)

- MyBatis PageHelper相关
    - [PageHelper 使用文档](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md)
    - [PageHelper更新日志](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Changelog.md)
    
- MyBatis Generator相关
    - [MyBatis Geneator详解 abel533 / Mybatis_Utils](http://git.oschina.net/free/Mybatis_Utils/blob/master/MybatisGeneator/MybatisGeneator.md)
    - [使用Mapper专用的MyBatis Generator插件](http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/7.UseMBG.md)
    - [在Spring4中使用通用Mapper](http://git.oschina.net/free/Mapper2/blob/master/wiki/mapper/4.Spring4.md)
    - [mybatis-generator界面工具 astarring/mybatis-generator-gui](https://github.com/astarring/mybatis-generator-gui)

- 权限控制相关(shior)
    - [Shiro学习笔记（2）——身份验证之Realm](http://blog.csdn.net/u010837612/article/details/46053249)
    - [Shiro学习笔记（3）——授权（Authorization）](http://blog.csdn.net/u010837612/article/details/46126157)

- 前后端分离相关
    - [Angular中在前后端分离模式下实现权限控制 - 基于RBAC - 顽Shi的Blog](https://my.oschina.net/blogshi/blog/300595)


- 环境搭建
    - [doc/【java脚手架搭建】容器 web.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)
    - [doc/【java脚手架搭建】基础 spring-context.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)
    - [doc/【java脚手架搭建】调度 spring-servlet.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)
    - [doc/【java脚手架搭建】权限 spring-context-shiro.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)
    - [doc/【java脚手架搭建】事务 spring-context-transaction.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)
    - [doc/【java脚手架搭建】注解 spring-context-annotation.xml.md](https://github.com/liuxiang/bright_frame/tree/master/doc)

- 问题处理
    - [doc/【java脚手架搭建】常见问题.md]()
    
# 作者信息
- 作者博客：http://liuxiang.github.io