package client.data.datasource;

import shared.GameConfiguration;
import shared.Message;
import shared.Packet;

public interface ClientCallback {
    void onError(ClientException exception);
    void onMessageReceived(Message message);
    void gameConfigurationReceived(GameConfiguration gameConfiguration);
}
