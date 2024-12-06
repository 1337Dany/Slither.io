package server.data;

import shared.Message;

import java.io.*;
import java.net.Socket;

public class ClientManager implements Runnable {
    private final Server server;
    private final Socket socket;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private String myName;
    private final String myIp;
    private final int myPort;
    private boolean isRunning = false;

    public ClientManager(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        myIp = String.valueOf(socket.getInetAddress());
        myPort = socket.getPort();

        //  Establish io streams
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Object gettingName =  in.readObject();
           // myName = gettingName.getMessage();
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            isRunning = true;

            while (isRunning) {
                Object clientMessage = in.readObject();
                if (clientMessage instanceof Message receivedMessage) {
                    System.out.println(receivedMessage.getMessage());
                    System.out.println(receivedMessage.getPrefix());
                    System.out.println(receivedMessage.getSender());
                    actionPerform((Message) clientMessage);
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Client " + getFullAddress() + " disconected");
            server.kickUser(myName);
        }
    }

    private void actionPerform(Message message) {
        server.getChatHistory().addNote("To all: " + "(" + myName + "): " + message);
        server.sendMessageToEveryone(message.getMessage(), myName);
//        if (message.startsWith("admin s30050: ")) {
//            server.sendMessageToEveryone("Admin message: " + message.substring(14));
//            if (message.contains("kick: ")) {
//                server.kickUser(message.substring(20));
//            }
//        } else if (!server.getBannedPhrases().containsBanPharases(message)) {
//            if (message.startsWith("To ")) {
//                if (message.startsWith("To all: ")) {
//                    server.getChatHistory().addNote("To all: " + "(" + myName + "): " + message.substring(8));
//                    server.sendMessageToEveryone("To all: " + "(" + myName + "): " + message.substring(8));
//                } else if (message.startsWith("To not ")) {
//                    server.sendMessageToEveryoneExceptOne(message.substring(7), myName);
//                } else {
//                    server.sendMessageTo(message.substring(3), myName);
//                }
//            } else {
//                server.getChatHistory().addNote("To all: " + "(" + myName + "): " + message);
//                server.sendMessageToEveryone("To all: " + "(" + myName + "): " + message);
//            }
//        } else {
//            sendMessage("Server: Inappropriate message detected in (" + message + ")");
//        }
    }

    public void sendMessage(Object message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            socket.close();
            in.close();
            out.close();
            isRunning = false;
        } catch (IOException ignored) {
        }
    }

    public String getFullAddress() {
        return myIp + ":" + myPort;
    }

    public String getName() {
        return myName;
    }
}
