package project.game.levels.io;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import project.misc.Utils;

/**
 * {@link ImageParser} parses images from text.
 */
public class ImageParser {

    private static final Pattern IMAGE_PATTERN = Pattern.compile("image\\(([^\\s]+)\\)");

    // map used to save image urls and their corresponding images
    private Map<String, Image> imgs = new HashMap<>();

    /**
     * Parse the image from the given text.
     * @param str : the text
     * @return an {@link Image} instance or null if failed to parse
     */
    public Image parseImage(String str) {
        Matcher m = IMAGE_PATTERN.matcher(str);

        // if is not even an image function
        if (!m.matches()) {
            return null;
        }

        String imageUrl = m.group(1);

        Image img = null;

        if (this.imgs.containsKey(imageUrl)) {
            // if the image was already read
            img = this.imgs.get(imageUrl);
        } else {
            // try reading image, if failed return null
            try {
                //                System.out.println(Utils.getResource(imageUrl));
                img = ImageIO.read(Utils.getResource(imageUrl));

                if (img != null) {
                    this.imgs.put(imageUrl, img);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return img;
    }
}
