package server.data;

import server.domain.Configurations;
import shared.GameConfiguration;
import shared.Message;
import shared.MessagePrefixes;
import shared.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements ClientManagerCallback {
    private final Configurations configurations;
    private final ChatHistory chatHistory = new ChatHistory();
    private final int port;
    private final String serverName;

    private static final ConcurrentHashMap<String, ClientManager> clients = new ConcurrentHashMap<>();
    private static final ExecutorService executorSevice = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("Player", 0).factory());

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
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        ClientManager clientManager = new ClientManager(this, clientSocket);

        //  Run async virtual thread for logic of creating and starting client through CompletableFuture
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            //  Check of existing player with the same name
            if (clients.containsKey(clientManager.getName())) {
                clientManager.sendMessage(new GameConfiguration(MessagePrefixes.CONNECTION_RESET, "Name already exists"));
                clientManager.closeConnection();
                return;
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

            clients.put(clientManager.getName(), clientManager);
            System.out.println("Client " + clientManager.getFullAddress() + " connected successfully");
            clientManager.run();
        }, executorSevice);

        //  Handling cases when client disconnects or can not connect to the server
        completableFuture.whenComplete((result, executor) -> {
            System.out.println("Client " + clientManager.getFullAddress() + " disconected");
            kickUser(clientManager.getName());
        });
    }

    private void kickUser(String name) {
        //  Necessary to remove it by name to avoid case when user did not specified his name and it is null
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

    @Override
    public void sendMessageToEveryone(Packet message) {
        clients.forEach((key, value) -> {
            value.sendMessage(message);
        });
    }

    @Override
    public void sendMessageTo(Message message) {
        String[] str = (message.getReceiver().split(","));
        for (String name : str) {
            clients.get(name).sendMessage(message);
        }
        clients.get(message.getSender()).sendMessage(message);
    }

    @Override
    public void sendMessageToEveryoneExceptOne(Message message) {
        String[] notToSendTo = (message.getReceiver().split(","));

        clients.forEach((key, value) -> {
            boolean isSent = true;
            for (String s : notToSendTo) {
                if (key.equals(s)) {
                    isSent = false;
                }
            }
        if (isSent) {
            value.sendMessage(message);
        }
        });
    }

    @Override
    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    @Override
    public Configurations getBannedPhrases() {
        return configurations;
    }

    @Override
    public String getServerName() {
        return serverName;
    }
}
