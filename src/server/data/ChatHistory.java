package server.data;

import java.util.ArrayList;

public record ChatHistory() {
    private static final ArrayList<String> history = new ArrayList<>();
    private static int index = 0;

    public void addNote(String text) {
        history.add(text);
    }
    public String getNextTextArea() {
        if (index < history.size()) {
            return history.get(index++);
        } else {
            index = 0;
            return null;
        }
    }
}
