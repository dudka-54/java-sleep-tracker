package ru.yandex.practicum.sleeptracker;

@FunctionalInterface
interface TypedFunction<R> {

    public SleepAnalysisResult<R> apply(Sessions sessions);
}
