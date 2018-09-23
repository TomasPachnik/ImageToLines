package sk.tomas.iti;

import sk.tomas.servant.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static sk.tomas.iti.Configuration.IMAGE_PATH;

@Bean("imageLoader")
public class ImageLoader {

    private BufferedImage image;

    public ImageLoader() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH + "image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
