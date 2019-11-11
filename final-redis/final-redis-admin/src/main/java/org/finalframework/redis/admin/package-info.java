/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:59:23
 * @since 1.0
 */
@SpringFactory(value = Controller.class, expand = true)
@SpringFactory(value = UIController.class, expand = true)
package org.finalframework.redis.admin;

import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.finalframework.ui.annotation.UIController;
import org.springframework.stereotype.Controller;