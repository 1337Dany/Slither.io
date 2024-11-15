package client.DOMAIN;

import client.UI.GameWindow;

public class GameManager {
    private final GameWindow gameWindow;
    private final Client client = new Client();
    private Game game;

    private final SettingsSetter settingsSetter;

    public GameManager() {
        gameWindow = new GameWindow(this);
        settingsSetter = new SettingsSetter(gameWindow);

        gameWindow.openMainJFrame();

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void startGame() {
        game = new Game();
    }

    public void establishConnection(String username, String ip) {
        client.setUsername(username);
        client.setIp(ip);

        if (client.connect()) {
            gameWindow.hideMenu();
            startGame();
        }

        gameWindow.repaint();
    }

}