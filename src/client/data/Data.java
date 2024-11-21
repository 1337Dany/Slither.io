package client.data;

public enum Data {

    ICON("src/client/resources/images/gameIcon.png"),
    FONT("src/client/resources/fonts/Montserrat-Bold.ttf");

    private final String path;

    Data(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}