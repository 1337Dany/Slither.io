package client.ui;

import shared.GameConfiguration;
import shared.Message;
import shared.Packet;

public interface ViewContract {
    void openGameWindow();
    void openMenu();
    void messageReceived(Message message);
    void gameConfigurationReceived(GameConfiguration packet);
    void showWrongIp();
    void showWrongName();
}
