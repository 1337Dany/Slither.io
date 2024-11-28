package client.domain;

import client.data.datasource.Client;
import client.data.datasource.ClientCallback;
import client.data.message.Message;
import client.ui.ViewContract;

import java.io.IOException;

public class ClientGamePresenter implements ClientCallback {

    private Client client;
    private String username;
    private final ViewContract viewContract;

    public ClientGamePresenter(ViewContract viewContract){
        this.viewContract = viewContract;
    }

    public void establishConnection(String username, String ip) throws IOException {
        client = new Client(12345, ip, this);
        this.username = username;
        client.connect();
        viewContract.openGameWindow();
    }

    @Override
    public void onError(String error) {
        viewContract.openMenu();
    }

    @Override
    public void onMessageReceived(String message) {
        Message parsedMessage = new MessageUtils().parseMessageFromServer(message);
        viewContract.messageReceived(parsedMessage);
    }

    public void sendMessageToServer(Message message) {
        String messageToSend = message.buildMessage();
        client.sendMessage(messageToSend);
    }
}