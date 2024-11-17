package client.DOMAIN;

import client.UI.GameWindow;
import client.UI.SlitherPanel;

import javax.swing.*;

public class GameManager {
    private GameWindow gameWindow;
    private SlitherPanel slitherPanel;
    private final Client client = new Client();
    private Chat chat;
    private final SettingsSetter settingsSetter;

    public GameManager() {
        gameWindow = new GameWindow(this);
        settingsSetter = new SettingsSetter(gameWindow);

        gameWindow.openMainJFrame();

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void startGame() {
        SwingUtilities.invokeLater(() -> slitherPanel = new SlitherPanel(gameWindow));
        chat = new Chat();

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void establishConnection(String username, String ip) {
        client.setUsername(username);
        client.setIp(ip);

        if (client.connect()) {
            gameWindow.hideMenu();
            gameWindow.repaint();

            startGame();
        }
    }

    public void returnToMenu(){
        gameWindow.remove(slitherPanel);
        gameWindow.showMenu();
        gameWindow.repaint();
    }

}