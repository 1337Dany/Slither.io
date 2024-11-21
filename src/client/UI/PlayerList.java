package client.UI;

import client.DOMAIN.SettingsSetter;

import javax.swing.*;
import java.awt.*;

public class PlayerList extends JPanel {
    private final SlitherPanel slitherPanel;
    private static final Dimension playerListSize = new Dimension(300, 200);
    private boolean isVisible = false;

    public PlayerList(SlitherPanel slitherPanel) {
        this.slitherPanel = slitherPanel;
        SettingsSetter.ignoreSettingParametersToObjects(this);
        draw();
    }

    private void draw() {
        setBackground(new Color(0,0,0,100));
        setSize(playerListSize);
        SettingsSetter.ignoreSettingParametersToObjects(this);
        setVisible(isVisible);
        setLocation(slitherPanel.getWidth()/2 - playerListSize.width/2,
                0);
    }
    public void showPlayerList(){
        setVisible(true);
        isVisible = true;
    }
    public void hidePlayerList(){
        setVisible(false);
        isVisible = false;
    }
    public boolean getIsVisible(){
        return isVisible;
    }
}
