package client.DOMAIN;

public class Client {
    private static GameManager gameManager;

    private String username;
    private String ip;
    private static final String password = "UTP_Pro2";
    public static void main(String[] args) {
        gameManager = new GameManager();
    }

    public void connect(){

    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setIp(String ip){
        this.ip = ip;
    }
}