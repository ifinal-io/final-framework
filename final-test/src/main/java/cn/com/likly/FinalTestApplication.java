package cn.com.likly;

import cn.com.likly.finalframework.mybatis.agent.MybatisAgent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:13
 * @since 1.0
 */
@SpringBootApplication()
public class FinalTestApplication {
    public static void main(String[] args) throws Exception {
//        MybatisAgent.agent();
        SpringApplication application = new SpringApplication(FinalTestApplication.class);
        application.addListeners(new ApplicationListener<ApplicationStartingEvent>() {
            @Override
            public void onApplicationEvent(ApplicationStartingEvent event) {
                System.out.println(event.getClass().getCanonicalName());
                MybatisAgent.agent();
            }
        });
        application.run(args);
//        ConfigurableApplicationContext context = SpringApplication.run(application, args);
    }
}
