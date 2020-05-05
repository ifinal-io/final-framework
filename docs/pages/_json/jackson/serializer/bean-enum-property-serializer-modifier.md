---
post: post
title: 对象枚举属性序列化修改器
subtitle: 对对象的枚举属性进行序列化增强
layout: post
categories: []
tags: []
menus:
    - jackson
    - serializer
    - bean-enum-property-serializer-modifier
author: likly
date: 2019-08-27 14:59:34 +800
version: 1.0
---

## BeanEnumPropertySerializerModifier

[BeanEnumPropertySerializerModifier](/final-json/final-json-jackson/src/main/java/org/finalframework/json/jackson/serializer/modifier/BeanEnumPropertySerializerModifier.java)
对`JavaBean`的枚举类型属性（实现了[IEnum](/final-data/final-data-context/src/main/java/org/finalframework/data/entity/enums/IEnum.java)接口）
进行了序列化修改，并其序列化为`IEnum#getCode()`所返回的值，并增加一个名为`xxxName`的属性来描述该枚举型属性的含义，其值为`IEnum#getDescription()`。


```java

public class BeanEnumPropertySerializerModifier extends BeanSerializerModifier {

    private static final Logger logger = LoggerFactory.getLogger(BeanEnumPropertySerializerModifier.class);

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {

        // 1. 将原有的属性映射成一个Map，key为属性名称
        Map<String, BeanPropertyWriter> beanPropertyWriterMap = beanProperties.stream()
                .collect(Collectors.toMap(BeanPropertyWriter::getName, Function.identity()));

        // 2. 遍历，找出实现了 IEnum 接口的属性，为其增加一个名称 xxxName 的新属性到 JavaBean的
        List<BeanPropertyDefinition> properties = beanDesc.findProperties();
        for (BeanPropertyDefinition property : properties) {
            
            if (IEnum.class.isAssignableFrom(property.getPrimaryType().getRawClass())) {
                // 实现了 IEnum 的属性
                BeanPropertyWriter def = beanPropertyWriterMap.get(property.getName());

                BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                        def.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                        EnumNameSerializer.instance, def.getTypeSerializer(), def.getSerializationType(),
                        def.willSuppressNulls(), null, property.findViews());

                try {
                    Field name = BeanPropertyWriter.class.getDeclaredField("_name");
                    name.setAccessible(true);
                    name.set(bpw, new SerializedString(bpw.getName() + "Name"));
                } catch (Exception e) {
                    logger.error("", e);
                }


                beanProperties.add(bpw);
            }
        }
        return super.changeProperties(config, beanDesc, beanProperties);
    }

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        JsonFormat jsonFormat = beanDesc.getClassAnnotations().get(JsonFormat.class);
        if (jsonFormat != null && JsonFormat.Shape.OBJECT == jsonFormat.shape()) {
            return EnumSerializer.instance;
        }

        return EnumCodeSerializer.instance;
//        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }
}

```