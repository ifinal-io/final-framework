---
layout: post title: checkstyle subtitle: checkstyle description: checkstyle tags: []
menus:

- checkstyle date: 2020-12-11 20:44:43 +800 version: 1.0

---

# checkstyle

## Imports

* 禁止 `improt *`

```xml

<module name="TreeWalker">

    <!-- Imports -->
    <!-- 禁止 import *-->
    <module name="AvoidStarImport"/>

</module>
```