package org.finalframework.data.api.controller;

import org.finalframework.data.api.serivce.EnumService;
import org.finalframework.data.api.serivce.query.EnumQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-10 22:19:07
 * @since 1.0
 */
@RestController
@RequestMapping("/api/enums")
public class EnumApiController {
    public static final Logger logger = LoggerFactory.getLogger(EnumApiController.class);

    @Resource
    private EnumService enumService;

    public List<Class<?>> query(EnumQuery query) {
        return enumService.query(query);
    }

//
//    @GetMapping({"/i18n/{name}", "/i18n"})
//    public List<String> i18n(@PathVariable(value = "name", required = false) String name) {
//
//        final List<String> messages = new ArrayList<>();
//        if (enums.containsKey(name)) {
//            Arrays.stream(enums.get(name).getEnumConstants())
//                    .map(it -> (Enum<?>) it)
//                    .forEach(item -> {
//                        final String i18NCode = Enums.getEnumI18NCode(item);
//                        final String defValue = item instanceof IEnum ? ((IEnum<?>) item).getDesc() : item.name();
//                        messages.add(String.format("%s=%s", i18NCode, Messages.getMessage(i18NCode, defValue)));
//                    });
//        } else {
//            enums.values()
//                    .stream()
//                    .map(Class::getEnumConstants)
//                    .map(it -> (Enum<?>[]) it)
//                    .forEach(enums -> {
//                        for (Enum<?> item : enums) {
//                            final String i18NCode = Enums.getEnumI18NCode(item);
//                            final String defValue = item instanceof IEnum ? ((IEnum<?>) item).getDesc() : item.name();
//                            messages.add(String.format("%s=%s", i18NCode, Messages.getMessage(i18NCode, defValue)));
//                        }
//                    });
//        }
//
//
//        return messages;
//
//
//    }
//
//
//    @GetMapping({"/values/{name}", "/values"})
//    public Object name(@PathVariable(value = "name", required = false) String name) {
//
//        if (enums.containsKey(name)) {
//            return enums.get(name);
//        }
//        return enums;
//
//    }
}

