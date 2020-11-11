# final json



`final-json`定义了统一的`Json`序列化与反序列化接口，默认使用`jackson`实现，并绞架了对**日期**、**枚举**等特定数据类型的序列化流程，丰富了`Json`的序列化内容，使`Json`更加易读。



## 增强的数据类型

### 日期

`final-json`对基本的日期类型进行了增强，对于原生日期类型属性，其默认序列化为其对应的时间戳，并为其增加格式为`***Format`的扩展属性，方便开发人员理解时间戳对应的值。

定义如下的Bean：

```java
@Data
public class DateBean {
    private Date date;
    private LocalDateTime localDateTime;
}
```

序列化后为：

```json
{
    "date":1605059845585,
    "dateFormat":"2020-11-11 09:57:25",
    "localDateTime":1605059845603,
    "localDateTimeFormat":"2020-11-11 09:57:25"
}
```

### 枚举

定义如下Bean：

```java
@Data
static class EnumBean{
    private YN yn = YN.YES;
}
```

序列化后为：

```json
{
    "yn":1,
    "ynName":"YES",
    "ynDesc":"有效"
}
```

