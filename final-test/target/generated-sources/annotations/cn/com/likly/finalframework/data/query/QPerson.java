package cn.com.likly.finalframework.data.query;

import cn.com.likly.finalframework.data.entity.Person;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.domain.QProperty;

import javax.annotation.Generated;

@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public final class QPerson{

    public static final QPerson Person = new QPerson();

    private QPerson(){}

    public final Entity<Person> holder = Entity.from(Person.class);
    public final QProperty stringList = (QProperty) holder.getRequiredPersistentProperty("stringList");
    public final QProperty intList = (QProperty) holder.getRequiredPersistentProperty("intList");
    public final QProperty maps = (QProperty) holder.getRequiredPersistentProperty("maps");
    public final QProperty yn = (QProperty) holder.getRequiredPersistentProperty("yn");
    public final QProperty id = (QProperty) holder.getRequiredPersistentProperty("id");


}
