package server.DOMAIN;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private final BannedPhrases bannedPhrases = new BannedPhrases();
    private final static int port = 9999;
    static Map<String, ClientManager> clients = new HashMap<>();

    public static void main(String[] args) {
        Server server = new Server();

        server.startServer();
    }

    private void startServer(){
            try(ServerSocket serverSocket = new ServerSocket(port)){

                while (true){
                    Socket clientSocket = serverSocket.accept();
                    ClientManager clientManager = new ClientManager(this, clientSocket);
                    new Thread(clientManager).start();
                    clients.put(clientManager.getName(), clientManager);
                    System.out.println("Client " + clientManager.getFullAddress() + " connected successfully");

                }

            }catch (IOException e){
                System.out.println("Client disconnected");
            }
    }
    public void sendMessageToEveryone(String message){
        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            client.getValue().sendMessage(message);
        }
    }
    public void sendMessageTo(String message, String from){
        String[] names = message.split(":", 2);
        for(String name : (names[0].split(","))){
            clients.get(name).sendMessage("(" + from + ")<" + names[0] + ">: " + names[1]);
        }
    }
    public BannedPhrases getBannedPhrases(){
        return bannedPhrases;
    }
    public void kickUser(String name){
        clients.get(name).closeConnection();
        clients.remove(name);
        System.out.println(name + " removed");
    }
}
