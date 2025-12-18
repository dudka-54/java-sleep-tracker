package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class FunctionsOnSleepTracker {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

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
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() / 2, i -> i + 1 )
                    .map(i -> Duration.between(dateTime.get(i),dateTime.get(i + 1) ))
                    .toList();
             Duration duration1 = duration.stream()
                    .min(Duration::compareTo)
                    .orElse(Duration.ZERO);
            return (int) duration1.toMinutes();
        }
    }

    static class maxDurationSessions implements Function<List<String>, Integer> {
        @Override
        public Integer apply(List<String> strings) {
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() / 2, i -> i + 1 )
                    .map(i -> Duration.between(dateTime.get(i),dateTime.get(i + 1) ))
                    .toList();
            Duration duration1 = duration.stream()
                    .max(Duration::compareTo)
                    .orElse(Duration.ZERO);
            return (int) duration1.toMinutes();
        }
    }

    static class averageDurationSessions implements Function<List<String>, Integer> {
        @Override
        public Integer apply(List<String> strings) {
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() / 2, i -> i + 1)
                    .map(i -> Duration.between(dateTime.get(i), dateTime.get(i + 1)))
                    .toList();
            List<Long> longList = duration.stream()
                    .map(Duration::toMinutes)
                    .toList();
            double average = longList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0);
            return (int) average;
        }
    }

    static class badSleep implements Function<List<String>, Integer> {
        @Override
        public Integer apply(List<String> strings) {
            List<String> badQuality = strings.stream()
                    .filter(s -> s.equals("BAD"))
                    .toList();
                return badQuality.size();
        }
    }

        static class insomniaNights implements  Function<List<String>, Integer> {
            @Override
            public Integer apply(List<String> strings) {
                LocalDateTime start = LocalDateTime.parse(strings.getFirst(), formatter);
                LocalDateTime end = LocalDateTime.parse(strings.getLast(), formatter);
                long days = 
            }
        }

    }