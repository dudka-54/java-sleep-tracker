package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    public String getDescription() {
        return description;
    }

    public T getValue() {
        return value;
    }

    private final String description;
    private final T value;

    public SleepAnalysisResult(String description, T value) {
        this.description = description;
        this.value = value;
    }

    public static SleepAnalysisResult<Integer> intResult(String description, Integer value) {
        return new SleepAnalysisResult<>(description, value);
    }

    public static SleepAnalysisResult<String> stringResult(String description, String value) {
        return new SleepAnalysisResult<>(description, value);
    }
}
