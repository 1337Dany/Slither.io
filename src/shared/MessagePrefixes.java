package shared;

import java.io.Serializable;

public enum MessagePrefixes implements Serializable {
    TOALL("To all"),
    WHISPER("To"),
    EXCEPTWHISPER("To not");
    private static final long serialVersionUID = 1L;
    private final String value;

    MessagePrefixes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
