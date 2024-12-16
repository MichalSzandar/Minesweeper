package michal.projects.gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class StringToImageConverter {
    private static Map<String, Image> stringToImageMap;
    private static StringToImageConverter instance;

    private StringToImageConverter(){
        stringToImageMap = new HashMap<>();

        //setting up images representing numbers from 0 to 8
        for(int i = 0; i <= 8; i++){
            stringToImageMap.put(Integer.toString(i), new Image(getClass().getResource("/images/minesweeper_" + i + ".png").toExternalForm()));
        }
        
        //setting up bomb images
        stringToImageMap.put("*", new Image(getClass().getResource("/images/bomb.png").toExternalForm()));
        stringToImageMap.put("-", new Image(getClass().getResource("/images/mine.png").toExternalForm()));
    }

    public Image getImage(String key){
        return stringToImageMap.get(key);
    }

     /**
     * creates new instance if current one is null and returns it
     * @return instance of this object
     */
    public static StringToImageConverter getInstance(){
        StringToImageConverter local = instance;
        if(local==null){
            synchronized(StringToImageConverter.class){
                local = instance;
                if(local == null){
                    local = instance = new StringToImageConverter();
                }
            }
        }
        return instance;
    }
}
