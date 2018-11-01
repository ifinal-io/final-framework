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
public void convertObjectToJson(){
    Object object = ...;
    String json = Json.toJson(object);
}
```

* Parse Json to Object

```java
@Test
public void parseJsonToObject(){
    String json = "{\"name\":\"xiaoming\",\"age\":20}";
    Person person = Json.parse(json,Person.class );
}
```

* Parse Json to Collection
```java
@Test
public void parseJsonToCollection(){
    String json = "[1,2,3]";
    //parse to list
    List<Interger> list = Json.parse(json,List.class,Interger.class);
    // parse to set
    Set<Interger> set = Json.parse(json,Set.class,Set.class); 
}
```