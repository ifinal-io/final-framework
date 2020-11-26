package org.ifinal.finalframework.selenium.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Mail<T> {


    void open();

    void close();

    void parse();

    void setContentParser(ContentParser<T> parser);

    void setMailListener(MailListener<T> listener);

    interface ContentParser<T> {
        T doParse(WebDriver driver);
    }

    interface MailListener<T> {
        void onInit(@NonNull String mail);

        void onReceive(@NonNull WebDriver driver, @NonNull WebElement message);

        void onRead(@NonNull T content);

    }


}
