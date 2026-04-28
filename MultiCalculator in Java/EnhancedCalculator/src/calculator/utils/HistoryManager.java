package calculator.utils;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {

    private List<String> history = new ArrayList<>();

    public void addEntry(String entry) {
        history.add(entry);
    }

    public List<String> getHistory() {
        return history;
    }

    public void clearHistory() {
        history.clear();
    }

    public void loadHistory() {}
}
