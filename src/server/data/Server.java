package server.data;

import server.domain.Configurations;
import shared.GameConfiguration;
import shared.Message;
import shared.MessagePrefixes;
import shared.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final Configurations configurations;
    private final ChatHistory chatHistory = new ChatHistory();
    private final int port;

    private final String serverName;

    static Map<String, ClientManager> clients = new HashMap<>();
    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        configurations = new Configurations();
        port = configurations.giveServerPort();
        serverName = configurations.giveServerName();

        startServer();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(this, clientSocket);

                //  Check of existing player with the same name
                if (clients.containsKey(clientManager.getName())) {
                    clientManager.sendMessage(new GameConfiguration(MessagePrefixes.CONNECTION_RESET, "Name already exists"));
                    clientManager.closeConnection();
                    continue;
                }

                sendMessageToEveryone(new Message(MessagePrefixes.CHAT_CONFIGURATION, null, serverName, "Player " + clientManager.getName() + " logged in"));

                //  Send nickname of new player to all players
                sendMessageToEveryone(new GameConfiguration(MessagePrefixes.TAB_CONFIGURATION, clientManager.getName()));

                //  Send nicknames of all players to new player
                for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
                    clientManager.sendMessage(new GameConfiguration(MessagePrefixes.TAB_CONFIGURATION, client.getKey()));
                }

                clientManager.sendMessage(
                        new Message(
                                MessagePrefixes.CHAT_CONFIGURATION,
                                null,
                                serverName,
                                "\nCommands:\n" +
                                        "To ....: -> send message to group or person\n" +
                                        "To not ....: -> send message to everyone except this group or person\n" +
                                        "To all: ....: -> send message to everyone\n" +
                                        "Keys:\n" +
                                        "TAB -> show list of players\n" +
                                        "Rules:\n" +
                                        "do not use this phrases in chat! -> " + configurations.getAllBanPhrases()
                        ));

                // send all chat data to new user
                String tmp;
                while ((tmp = chatHistory.getNextTextArea()) != null) {
                    clientManager.sendMessage(new Message(MessagePrefixes.CHAT_HISTORY, null, serverName, tmp));
                }

                new Thread(clientManager).start();
                clients.put(clientManager.getName(), clientManager);
                System.out.println("Client " + clientManager.getFullAddress() + " connected successfully");

            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    public void sendMessageToEveryone(Packet message) {
        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            client.getValue().sendMessage(message);
        }
    }

    public void sendMessageTo(Message message) {
        String[] str = (message.getReceiver().split(","));
        for (String name : str) {
            clients.get(name).sendMessage(message);
        }
        clients.get(message.getSender()).sendMessage(message);
    }

    public void sendMessageToEveryoneExceptOne(Message message) {
        String[] notToSendTo = (message.getReceiver().split(","));
        boolean isSent = true;

        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            for (int i = 0; i < notToSendTo.length; i++) {
                if (client.getKey().equals(notToSendTo[i])) {
                    isSent = false;
                }
            }
            if (isSent) {
                client.getValue().sendMessage(message);
            }
            isSent = true;
        }
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public Configurations getBannedPhrases() {
        return configurations;
    }

    public void kickUser(String name) {
        clients.get(name).closeConnection();
        clients.remove(name);
        System.out.println(name + " removed");

        sendMessageToEveryone(new GameConfiguration(MessagePrefixes.KICK, name));
        sendMessageToEveryone(
                new Message(
                        MessagePrefixes.CHAT_CONFIGURATION,
                        null,
                        serverName,
                        "Player " + name + " logged out"
                ));
    }
    public String getServerName() {
        return serverName;
    }
}
