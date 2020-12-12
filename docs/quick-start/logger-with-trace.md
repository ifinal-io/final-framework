---
layout: post title: logger with trace subtitle: logger with trace description: logger with trace tags: []
menus:

- pinrt-trace-logger date: 2020-12-03 22:35:19 +800 version: 1.0

---

# logger with trace

```yaml
logging:
  pattern:
    console: "%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %clr([ %-36X{trace} ]){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"
    file: "%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : [ %-36X{trace} ] : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"
```

