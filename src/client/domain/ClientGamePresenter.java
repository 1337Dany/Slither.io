package client.domain;

import client.data.datasource.*;
import client.ui.ViewContract;
import shared.Message;

import java.io.IOException;

public class ClientGamePresenter implements ClientCallback {

    private Client client;
    private final ViewContract viewContract;

    public ClientGamePresenter(ViewContract viewContract) {
        this.viewContract = viewContract;
    }

    public void establishConnection(String username, String ip) {
        try {
            client = new Client(12345, ip, this);
            client.connect(username);
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
        client.closeConnection();
    }

    @Override
    public void onMessageReceived(Message message) {
        //Message parsedMessage = new MessageUtils().parseMessageFromServer(message);
        viewContract.messageReceived(message);
    }

    public void sendMessageToServer(Message message) {
        client.sendMessage(message);
    }
}