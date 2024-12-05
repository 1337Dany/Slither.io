package client.ui.slither;

import client.data.message.Message;
import client.domain.SettingsSetter;
import client.ui.chat.ChatPanel;
import client.ui.chat.IChatCallback;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SlitherPanel extends JPanel implements IChatCallback, ActionDialogContract, PlayerListContract {

    private final ISlitherCallback callback;
    private static final int SPACE = 20;//separated space for name
    private final JTextArea name = new JTextArea();
    private final ChatPanel chat = new ChatPanel(this);
    private final ActionDialog actionDialog = new ActionDialog(this);

    private final PlayerList playerList = new PlayerList(this);

    public SlitherPanel(Dimension frameSize, ISlitherCallback callback) {
        this.callback = callback;
        this.setSize(frameSize);
        this.setLayout(null);
        configure();
        configureName(frameSize);
        configureChat();
        configuratePlayerList();
    }

    public void setUsername(String username) {
        name.setText(username);
    }

    private void configureName(Dimension frameSize) {
        name.setFont(new Font(name.getFont().getFontName(), Font.PLAIN, 30));
        name.setWrapStyleWord(true);
        name.setLineWrap(true);
        name.setOpaque(false);
        name.setForeground(Color.WHITE);
        name.setBounds(((int) frameSize.getWidth()) - SPACE - ((int) frameSize.getWidth() / 2), SPACE, ((int) frameSize.getWidth() / 2), ((int) frameSize.getHeight() - SPACE * 2));
        name.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        name.setEditable(false);
        name.setFocusable(false);
        this.add(name);
    }

    private void configure() {
        this.setBackground(Color.DARK_GRAY);
        this.setLocation(0, 0);
        SettingsSetter.ignoreSettingParametersToObjects(actionDialog);
        chat.setActionDialogContract(this);
        add(actionDialog);
    }

    private void configureChat() {
        chat.setLocation(0, (this.getHeight() - chat.getHeight()) / 2);
        chat.setPlayerListContract(this);
        this.add(chat);
    }

    private void configuratePlayerList() {
        playerList.setLocation(this.getWidth() / 2 - playerList.getWidth()/2,
                0
        );

        setFocusTraversalKeysEnabled(false);

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

    public void addGamer(String name) {
        playerList.addPlayer(name);
    }

    public void removePlayer(String name) {
        playerList.removePlayer(name);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawHexagons(graphics);
    }

    private void drawHexagons(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(Color.LIGHT_GRAY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // SIZE OF HEXAGONS
        int size = 45;

        // high of hexagon
        double high = Math.sqrt(3) * size;
        int cols = (int) (width / (1.5 * size)) + 1;
        int rows = (int) (height / high) + 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = (int) (col * 1.5 * size);
                int y = (int) (row * high);
                if (col % 2 == 1) {
                    y += (int) (high / 2);
                }
                drawHexagon(g2, x, y, size);
            }
        }
    }

    private void drawHexagon(Graphics2D g2, int x, int y, int sideLength) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (x + sideLength * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (y + sideLength * Math.sin(i * Math.PI / 3));
        }
        g2.drawPolygon(xPoints, yPoints, 6);
    }

    public void onMessageReceived(Message message) {
        chat.addMessage(message);
    }

    @Override
    public void sendMessage(Message message) {
        callback.sendMessage(message);
    }

    @Override
    public void showActionDialog(int x, int y) {
        actionDialog.setLocation(x,y);
        actionDialog.setVisible(true);
    }

    @Override
    public void hideActionDialog() {
        actionDialog.setVisible(false);
    }

    @Override
    public void setSender(String sender) {
        actionDialog.setSender(sender);
    }

    @Override
    public void callPlayerList() {
        if (playerList.getIsVisible()) {
            playerList.hidePlayerList();
        } else {
            playerList.showPlayerList();
        }
    }
}
