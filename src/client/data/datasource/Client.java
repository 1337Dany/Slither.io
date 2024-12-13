package client.data.datasource;

import shared.GameConfiguration;
import shared.Message;
import shared.MessagePrefixes;
import shared.Packet;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private final Thread gameThread = new Thread(this);
    private final ClientCallback callback;
    String name;
    private final Socket serverSocket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;

    public Client(int port, String serverIp, ClientCallback callback) throws IOException {
        serverSocket = new Socket(serverIp, port);
        out = new ObjectOutputStream(serverSocket.getOutputStream());
        in = new ObjectInputStream(serverSocket.getInputStream());
        this.callback = callback;
    }

    public void connect(String name) {
        this.name = name;
        gameThread.start();
    }

    @Override
    public void run() {
        try {
            //Sending name first to check similarities
            sendMessage(new Message(MessagePrefixes.CHAT_CONFIGURATION, null, null, name));
            while (true) {
                Packet clientMessage = (Packet) in.readObject();
                if (callback != null) {
                    if (clientMessage instanceof Message) {
                        callback.onMessageReceived((Message) clientMessage);
                    } else if (clientMessage instanceof GameConfiguration) {
                        callback.gameConfigurationReceived((GameConfiguration) clientMessage);
                    }
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

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException exception) {
            System.out.println("Send message error");
        }
    }
}