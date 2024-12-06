package client.data.datasource;

import shared.Message;

public interface ClientCallback {
    void onError(ClientException exception);
    void onMessageReceived(Message message);
}
