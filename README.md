# BMS-BE [![Build Status](https://travis-ci.org/MengStar/BMS-BE.svg?branch=develop)](https://travis-ci.org/MengStar/BMS-BE)
基于springboot的考试项目（后台）


[前端项目地址](https://github.com/MengStar/lanou)
[后台微服务重构](https://github.com/MengStar/micro-sevice-lanou)

## 特性

- 基于[springboot](https://spring.io/guides)构建的[restful](http://www.ruanyifeng.com/blog/2014/05/restful_api.html)风格后台
- 基于[spring-security](http://docs.spring.io/spring-security/site/docs/5.0.0.M3/reference/htmlsingle/)的api权限管理
- 支持[cros](http://www.ruanyifeng.com/blog/2016/04/cors.html)跨域访问
- 支持[docker](https://baike.baidu.com/item/Docker/13344470?fr=aladdin)容器部署

## 环境

* java8
* maven

## 安装

```
通过 maven 安装 pom.xml 中定义的依赖  
```
- 使用IntelliJ IDEA 配置Maven [参考](http://blog.csdn.net/qq_32588349/article/details/51461182)

## 运行

- springboot自带tomcat容器，故和普通jar程序运行方式一样

- 在IDE中直接启动``EndFontApplication``下的``main``方法

- 或者通过 maven ``package`` 打包成 ``XXXX.jar``后，通过``java -jar XXXX.jar``运行

- 运行成功后，访问[http://localhost:8080/api/v1](http://localhost:8080/api/v1),可以看到``hello world!``

## 注意

- `src/java/meng/xing/api` 类下的为controller
- `auth` 下为用户登录、注册接口，不需要权限
-  其余接口需要认证
-  具体权限认证，参考 `security` 下的代码
