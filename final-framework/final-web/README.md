---
layout: post title: README subtitle: README description: README tags: []
menus:

- README date: 2020-11-11 10:49:52 +800 version: 1.0
---

# final web

## 统一的结果集

通过使用`@RestControllerAdvice`，将`@RestController`返回的结果进行统一的处理，实现结果集格式的统一。

如在`HelloController`中定义如下的`hello`方法:

```java

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(String word) {
        return "hello " + word + "!";
    }

}
```

当访问`/hello?word=final`时，得到的结果如下：

```json
{
  "status": 0,
  "description": "success",
  "code": "0",
  "message": "success",
  "data": "hello final!",
  "trace": "7aba435f-69d2-4c44-a944-315107623a92",
  "timestamp": 1605063263491,
  "duration": 0.063,
  "address": "127.0.0.1:80",
  "locale": "en",
  "timeZone": "Asia/Shanghai",
  "success": true
}
```



