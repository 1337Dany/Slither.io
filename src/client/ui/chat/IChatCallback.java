package client.ui.chat;

import shared.Message;

import javax.swing.*;

public interface IChatCallback {
    void sendMessage(Message message);
    JTextArea getInputPanel();
}
