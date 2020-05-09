package org.finalframework.spring.web.api.controller;

import org.finalframework.core.generator.NameGenerator;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.util.Enums;
import org.finalframework.data.util.Messages;
import org.finalframework.spring.annotation.factory.SpringController;
import org.finalframework.util.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 11:11:24
 * @since 1.0
 */
@RestController
@RequestMapping("/api/enums")
public class ApiEnumController {
    public static final Logger logger = LoggerFactory.getLogger(ApiEnumController.class);

    private final Map<String, Class<?>> enums = SpringFactoriesLoader.loadFactoryNames(Enum.class, getClass().getClassLoader())
            .stream()
            .map(Classes::forName)
            .collect(Collectors.toMap(it -> NameGenerator.decapitalize(it.getSimpleName()), Function.identity()));

    @GetMapping
    public Map<String, String> enums() {
        return enums.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().getCanonicalName()));
    }

    @GetMapping({"/i18n/{name}", "/i18n"})
    public List<String> i18n(@PathVariable(value = "name", required = false) String name) {

        final List<String> messages = new ArrayList<>();
        if (enums.containsKey(name)) {
            Arrays.stream(enums.get(name).getEnumConstants())
                    .map(it -> (Enum<?>) it)
                    .forEach(item -> {
                        final String i18NCode = Enums.getEnumI18NCode(item);
                        final String defValue = item instanceof IEnum ? ((IEnum<?>) item).getDesc() : item.name();
                        messages.add(String.format("%s=%s", i18NCode, Messages.getMessage(i18NCode, defValue)));
                    });
        } else {
            enums.values()
                    .stream()
                    .map(Class::getEnumConstants)
                    .map(it -> (Enum<?>[]) it)
                    .forEach(enums -> {
                        for (Enum<?> item : enums) {
                            final String i18NCode = Enums.getEnumI18NCode(item);
                            final String defValue = item instanceof IEnum ? ((IEnum<?>) item).getDesc() : item.name();
                            messages.add(String.format("%s=%s", i18NCode, Messages.getMessage(i18NCode, defValue)));
                        }
                    });
        }


        return messages;


    }


    @GetMapping({"/values/{name}", "/values"})
    public Object name(@PathVariable(value = "name", required = false) String name) {

        if (enums.containsKey(name)) {
            return enums.get(name);
        }
        return enums;

    }


}

