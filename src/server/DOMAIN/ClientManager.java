package server.DOMAIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager implements Runnable {
    private Socket clientSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    public ClientManager(Socket socket){
        clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            //  Establish io streams
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        }catch (IOException ignored){
            System.out.println("Client " + clientSocket.getInetAddress() + " disconected");
        }
    }

    public void sendMessage(String message){
        out.println(message);
    }
}
