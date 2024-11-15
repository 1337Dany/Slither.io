package DATA;

public enum Data {

    ICON("src/DATA/images/gameIcon.png"),
    FONT("src/DATA/fonts/Montserrat-Bold.ttf");

    private final String path;

    Data(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}