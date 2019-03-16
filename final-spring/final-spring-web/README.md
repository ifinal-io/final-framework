# final spring web

## ImportResource
[`SpringResourceConfigurer`](src/main/java/org/finalframework/spring/web/configurer/SpringResourceConfigurer.java)

默认加载如下路径下的`xml`配置文件：

* `classpath*:spring-config-*.xml`
* `classpath*:config/spring-config-*.xml`
* `classpath*:spring/spring-config-*.xml`

## RequestJsonParam

自定义`RequestJsonParam`参数解析器，使表单支持`name=json`的格式提交数据。

