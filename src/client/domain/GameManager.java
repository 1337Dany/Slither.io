package client.domain;

import client.data.Client;
import client.ui.ChatPanel;
import client.ui.GameWindow;
import client.ui.SlitherPanel;

import java.awt.*;

public class GameManager {
    private final GameWindow gameWindow;
    private SlitherPanel slitherPanel;
    private final Client client = new Client();
    private ChatPanel chatPanel;
    private final SettingsSetter settingsSetter;

    public GameManager() {
        gameWindow = new GameWindow(this);
        settingsSetter = new SettingsSetter(gameWindow);

        gameWindow.openMainJFrame();

        slitherPanel = new SlitherPanel(gameWindow, this);

        settingsSetter.setParametersToObjects(gameWindow);
    }

    public void startGame() {
        gameWindow.add(slitherPanel);
    }

    public void establishConnection(String username, String ip) {
        client.setUsername(username);
        client.setIp(ip);

        if (client.connect()) {
            gameWindow.hideMenu();
            startGame();
            gameWindow.repaint();
        } else {
            gameWindow.wrongServer();
        }
    }

    public void sendMessageToServer(String message) {
        client.sendMessage(message);
    }

    public void actionPerform(String message) {
        try {
            if (message.startsWith("System: ")) {
                if (message.contains("new game member: ")) {
                    chatPanel.addMessage(message.substring(25) + " has connected!", Color.ORANGE);
                    slitherPanel.addGamer(message.substring(25));
                } else if (message.contains("download names: ")) {
                    slitherPanel.addGamer(message.substring(24));
                } else if (message.contains("kick: ")) {
                    chatPanel.addMessage(message.substring(14) + " has left", Color.ORANGE);
                    slitherPanel.removePlayer(message.substring(14));
                }
            } else if (message.startsWith("Server: ")) {
                chatPanel.addMessage(message, Color.RED);
            } else if (message.startsWith("Admin message: ")) {
                chatPanel.addMessage(message, new Color(148, 0, 211));
            } else if (message.startsWith("To ")) {
                if (message.startsWith("To all: ")) {
                    chatPanel.addMessage(message.substring(8), Color.WHITE);
                }
            } else {
                chatPanel.addMessage(message, Color.GREEN);
            }
        } catch (NullPointerException e) {
            client.closeConnection();
            returnToMenu();
        }
    }

    public void wrongName() {
        gameWindow.wrongName();
    }

    public void returnToMenu() {
        gameWindow.remove(slitherPanel);
        gameWindow.showMenu();
        gameWindow.repaint();
        gameWindow.revalidate();
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public Client getClient() {
        return client;
    }
}