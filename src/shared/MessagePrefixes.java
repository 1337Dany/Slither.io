package shared;

import java.io.Serializable;

public enum MessagePrefixes implements Serializable {
    TOALL("To all"),
    WHISPER("To"),
    EXCEPTWHISPER("To not"),
    CHAT_CONFIGURATION("Server"),
    CHAT_HISTORY("History"),
    TAB_CONFIGURATION("Tab"),
    KICK("Kicked"),
    CONNECTION_RESET("Connection reset");
    private static final long serialVersionUID = 1L;
    private final String value;

    MessagePrefixes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
