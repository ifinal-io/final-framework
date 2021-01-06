---
layout: post title: 统一的结果集 subtitle: result description: result tags: []
menus:

- result date: 2020-11-11 15:46:17 +800 version: 1.0

---

## 统一的结果集

你在`@RestController`中定义的方法：

```java

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping
    @MonitorAction("${'{访问Hello} ' + #word}")
    public String hello(String word) {
        return "hello " + word + "!";
    }

}
```

你在访问`/hello?word=final`时，得到的结果：

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

## 全局异常处理

你在系统中抛出的异常：

```java

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/ex")
    public void ex(Integer code, String message) {
        throw new ServiceException(code, message);
    }

}
```

你在访问`/hello/ex?code=123&message=exception`时，得到的结果：

```json
{
  "status": 123,
  "description": "exception",
  "code": "123",
  "message": "exception",
  "data": null,
  "pagination": null,
  "trace": "75c4c483-f337-415c-94a8-1c039665034f",
  "timestamp": 1605081576013,
  "duration": "PT0.138S",
  "address": "127.0.0.1:80",
  "locale": "en",
  "timeZone": "Asia/Shanghai",
  "operator": null,
  "view": null,
  "exception": "ServiceException",
  "success": false
}
```  