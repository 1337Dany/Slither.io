package client.DATA;

public enum Data {

    ICON("src/client/DATA/images/gameIcon.png"),
    FONT("src/client/DATA/fonts/Montserrat-Bold.ttf");

    private final String path;

    Data(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}