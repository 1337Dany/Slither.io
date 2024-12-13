package server.data;

import server.domain.Configurations;
import shared.Message;
import shared.MessagePrefixes;

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
                    clientManager.sendMessage(new Message(MessagePrefixes.SERVER_CONFIGURATION, null, serverName, "Server: player with this name is already exist"));
                    clientManager.closeConnection();
                    continue;
                }
                //     clientManager.sendMessage("Server: connection established!");

                //  sendMessageToEveryone("System: new game member: " + clientManager.getName());

//                for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
//                    clientManager.sendMessage("System: download names: " + client.getKey());
//                }
//                clientManager.sendMessage("Server: Welcome to the server " + serverName + "!!!!");
//                clientManager.sendMessage("""
//                        Server: Commands:
//                         To ....: -> send message to group or person
//                         To not ....: -> send message to everyone except this group or person
//                         To all: ....: -> send message to everyone
//
//                         Keys:
//                         TAB -> show list of players
//
//                        Server: Rules:
//                         do not use this phrases in chat! ->\s"""+ configurations.getAllBanPhrases()
//                        );

                // send all chat data to new user
                String tmp;
                while ((tmp = chatHistory.getNextTextArea()) != null) {
                    clientManager.sendMessage(new Message(MessagePrefixes.SERVER_CONFIGURATION, null, serverName, tmp));
                }

                new Thread(clientManager).start();
                clients.put(clientManager.getName(), clientManager);
                System.out.println("Client " + clientManager.getFullAddress() + " connected successfully");

            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    public void sendMessageToEveryone(String message, String from) {
        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            client.getValue().sendMessage(new Message(MessagePrefixes.TOALL,null, from, message));
        }
    }

    public void sendMessageTo(Message message) {
        try {
            String receiver = message.getReceiver();
            String[] str = (message.getReceiver().split(","));
            for (String name : str) {
                clients.get(name).sendMessage(message);
            }
            clients.get(message.getSender()).sendMessage(message);
        } catch (NullPointerException e) {
            System.out.println("idi na hui");
            //clients.get(from).sendMessage(new Message(MessagePrefixes.SERVER_CONFIGURATION, null, serverName,"Server: there is no user with this name" + message.getReceiver()));
        }
    }

    public void sendMessageToEveryoneExceptOne(String message, String from) {
        String[] names = message.split(":", 2);
        String[] notToSendTo = names[0].split(",");

        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            for (String s : notToSendTo) {
                if (!client.getKey().equals(s)) {
                    if (!client.getKey().equals(from)) {
                        //client.getValue().sendMessage("(" + from + ") -> : " + names[1]);
                    }
                }
            }
        }

       // clients.get(from).sendMessage(" -> not to (" + names[0] + "): " + names[1]);
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

        sendMessageToEveryone("Player " + name + " logged out", serverName);
    }
}
