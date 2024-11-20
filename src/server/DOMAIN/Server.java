package server.DOMAIN;

import server.DATA.ChatHistory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final BannedPhrases bannedPhrases = new BannedPhrases();
    private final ChatHistory chatHistory = new ChatHistory();
    private final static int port = 9999;
    static Map<String, ClientManager> clients = new HashMap<>();

    public static void main(String[] args) {
        Server server = new Server();

        server.startServer();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientManager clientManager = new ClientManager(this, clientSocket);

                //  Check of existing player with the same name
                if(clients.containsKey(clientManager.getName())) {
                    clientManager.sendMessage("Server: player with this name is already exist");
                    clientManager.closeConnection();
                    continue;
                }


                clientManager.sendMessage("Server: connection established!");
                clientManager.sendMessage("Server: do not use this phrases in chat! ->" + bannedPhrases.getAllBanPhrases());
                sendMessageToEveryone("Server: " + clientManager.getName() + " connected!");

                //  send all chat data to new user
                String tmp;
                while ((tmp = chatHistory.getNextTextArea()) != null) {
                    clientManager.sendMessage(tmp);
                }

                new Thread(clientManager).start();
                clients.put(clientManager.getName(), clientManager);
                System.out.println("Client " + clientManager.getFullAddress() + " connected successfully");

            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    public void sendMessageToEveryone(String message) {
        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            client.getValue().sendMessage(message);
        }
    }

    public void sendMessageTo(String message, String from) {
        try {
            String[] names = message.split(":", 2);
            for (String name : (names[0].split(","))) {
                clients.get(name).sendMessage("(" + from + ") -> : " + names[1]);
            }
            clients.get(from).sendMessage(" -> (" + names[0] + "): " + names[1]);
        }catch (NullPointerException e){
            clients.get(from).sendMessage("Server: there is no user with this name");
        }
    }

    public void sendMessageToEveryoneExceptOne(String message, String from) {
        String[] names = message.split(":", 2);
        String[] notToSendTo = names[0].split(",");

        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            for (String s : notToSendTo) {
                if (!client.getKey().equals(s)) {
                    if (!client.getKey().equals(from)) {
                        client.getValue().sendMessage("(" + from + ") -> : " + names[1]);
                    }
                }
            }
        }

        clients.get(from).sendMessage(" -> not to (" + names[0] + "): " + names[1]);
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public BannedPhrases getBannedPhrases() {
        return bannedPhrases;
    }

    public void kickUser(String name) {
        clients.get(name).closeConnection();
        clients.remove(name);
        System.out.println(name + " removed");
    }
}
