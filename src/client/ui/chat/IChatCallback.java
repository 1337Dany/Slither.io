package client.ui.chat;

import client.data.message.Message;

public interface IChatCallback {
    void sendMessage(Message message);
}
