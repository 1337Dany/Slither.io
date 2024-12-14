package shared;

public class GameConfiguration implements Packet {
    private static final long serialVersionUID = 1L;
    private MessagePrefixes prefix;
    private String tabTags;

    public GameConfiguration(MessagePrefixes messagePrefixes, String tabTags) {
        this.prefix = messagePrefixes;
        this.tabTags = tabTags;
    }

    public String getTabTags() {
        return tabTags;
    }

    public void setTabTags(String tabTags) {
        this.tabTags = tabTags;
    }

    public MessagePrefixes getPrefix() {
        return prefix;
    }

    public void setPrefix(MessagePrefixes prefix) {
        this.prefix = prefix;
    }
}
