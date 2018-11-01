# final data json

A simple and powerful json api framework.

## Get Started

### Import Dependency

```xml
<dependency>
    <groupId>cn.com.likly.finalframework</groupId>
    <artifactId>final-data-json</artifactId>
    <version>${project.version}</version>
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