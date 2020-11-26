package org.ifinal.finalframework.selenium.processon;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class ImageCode {
    public static void main(String[] args) throws IOException {

        try {
            File input = new File("code.jpeg");
            BufferedImage image = ImageIO.read(input);
            logger.info("width={},height={}", image.getWidth(), image.getHeight());


            System.out.println(image.getRGB(555, 170));

            for (int i = 30; i < image.getHeight() - 60; i++) {
                for (int j = 0; j < image.getWidth() - 100; j++) {

                    if (image.getRGB(j, i) == image.getRGB(j + 1, i)
                            && image.getRGB(j, i) == image.getRGB(j + 2, i)
                            && image.getRGB(j, i) == image.getRGB(j + 3, i)
                            && image.getRGB(j, i) == image.getRGB(j + 4, i)
                            && image.getRGB(j, i) == image.getRGB(j + 5, i)
//                            && image.getRGB(j,i)== image.getRGB(j+6,i)
//                            && image.getRGB(j,i)== image.getRGB(j+7,i)
//                            && image.getRGB(j,i)== image.getRGB(j+8,i)
//                            && image.getRGB(j,i)== image.getRGB(j+9,i)
//                            && image.getRGB(j,i)== image.getRGB(j+10,i)

                            && image.getRGB(j, i) == image.getRGB(j, i + 1)
                            && image.getRGB(j, i) == image.getRGB(j, i + 2)
                            && image.getRGB(j, i) == image.getRGB(j, i + 3)
                            && image.getRGB(j, i) == image.getRGB(j, i + 4)
                            && image.getRGB(j, i) == image.getRGB(j, i + 5)
//                        && image.getRGB(j,i) == image.getRGB(j,i+6)
//                        && image.getRGB(j,i) == image.getRGB(j,i+7)
//                        && image.getRGB(j,i) == image.getRGB(j,i+8)
//                        && image.getRGB(j,i) == image.getRGB(j,i+9)
//                        && image.getRGB(j,i) == image.getRGB(j,i+10)
                    ) {
                        logger.info("x={},y={}", j, i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
