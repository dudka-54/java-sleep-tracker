package ru.yandex.practicum.sleeptracker;

import java.util.*;
import java.util.function.Function;

@FunctionalInterface
interface TypedFunction<R> {

    public SleepAnalysisResult<R> apply(Sessions sessions);
}
