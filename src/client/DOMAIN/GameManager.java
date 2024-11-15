package client.DOMAIN;

import client.UI.GameWindow;

public class GameManager {
    private final GameWindow gameWindow;
    private Client client = new Client();
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

    public void establishConnection(String username, String ip){
        client.setUsername(username);
        client.setIp(ip);

        client.connect();
    }

    //TODO: make play button return name and ip in the way that it will manipulates through manager
}