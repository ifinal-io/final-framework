# README

实现SPI的自动配置，即自动生成目标文件`META-INF/{path}/{service}`，其内容为`{instance}`的全路径限定名，如果`instance`指定了`name`
则内容为`name={instance}`。