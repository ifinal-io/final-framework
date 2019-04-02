# Final Json

A simple and powerful json api framework.

## Get Started

### Import Dependency

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-json</artifactId>
    <version>0.0.6-SNAPSHOT</version>
</dependency>
```

### Use Json

* Convert Object to Json

```java
@Test
public void testToJson() {
    final JsonBean bean = new JsonBean();
    bean.setName("xiaoMing");
    bean.setAge(20);
    logger.info("bean={}", bean);
    final String json = Json.toJson(bean);
    logger.info(json);//{"name":"xiaoMing","age":20}
}
```

* Parse Json to Object

```java
public void testParseObject() {
    final JsonBean bean = new JsonBean();
    bean.setName("xiaoMing");
    bean.setAge(20);
    logger.info("bean={}", bean);
    final String json = Json.toJson(bean);
    logger.info(json);
    final JsonBean result = Json.parse(json, JsonBean.class);
    logger.info("result={}", result);//result=JsonBean(name=xiaoMing, age=20)
}
```

* Parse Json to Collection
```java
@Test
public void testParseCollection() {
    final String json = Json.toJson(Arrays.asList(1, 2, 3, 2, 1));
    //parse to list
    List<Integer> list = Json.parse(json, List.class, int.class);
    logger.info("list={}", list);// list=[1,2,3,2,1]
    //parse to set
    Set<Integer> set = Json.parse(json, Set.class, int.class);
    logger.info("set={}", set);// set=[1,2,3]
}
```

## Json Core

**Json Util**

* `Json.toJson(Object)`：把数据序列化为`json`串。
* `Json.parse(String,Class)`：把`json`串反序列化为`JavaBean`对象。
* `Json.parse(String,Type)`：把`json`串反序列化为`Type`所代表的`JavaBean`对象。
* `Json.parse(String,TypeReference)`：把`json`串反序列化为`TypeReference`所表示的`JavaBean`对象。
* `Json.parse(String,Class<? extend Collection>,Class)`：把`json`串反序列化为`List`或`Set`等`Collection`的子类。

**Json Lib**

* [`jackson`](src/main/java/com/ilikly/finalframework/json/jackson/JacksonJsonService.java): 默认的JsonService实现
* [`fastjson`](src/main/java/com/ilikly/finalframework/json/fastjson/FastJsonService.java):
* [`gson`](src/main/java/com/ilikly/finalframework/json/gson/GsonJsonService.java):

**Custom Json Lib**

1. Implement [`JsonService`](src/main/java/com/ilikly/finalframework/json/JsonService.java) Interface
2. Register `JsonService` to [`JsonRegistry`](src/main/java/com/ilikly/finalframework/json/JsonRegistry.java)

