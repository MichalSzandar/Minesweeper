package michal.projects.gui;

import javafx.scene.image.Image;

public class Images {
    private static Images  instancce;
    private Image flag;
    private Image defaultImg;

    private Images(){
        flag = new Image(getClass().getResource("/images/flag.png").toExternalForm());
        defaultImg = new Image(getClass().getResource("/images/minesweeper_default.png").toExternalForm());
    }

    public static Images getInstance(){
        if(instancce == null){
            instancce = new Images();
        }
        return instancce;
    }

    public Image getFlagImage(){
        return flag;
    }

    public Image getDefault(){
        return defaultImg;
    }
    
}
