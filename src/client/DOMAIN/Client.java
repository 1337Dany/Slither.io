package client.DOMAIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {
    private static GameManager gameManager;
    private String username;
    private String ip;
    private static int port = 9999;

    private Socket serverSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public static void main(String[] args) {
        gameManager = new GameManager();
    }

    public boolean connect() {
        try {
            serverSocket = new Socket(ip, port);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            sendMessage(username);
            new Thread(this).start();
            return true;
        } catch (IOException ignored) {
            System.out.println(false);
            return false;
        }

    }
    @Override
    public void run() {
        try {
            while (true){
                String clientMessage = in.readLine();
                System.out.println(clientMessage);
                gameManager.actionPerform(clientMessage);
            }
        } catch (IOException exception) {
            System.out.println("Server disconnected");
            gameManager.returnToMenu();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public void sendMessage(String message){
        out.println(message);
    }
}