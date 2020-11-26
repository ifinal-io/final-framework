package org.ifinal.finalframework.selenium.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class TempMailTest {


    @Test
    void testTempMail() throws InterruptedException {

        Mail<String> mail = new TempMail<>();

        try {

            mail.setContentParser(new Mail.ContentParser<String>() {
                @Override
                public String doParse(WebDriver driver) {

                    while (true) {

                        try {
                            WebElement mailContent = driver.findElement(By.className("mail-content"));
                            List<WebElement> elements = mailContent.findElements(By.tagName("strong"));

                            if (elements != null && elements.size() == 2) {
                                return elements.get(1).getText().trim();
                            }

                            Thread.sleep(200);


                        } catch (Exception e) {

                        }
                    }

                }
            });


            mail.setMailListener(new Mail.MailListener<String>() {
                @Override
                public void onInit(String mail) {
                    logger.info("find mail:{}", mail);

                }

                @Override
                public void onReceive(WebDriver driver, WebElement message) {
                    logger.info("received mail: {}", message.getAttribute("href"));
                    message.click();
                    mail.parse();


                }

                @Override
                public void onRead(String content) {
                    logger.info("read mail: {}", content);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    logger.info("close mail!!!");

                    mail.close();
                }
            });
            mail.open();


        } finally {
        }
    }
}