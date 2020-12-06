---
layout: post title: global-exception-handler subtitle: global-exception-handler description: global-exception-handler
tags: []
menus:
- global-exception-handler date: 2020-12-05 11:16:05 +800 version: 1.0
---

# global-exception-handler

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