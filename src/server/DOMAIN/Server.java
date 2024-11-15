package server.DOMAIN;

import client.DOMAIN.GameManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int port = 9999;

    public static void main(String[] args) {
        Server server = new Server();

        startServer();
    }

    private static void startServer(){
            try(ServerSocket serverSocket = new ServerSocket(port)){

                while (true){
                    Socket clientSocket = serverSocket.accept();
                    ClientManager clientManager = new ClientManager(clientSocket);
                    System.out.println("Client " + clientSocket.getInetAddress() + " connected successfully");
                }

            }catch (IOException e){
                System.out.println("Client disconnected");
            }
    }
}
