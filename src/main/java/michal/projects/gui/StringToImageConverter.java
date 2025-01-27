package michal.projects.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public final class StringToImageConverter {
    private Map<String, Image> stringToImageMap;
    private static StringToImageConverter instance;

    private StringToImageConverter() {
        stringToImageMap = new HashMap<>();

        // setting up images representing numbers from 0 to 8
        for (int i = 0; i <= 8; i++) {
            stringToImageMap.put(Integer.toString(i), Images.getNumericImage(i));
        }

        // setting up bomb images
        stringToImageMap.put("*", Images.getBombImage());
        stringToImageMap.put("-", new Image(getClass().getResource("/images/mine.png").toExternalForm()));
    }

    /**
     * method to acces icon correspo
     * @param key
     * @return
     */
    public Image getImage(final String key) {
        return stringToImageMap.get(key);
    }

     /**
     * creates new instance if current one is null and returns it.
     * @return instance of this object
     */
    public static StringToImageConverter getInstance() {
        StringToImageConverter local = instance;
        if (local == null) {
            synchronized (StringToImageConverter.class) {
                local = instance;
                if (local == null) {
                    instance = new StringToImageConverter();
                    local = instance;
                }
            }
        }
        return instance;
    }
}
