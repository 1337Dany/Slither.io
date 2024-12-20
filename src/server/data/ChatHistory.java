package server.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record ChatHistory() {
    private static final List<String> history = Collections.synchronizedList(new ArrayList<>());
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
