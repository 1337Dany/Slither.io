package shared;

public class GameConfiguration implements Packet{
    private static final long serialVersionUID = 1L;

    private String tabTags;

    public GameConfiguration(String tabTags) {
        this.tabTags = tabTags;
    }

    public String getTabTags() {
        return tabTags;
    }
    public void setTabTags(String tabTags) {
        this.tabTags = tabTags;
    }
}
