package DOMAIN;

public class Client {
    private static GameManager gameManager;
    public static void main(String[] args) {
        gameManager = new GameManager();

        gameManager.startGame();
    }
}