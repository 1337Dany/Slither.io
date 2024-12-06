package client.ui;

import shared.Message;

public interface ViewContract {
    void openGameWindow();
    void openMenu();
    void messageReceived(Message message);
    void showWrongIp();
    void showWrongName();
}
