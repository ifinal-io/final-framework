package org.ifinal.finalframework.example.entity;

import lombok.Data;
import org.ifinal.finalframework.annotation.IView;
import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.data.Reference;
import org.ifinal.finalframework.annotation.data.View;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
public class Person extends AbsEntity {

    @View(RegisterView.class)
    private String account;
    @View(RegisterView.class)
    private String password;
    private Boolean admin;
    private String name;
    private Integer age;
    private List<Integer> intList;
    private Map<String, String> map;
    @Reference(properties = {"id", "intList", "name"})
    private Person creator;


    public interface RegisterView extends IView {
    }

    public interface ShareView extends IView {
    }

}
