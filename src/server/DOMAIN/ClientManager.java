package server.DOMAIN;

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
    private boolean isRunning;

    public ClientManager(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        myIp = String.valueOf(socket.getInetAddress());
        myPort = socket.getPort();

        //  Establish io streams
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            myName = in.readLine();
        }catch (IOException ignored){}
    }

    @Override
    public void run() {
        try {
            isRunning = true;

            while (isRunning) {
                String clientMessage = in.readLine();
                actionPerform(clientMessage);
            }

        } catch (IOException ignored) {
            System.out.println("Client " + getFullAddress() + " disconected");
            server.kickUser(myName);
        }
    }

    private void actionPerform(String message) {
        if(!server.getBannedPhrases().containsBanPharases(message)) {
            if (message.contains("Chat: ")) {

            }
        }else{
            sendMessage("Server: Inappropriate message detected");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getFullAddress() {
        return myIp + ":" + myPort;
    }

    public String getName() {
        return myName;
    }
    public void setRunning(boolean bool){
        isRunning = bool;
    }
}