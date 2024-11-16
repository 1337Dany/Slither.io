package server.DOMAIN;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
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

                    sendMessageToEveryone("Test");
                }

            }catch (IOException e){
                System.out.println("Client disconnected");
            }
    }
    private static void sendMessageToEveryone(String message){
        for (Map.Entry<String, ClientManager> client : clients.entrySet()) {
            client.getValue().sendMessage(message);
        }
    }

    public void kickUser(String name){
        clients.remove(name);
    }
}
