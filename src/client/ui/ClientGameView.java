package client.ui;

import client.domain.ClientGamePresenter;
import client.domain.SettingsSetter;
import client.ui.menu.IMenuCallback;
import client.ui.menu.MenuView;
import client.ui.slither.ISlitherCallback;
import client.ui.slither.SlitherPanel;
import shared.Message;

import javax.swing.*;

import java.awt.*;

public class ClientGameView extends JFrame implements ViewContract, IMenuCallback, ISlitherCallback {
    private static final Dimension frameSize = new Dimension(1000, 600);
    private final SettingsSetter settingsSetter = new SettingsSetter(this);

    private final ClientGamePresenter clientGamePresenter = new ClientGamePresenter(this);
    private final MenuView menuPanel = new MenuView(frameSize, this);
    private final SlitherPanel slitherPanel = new SlitherPanel(frameSize, this);

    public void openMainJFrame() {
        configure();
        openMenu();

        SettingsSetter.setParametersToObjects(this);
    }
    public void configure(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setLayout(null);
    }

    @Override
    public void openGameWindow() {
        this.remove(menuPanel);
        this.add(slitherPanel);
        repaint();
        revalidate();
    }

    @Override
    public void openMenu() {
        this.add(menuPanel);
        this.remove(slitherPanel);
        repaint();
        revalidate();
    }

    @Override
    public void messageReceived(Message message) {
        slitherPanel.onMessageReceived(message);
    }

    @Override
    public void showWrongIp() {
        menuPanel.showIpErrorText();
    }

    @Override
    public void showWrongName() {
        menuPanel.showNameErrorText();
    }

    @Override
    public void onPlayClicked(String name, String ip) {
        slitherPanel.setUsername(name);
        clientGamePresenter.establishConnection(name, ip);
    }

    @Override
    public void sendMessage(Message message) {
        clientGamePresenter.sendMessageToServer(message);
    }
}