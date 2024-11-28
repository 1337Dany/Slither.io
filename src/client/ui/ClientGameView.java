package client.ui;

import client.data.message.Message;
import client.domain.ClientGamePresenter;
import client.ui.menu.IMenuCallback;
import client.ui.menu.MenuView;
import client.ui.slither.ISlitherCallback;
import client.ui.slither.SlitherPanel;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

public class ClientGameView extends JFrame implements ViewContract, IMenuCallback, ISlitherCallback {
    private static final Dimension frameSize = new Dimension(1000, 600);

    private final ClientGamePresenter clientGamePresenter = new ClientGamePresenter(this);
    private final MenuView menuPanel = new MenuView(frameSize,this);
    private final SlitherPanel slitherPanel = new SlitherPanel(frameSize, this);
    private ActionDialog actionDialog;

    public void openMainJFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setLayout(null);
        openMenu();
//        actionDialog = new ActionDialog();
//        add(actionDialog);
        setVisible(true);
    }

    public ActionDialog getActionDialog() {
        return actionDialog;
    }

    @Override
    public void openGameWindow() {
        this.remove(menuPanel);
        this.add(slitherPanel);
        repaint();
    }

    @Override
    public void openMenu() {
        this.add(menuPanel);
        this.remove(slitherPanel);
        repaint();
    }

    @Override
    public void messageReceived(Message message) {
        slitherPanel.onMessageReceived(message);
    }

    @Override
    public void onPlayClicked(String name, String ip) {
        try {
            slitherPanel.setUsername(name);
            clientGamePresenter.establishConnection(name, ip);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessage(Message message) {
        clientGamePresenter.sendMessageToServer(message);
    }
}