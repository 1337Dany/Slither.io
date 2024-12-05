package client.ui;

import client.data.message.Message;

public interface ViewContract {
    void openGameWindow();
    void openMenu();
    void messageReceived(Message message);
    void showWrongIp();
    void showWrongName();
}
