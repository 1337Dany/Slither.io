package client.ui.chat;

import shared.Message;

public interface IChatCallback {
    void sendMessage(Message message);
}
