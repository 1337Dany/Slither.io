package client.data.datasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private final Thread gameThread = new Thread(this);
    private ClientCallback callback;

    private final Socket serverSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Client(int port, String serverIp, ClientCallback callback) throws IOException {
        serverSocket = new Socket(serverIp, port);
        out = new PrintWriter(serverSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.callback = callback;
    }

    public void connect() {
        gameThread.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String clientMessage = in.readLine();
                if (callback != null) {
                    callback.onMessageReceived(clientMessage);
                }
            }
        } catch (Exception exception) {
            callback.onError(new ServerDisconnectedException());
        }
    }

    public void closeConnection() {
        try {
            serverSocket.close();
            in.close();
            out.close();
            gameThread.interrupt();
        } catch (IOException exception) {
            callback.onError(new ServerDisconnectedException());
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}