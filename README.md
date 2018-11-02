# final-framework

## Modules

### Json

提供统一的`Json`操作`API`，并支持多种`Json`库，如`jackson`、`fastjson`、`gson`。

**Json API**

* `Json.toJson(Object)`：把数据序列化为`json`串。
* `Json.parse(String,Class)`：把`json`串反序列化为`JavaBean`对象。
* `Json.parse(String,Class<? extend Collection>,Class)`：把`json`串反序列化为`List`或`Set`等`Collection`的子类。

**Json Lib**

* `jackson`:
* `fastjson`:
* `gson`: