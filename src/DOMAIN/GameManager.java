package DOMAIN;

import UI.GameWindow;

public class GameManager {
    private final GameWindow gameWindow;
    private final SettingsSetter settingsSetter;

    public GameManager(){
        gameWindow = new GameWindow();
        settingsSetter = new SettingsSetter(gameWindow);

        startGame();
    }

    public void startGame(){
        gameWindow.openMainJFrame();

        settingsSetter.setParametersToObjects(gameWindow);
    }
}