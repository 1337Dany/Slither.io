package client.domain;

import client.data.datasource.*;
import client.data.message.Message;
import client.ui.ViewContract;

import java.io.IOException;

public class ClientGamePresenter implements ClientCallback {

    private Client client;
    private String username;
    private final ViewContract viewContract;

    public ClientGamePresenter(ViewContract viewContract) {
        this.viewContract = viewContract;
    }

    public void establishConnection(String username, String ip) {
        try {
            client = new Client(12345, ip, this);
            this.username = username;
            client.connect();
            viewContract.openGameWindow();
        } catch (IOException e) {
            this.onError(new IpException());
        }
    }

    @Override
    public void onError(ClientException exception) {
        if (exception instanceof IpException) {
            viewContract.showWrongIp();
        } else if (exception instanceof NameException) {
            viewContract.showWrongName();
        } else if (exception instanceof ServerDisconnectedException) {
            viewContract.openMenu();
        }
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