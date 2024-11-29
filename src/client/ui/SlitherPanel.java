package client.ui;

import client.domain.GameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SlitherPanel extends JPanel {
    private final GameWindow gameWindow;
    private final GameManager gameManager;
    private ChatPanel chat;
    private static PlayerList playerList;
    public SlitherPanel(GameWindow gameWindow, GameManager gameManager){
        this.gameWindow = gameWindow;
        this.gameManager = gameManager;
        this.setSize(gameWindow.getSize());
        this.setLayout(null);

        drawChat();
        drawPanel();
        drawPlayerList();

        gameManager.setChatPanel(chat);

        gameWindow.repaint();
        gameWindow.revalidate();
    }

    private void drawPanel(){
        int space = 20;//separated space for name
        this.setBackground(Color.DARK_GRAY);
        this.setLocation(0,0);
        JTextArea name = new JTextArea(gameManager.getClient().getUsername());
        name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        name.setWrapStyleWord(true);
        name.setLineWrap(true);
        name.setOpaque(false);
        name.setForeground(Color.WHITE);
        name.setBounds(gameWindow.getWidth() - space - gameWindow.getWidth()/2,space,gameWindow.getWidth()/2, gameWindow.getHeight()-space*2);
        name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        name.setEditable(false);
        name.setFocusable(false);

        this.add(name);
        gameWindow.add(this);
    }
    private void drawChat(){
        chat = new ChatPanel(gameManager, gameWindow);
        chat.setLocation(0,(this.getHeight() - chat.getHeight())/2);
        this.add(chat);
    }

    private void drawPlayerList(){
        playerList = new PlayerList(this, gameWindow);

        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("TAB"), "handleTab");
        actionMap.put("handleTab", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callPlayerList();
            }
        });

        this.add(playerList);
    }
    public void addGamer(String name){
        playerList.addPlayer(name);
    }
    public void removePlayer(String name){
        playerList.removePlayer(name);
    }

    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(Color.LIGHT_GRAY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // SIZE OF HEXAGONS
        int size = 45;

        // heigh of hexagon
        double heigh = Math.sqrt(3) * size;
        int cols = (int) (width / (1.5 * size)) + 1;
        int rows = (int) (height / heigh) + 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = (int) (col * 1.5 * size);
                int y = (int) (row * heigh);

                if (col % 2 == 1) {
                    y += heigh / 2;
                }

                drawHexagon(g2, x, y, size);
            }
        }    }

    private void drawHexagon(Graphics2D g2, int x, int y, int sideLength) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + sideLength * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + sideLength * Math.sin(i * Math.PI / 3));
        }

        g2.drawPolygon(xPoints, yPoints, 6);
    }

    public static void callPlayerList(){
        if (playerList.getIsVisible()){
            playerList.hidePlayerList();
        }else {
            playerList.showPlayerList();
        }
    }
}