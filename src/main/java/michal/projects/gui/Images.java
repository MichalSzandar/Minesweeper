package michal.projects.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public final class Images {
    /**flag.png . */
    private static Image flag;
    /**default.png . */
    private static Image defaultImg;
    /**bomb.png .*/
    private static Image bombImg;
    /**list of images representing numbers. */
    private static List<Image> numericImages;

    public static void configureImages() {
        numericImages = new ArrayList<>();

        flag = new Image(Images.class
            .getResource("/images/flag.png").toExternalForm());
        defaultImg = new Image(Images.class
            .getResource("/images/minesweeper_default.png").toExternalForm());
        bombImg = new Image(Images.class
            .getResource("/images/bomb.png").toExternalForm());

        for (int i = 0; i <= 8; i++) {
            numericImages.add(new Image(Images.class
                .getResource("/images/minesweeper_" + i + ".png").toExternalForm()));
        }
    }

    /**
     * method to access flag.png .
     * @return Image flag.png
     */
    public static Image getFlagImage() {
        return flag;
    }

    /**
     * method to acces default.png .
     * @return image default.png
     */
    public static Image getDefault() {
        return defaultImg;
    }

    /**
     * method to access images representing numbers.
     * @param number
     * @return
     */
    public static Image getNumericImage(int number) {
        return numericImages.get(number);
    }

    /**
     * method to access bomb.png .
     * @return Image bomb.png
     */
    public static Image getBombImage() {
        return bombImg;
    }
}
