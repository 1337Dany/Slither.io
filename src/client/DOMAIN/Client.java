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

    private Socket clientSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public Client(Socket socket) {
        clientSocket = socket;
    }

    public Client() {
    }

    public static void main(String[] args) {
        gameManager = new GameManager();
    }

    public boolean connect() {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            return true;
        } catch (IOException ignored) {
            return false;
        }

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public void run() {

    }
}