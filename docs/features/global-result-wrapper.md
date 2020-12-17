---
layout: post title: global-result-wrapper subtitle: global-result-wrapper description:
global-result-wrapper tags: []
menus:

- global-result-wrapper date: 2020-12-06 19:04:43 +800 version: 1.0

---

# 全局结果集包装

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

* 属性：

| 属性          | 类型      | 含义     | 备注 |
| :------------: | :--------: | :-------: | :---: |
| `status`      | `Integer` | 状态     |      |
| `description` | `String`  | 状态描述 |      |
|    `code`    |           `String`           |   业务码   |      |
|  `message`   |           `String`           | 业务码描述 |      |
|    `data`    |             `T`              |  业务数据  |      |
| `pagination` |         `Pagination`         |  分页信息  |      |
|   `trace`    |           `String`           | 业务trace  |      |
| `timestamp`  |            `Long`            | 响应时间戳 |      |
|  `duration`  |          `Duration`          |  业务耗时  |      |
|  `address`   |           `String`           |  服务地址  |      |
|     `ip`     |           `String`           |   服务IP   |      |
|   `locale`   |           `Locale`           |    地区    |      |
|  `timeZone`  |          `TimeZone`          |    时区    |      |
|  `operator`  |           `IUser`            |   操作人   |      |
|    `view`    |           `Class`            |    视图    |      |
| `exception`  | `Class<? extends Throwable>` |    异常    |      |
|              |                              |            |      |
|              |                              |            |      |
|              |                              |            |      |

