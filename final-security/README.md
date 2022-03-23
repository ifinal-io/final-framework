# Final Security

## 原则

* 默认所有端点（`endpoints`）均为开放资源。
* 通过注解`@PreAuthorize`、`@PostAuthorize`等注解进行授权。
* 统一使用`Json`与接口调用方交互。