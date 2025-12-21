package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepTrackerApp {
    public static void main(String[] args) {
        List<TypedFunction<?>> functionList = new FunctionsOnSleepTracker().fillList();
        SleepingSessionsLoader loader = new SleepingSessionsLoader();

        functionList.stream()
                .map(function -> {
                    return function.apply(loader.sessionsLoader());
                })
                .forEach(result -> {
                    System.out.println(result.getDescription() + result.getValue());
                });

    }
}