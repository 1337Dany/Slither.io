package server.data;

import shared.GameConfiguration;
import shared.Message;
import shared.MessagePrefixes;
import shared.Packet;

import java.io.*;
import java.net.Socket;

public class ClientManager {
    private final ClientManagerCallback clientManagerCallback;
    private final Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private String myName;
    private final String myIp;
    private final int myPort;
    private boolean isRunning = false;

    public ClientManager(ClientManagerCallback clientManagerCallback, Socket socket) {
        this.clientManagerCallback = clientManagerCallback;
        this.socket = socket;
        myIp = String.valueOf(socket.getInetAddress());
        myPort = socket.getPort();

        //  Establish io streams
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            GameConfiguration nameMessage = (GameConfiguration) in.readObject();
                myName = nameMessage.getTabTags();

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        try {
            isRunning = true;

            while (isRunning) {
                 Packet clientMessage = (Packet) in.readObject();
                if (clientMessage instanceof Message receivedMessage) {
                    actionPerform(receivedMessage);
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void actionPerform(Message message) {
        if(clientManagerCallback.getBannedPhrases().containsBanPharases(message.getMessage())){
            sendMessage(
                    new Message(
                            MessagePrefixes.CHAT_CONFIGURATION,
                            null,
                            clientManagerCallback.getServerName(),
                            "Inappropriate message detected in (" + message.getMessage() + ")"
                    ));
            return;
        }
        if(message.getPrefix() == MessagePrefixes.TOALL){
            clientManagerCallback.getChatHistory().addNote("(" + myName + "): " + message.getMessage());
            message.setSender(myName);
            clientManagerCallback.sendMessageToEveryone(message);
        }else if(message.getPrefix() == MessagePrefixes.WHISPER){
            message.setSender(myName);
            clientManagerCallback.sendMessageTo(message);
        }else if(message.getPrefix() == MessagePrefixes.EXCEPTWHISPER){
            message.setSender(myName);
            clientManagerCallback.sendMessageToEveryoneExceptOne(message);
        }
    }

    public void sendMessage(Packet message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFullAddress() {
        return myIp + ":" + myPort;
    }

    public String getName() {
        return myName;
    }
}