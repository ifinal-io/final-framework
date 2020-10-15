package org.finalframework.data.trigger;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.core.Asserts;
import org.springframework.beans.factory.ObjectProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:21:28
 * @since 1.0
 */
@SpringComponent
public class TriggerManager {

    private Map<Class<?>, List<InsertTrigger<?, ?>>> insertTriggers = new LinkedHashMap<>(32);
    private Map<Class<?>, List<UpdateTrigger<?, ?>>> updateTriggers = new LinkedHashMap<>(32);
    private Map<Class<?>, List<DeleteTrigger<?, ?>>> deleteTriggers = new LinkedHashMap<>(32);
    private Map<Class<?>, List<SelectTrigger<?, ?>>> selectTriggers = new LinkedHashMap<>(32);

    public TriggerManager(ObjectProvider<List<Trigger>> triggerObjectProvider) {
        List<Trigger> triggers = triggerObjectProvider.getIfAvailable();
        if (Asserts.nonEmpty(triggers)) {
            for (Trigger trigger : triggers) {
                this.registerTrigger(trigger);
            }
        }
        System.out.println();
    }

    public static Class<?> findTriggerEntity(Class<? extends Trigger> trigger) {
        Type[] genericInterfaces = trigger.getGenericInterfaces();

        for (Type type : genericInterfaces) {

            if (type instanceof ParameterizedType && Trigger.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                Type typeArgument = ((ParameterizedType) type).getActualTypeArguments()[1];
                return (Class<?>) typeArgument;
            }

        }

        return null;


    }

    public static void main(String[] args) {
        System.out.println(TriggerManager.findTriggerEntity(AbsEntityInsertTrigger.class));
    }

    public void registerTrigger(Trigger trigger) {
        Class entity = findTriggerEntity(trigger.getClass());
        if (trigger instanceof InsertTrigger) {
            List<InsertTrigger<?, ?>> insertTriggers = this.insertTriggers
                    .computeIfAbsent(entity, key -> new ArrayList<>());
            insertTriggers.add((InsertTrigger<?, ?>) trigger);
        }

        if (trigger instanceof UpdateTrigger) {
            List<UpdateTrigger<?, ?>> updateTriggers = this.updateTriggers
                    .computeIfAbsent(entity, key -> new ArrayList<>());
            updateTriggers.add((UpdateTrigger<?, ?>) trigger);
        }

        if (trigger instanceof DeleteTrigger) {
            List<DeleteTrigger<?, ?>> deleteTriggers = this.deleteTriggers
                    .computeIfAbsent(entity, key -> new ArrayList<>());
            deleteTriggers.add((DeleteTrigger<?, ?>) trigger);
        }

        if (trigger instanceof SelectTrigger) {
            List<SelectTrigger<?, ?>> selectTriggers = this.selectTriggers
                    .computeIfAbsent(entity, key -> new ArrayList<>());
            selectTriggers.add((SelectTrigger<?, ?>) trigger);
        }

    }

    public List<InsertTrigger<?, ?>> getInsertTriggers(Class<?> entity) {
        return insertTriggers.get(entity);
    }

    public List<SelectTrigger<?, ?>> getSelectTriggers(Class<?> entity) {
        return selectTriggers.get(entity);
    }
}
