package org.finalframework.data.annotation;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:06
 * @see IEnum
 * @since 1.0
 */
public interface IEntity<ID extends Serializable> extends Serializable {

  /**
   * return the value of {@link ID}.
   *
   * @see PrimaryKey
   */
  ID getId();

  /**
   * set value to {@link ID}.
   *
   * @see PrimaryKey
   */
  void setId(ID id);
}
