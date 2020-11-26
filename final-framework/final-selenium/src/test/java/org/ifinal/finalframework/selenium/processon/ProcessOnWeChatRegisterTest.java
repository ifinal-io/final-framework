package org.ifinal.finalframework.selenium.processon;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ProcessOnWeChatRegisterTest {

    @Test
    void register() throws InterruptedException {

        int count = 1;
        while (true) {
            long start = System.currentTimeMillis();
            ProcessOnWeChatRegister register = new ProcessOnWeChatRegister();
            try {
                String url = "https://www.processon.com/i/566ae249e4b0add117b7c405";
                register.home(url);
                register.login();
                register.wechatLogin();
                register.skipBindAccount();
                register.goSetting();
                register.bindMail();
                register.loginOut();
                logger.info("--> 第{}次注册完成，用时：{}", count++, System.currentTimeMillis() - start);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                register.close();

            }
        }
    }
}