package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitSession {

    public static List<String> getSplitAllSessionWithoutQuality(List<String> strings) {
        return strings.stream()
                .map(s -> {
                    List<String> strings1 = new ArrayList<>(Arrays.asList(s.split(";")));
                    if (!strings1.isEmpty()) {
                        strings1.removeLast();
                    }
                    return strings1;
                })
                .flatMap(List::stream)
                .toList();
    }

    public static List<String> getAllQuality(List<String> strings) {
        return strings.stream()
                .map(s -> {
                    List<String> strings1 = new ArrayList<>(Arrays.asList(s.split(";")));
                    return strings1.getLast();
                })
                .toList();
    }


}
