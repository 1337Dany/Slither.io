package server.DOMAIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final static int port = 9999;
    static ArrayList<ClientManager> clients = new ArrayList<>();

    public static void main(String[] args) {
        Server server = new Server();

        startServer();
    }

    private static void startServer(){
            try(ServerSocket serverSocket = new ServerSocket(port)){

                while (true){
                    Socket clientSocket = serverSocket.accept();
                    ClientManager clientManager = new ClientManager(clientSocket);
                    clientManager.run();
                    clients.add(clientManager);
                    System.out.println("Client " + clientSocket.getInetAddress() + " connected successfully");

                    sendMessageToEveryone("Test");
                }

            }catch (IOException e){
                System.out.println("Client disconnected");
            }
    }
    private static void sendMessageToEveryone(String message){
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).sendMessage(message);
        }
    }
}
