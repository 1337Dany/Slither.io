package server.DOMAIN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager implements Runnable {
    private final Server server;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String myName;
    private final String myIp;
    private final int myPort;
    public ClientManager(Server server, Socket socket){
        this.server = server;
        myIp = String.valueOf(socket.getInetAddress());
        myPort = socket.getPort();

        //  Establish io streams
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            myName = in.readLine();
        }catch (IOException e){
            System.out.println("Client " + getFullAddress() + " couldn't connect!");
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                String clientMessage = in.readLine();
                actionPerform(clientMessage);
            }

        }catch (IOException ignored){
            System.out.println("Client " + myIp + ":" + myPort + " disconected");
        }
    }

    private void actionPerform(String message){

    }

    public void sendMessage(String message){
        out.println(message);
    }

    public String getFullAddress(){
        return myIp + ":" + myPort;
    }
    public String getName(){
        return myName;
    }
}
