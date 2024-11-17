package client.DOMAIN;

import client.UI.ChatPanel;
import client.UI.GameWindow;
import client.UI.SlitherPanel;

import java.awt.*;

public class GameManager {
    private GameWindow gameWindow;
    private SlitherPanel slitherPanel;
    private final Client client = new Client();
    private ChatPanel chatPanel;
    private final SettingsSetter settingsSetter;

    public GameManager() {
        gameWindow = new GameWindow(this);
        settingsSetter = new SettingsSetter(gameWindow);

        gameWindow.openMainJFrame();

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void startGame() {
        slitherPanel = new SlitherPanel(gameWindow, this);

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void establishConnection(String username, String ip) {
        client.setUsername(username);
        client.setIp(ip);

        startGame();
        if (client.connect()) {
            gameWindow.hideMenu();
            gameWindow.repaint();

        }
    }

    public void sendMessageToServer(String message) {
        client.sendMessage(message);
    }

    public void actionPerform(String message) {
        if (message.contains("Server: ")) {
            chatPanel.addMessage(message, Color.RED);
        } else if (message.contains("Admin message: ")) {
            chatPanel.addMessage(message, new Color(148, 0, 211));
        } else if (message.contains("To ")){
            if (message.contains("To all: ")) {
                chatPanel.addMessage(message.substring(8), Color.WHITE);
            }
        }else {
            System.out.println(message);
            chatPanel.addMessage(message, Color.GREEN);
        }
    }

    public void returnToMenu() {
        gameWindow.remove(slitherPanel);
        gameWindow.showMenu();
        gameWindow.repaint();
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public Client getClient() {
        return client;
    }
}