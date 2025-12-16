package ru.yandex.practicum.sleeptracker;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public class FunctionsOnSleepTracker {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");



    public List<Function<List<String>, Integer>> fillList() {
        return List.of(new PrintCountSessions());
    }


    static class PrintCountSessions implements Function<List<String>, Integer> {
        @Override
        public Integer apply(List<String> strings) {
            return strings.size();
        }
    }

    static class minDurationSessions implements Function<List<String>, Integer> {
        @Override
        public Integer apply(List<String> strings) {

        }
    }
}
