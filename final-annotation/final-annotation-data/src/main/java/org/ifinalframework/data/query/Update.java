/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.query;

import java.util.LinkedList;
import java.util.function.Consumer;

import org.springframework.lang.Nullable;

import org.ifinalframework.core.IUpdate;
import org.ifinalframework.data.query.update.JsonUpdatable;
import org.ifinalframework.data.query.update.SimpleUpdatable;
import org.ifinalframework.data.query.update.Updatable;

/**
 * Update sql fragment in java.
 *
 * <p>Usage</p>
 *
 *
 * <pre class="code">
 *      Update update = Update.update();
 *      // set column value
 *      update.set(column,value);
 *      // incr or decr column value
 *      update.incr(column,number);
 *      update.decr(column,number);
 *      // custom update
 *      update.update(expression,column,value,consumer);
 * </pre>
 *
 * <p>Update With {@code JSON_SET}</p>
 *
 * <pre class="code">
 * Update.update().jsonSet(column,&lt;path,val&gt;);
 * </pre>
 *
 * <p>Support update sql fragments:</p>
 * <ul>
 *     <li>set column with value like {@code column = #{value}},{@link #set(String, Object)}</li>
 *     <li>set column with value like {@code column = column +/- #{value}},see {@link #incr(String, Number)} and {@link #decr(String, Number)}</li>
 *     <li>set column with custom sql, see {@link #update(String, String, Object, Consumer)}</li>
 * </ul>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class Update extends LinkedList<Criterion> implements Updatable, IUpdate,
        SimpleUpdatable, JsonUpdatable {

    public static Update update() {
        return new Update();
    }


    @Override
    public Update update(String expression, String column, Object value, @Nullable Consumer<CriterionAttributes> consumer) {
        Criterion criterion = CriterionTarget.from(column)
                .condition(expression, value, consumer);
        this.add(criterion);
        return this;
    }

}
