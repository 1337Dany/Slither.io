package DOMAIN;

import UI.GameWindow;

public class GameManager {
    private final GameWindow gameWindow;
    private final SettingsSetter settingsSetter;

    public GameManager(){
        gameWindow = new GameWindow(this);
        settingsSetter = new SettingsSetter(gameWindow);

        startGame();
    }

    public void startGame(){
        gameWindow.openMainJFrame();

        settingsSetter.setParametersToObjects(gameWindow);
    }

    //TODO: make play button return name and ip in the way that it will manipulates through manager
}