package server.data;

import server.domain.Configurations;
import shared.Message;
import shared.Packet;

public interface ClientManagerCallback {
    void sendMessageToEveryone(Packet message);

    void sendMessageTo(Message message);

    void sendMessageToEveryoneExceptOne(Message message);

    ChatHistory getChatHistory();

    Configurations getBannedPhrases();

    String getServerName();
}
