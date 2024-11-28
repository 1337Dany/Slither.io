package server.domain;

import server.data.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager implements Runnable {
    private final Server server;
    private final Socket socket;
    private BufferedReader in = null;
    private PrintWriter out = null;
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
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            myName = in.readLine();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void run() {
        try {
            isRunning = true;

            while (isRunning) {
                String clientMessage = in.readLine();
                System.out.println(clientMessage);
                actionPerform(clientMessage);
            }

        } catch (IOException ignored) {
            System.out.println("Client " + getFullAddress() + " disconected");
            server.kickUser(myName);
        }
    }

    private void actionPerform(String message) {
        server.getChatHistory().addNote("To all: " + "(" + myName + "): " + message);
        server.sendMessageToEveryone("To all: " + "(" + myName + "): " + message);
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

    public void sendMessage(String message) {
        out.println(message);
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
