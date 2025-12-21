package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

public class FunctionsOnSleepTracker {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public List<TypedFunction<?>> fillList() {
        return List.of(new PrintCountSessions(),
                new getMinDurationSessions(),
                new getMaxDurationSessions(),
                new getAverageDurationSessions(),
                new getBadDreams(),
                new getInsomniaNights(),
                new getUserType());
    }


    static class PrintCountSessions implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            System.out.println();
            String description = "Всего сессий сна за представленный период - ";
            return new SleepAnalysisResult<>(description, unprocessedString.size());
        }
    }

    static class getMinDurationSessions implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getSplitAllSessionWithoutQuality(unprocessedString);
            String description = "Минимальная продолжительность сессии (в минутах) - ";
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() / 2, i -> i + 1)
                    .map(i -> Duration.between(dateTime.get(i), dateTime.get(i + 1)))
                    .toList();
            Duration duration1 = duration.stream()
                    .min(Duration::compareTo)
                    .orElse(Duration.ZERO);
            return new SleepAnalysisResult<>(description, ((int) duration1.toMinutes()));
        }
    }

    static class getMaxDurationSessions implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getSplitAllSessionWithoutQuality(unprocessedString);
            ;
            String description = "Максимальная продолжительность сессии (в минутах) - ";
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() - 1, i -> i + 2)
                    .map(i -> Duration.between(dateTime.get(i), dateTime.get(i + 1)))
                    .toList();
            Duration duration1 = duration.stream()
                    .max(Duration::compareTo)
                    .orElse(Duration.ZERO);
            return new SleepAnalysisResult<>(description, ((int) duration1.toMinutes()));
        }
    }

    static class getAverageDurationSessions implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getSplitAllSessionWithoutQuality(unprocessedString);
            String description = "Средняя продолжительность сессии (в минутах) - ";
            List<LocalDateTime> dateTime = strings.stream()
                    .map(s -> LocalDateTime.parse(s, formatter))
                    .toList();
            List<Duration> duration = Stream.iterate(0, i -> i < dateTime.size() / 2, i -> i + 2)
                    .map(i -> Duration.between(dateTime.get(i), dateTime.get(i + 1)))
                    .toList();
            List<Long> longList = duration.stream()
                    .map(Duration::toMinutes)
                    .toList();
            double average = longList.stream()
                    .mapToLong(Long::longValue)
                    .average()
                    .orElse(0);
            return new SleepAnalysisResult<>(description, (int) average);
        }
    }

    static class getBadDreams implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getAllQuality(unprocessedString);
            ;
            String description = "Количество сессий с плохим качество сна - ";
            List<String> badQuality = strings.stream()
                    .filter(s -> s.equals("BAD"))
                    .toList();
            return new SleepAnalysisResult<>(description, badQuality.size());
        }
    }

    static class getInsomniaNights implements TypedFunction<Integer> {
        @Override
        public SleepAnalysisResult<Integer> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getSplitAllSessionWithoutQuality(unprocessedString);
            String description = "Сколько дней за всё время логирования сна пользователь не спал ночью - ";
            LocalDateTime start = LocalDateTime.parse(strings.getFirst(), formatter);
            LocalDateTime end = LocalDateTime.parse(strings.getLast(), formatter);
            int days = (int) ChronoUnit.DAYS.between(start, end);
            long insomniaNights = Stream.iterate(0, i -> i < strings.size() - 1, i -> i + 2)
                    .filter(i -> {
                        LocalDateTime time1 = LocalDateTime.parse(strings.get(i), formatter);
                        LocalDateTime time2 = LocalDateTime.parse(strings.get(i + 1), formatter);
                        return (time1.getDayOfMonth() + 1 != time2.getDayOfMonth() ||
                                (!time1.toLocalTime().isAfter(LocalTime.of(12, 0)) &&
                                        !time2.toLocalTime().isBefore(LocalTime.of(6, 0))));
                    })
                    .count();
            return new SleepAnalysisResult<>(description, days - (strings.size() / 2) + (int) insomniaNights);
        }
    }

    static class getUserType implements TypedFunction<String> {
        @Override
        public SleepAnalysisResult<String> apply(Sessions sessions) {
            List<String> unprocessedString = sessions.getSessions().stream()
                    .map(SleepingSession::getSession)
                    .toList();
            List<String> strings = SplitSession.getSplitAllSessionWithoutQuality(unprocessedString);
            ;
            String description = "Тип пользователя опираясь на сессии сна - ";
            List<String> types = Stream.iterate(0, i -> i < strings.size() - 1, i -> i + 2)
                    .filter(i -> {
                        LocalDateTime time1 = LocalDateTime.parse(strings.get(i), formatter);
                        LocalDateTime time2 = LocalDateTime.parse(strings.get(i + 1), formatter);
                        return (time1.getDayOfMonth() + 1 == time2.getDayOfMonth() ||
                                (time1.toLocalTime().isAfter(LocalTime.of(12, 0)) &&
                                        time2.toLocalTime().isBefore(LocalTime.of(6, 0))));
                    })
                    .map(i -> {
                        LocalDateTime time1 = LocalDateTime.parse(strings.get(i), formatter);
                        LocalDateTime time2 = LocalDateTime.parse(strings.get(i + 1), formatter);
                        if (time1.toLocalTime().isAfter(LocalTime.of(23, 0))
                                && (time2.toLocalTime().isBefore(LocalTime.of(9, 0)))) {
                            return "OWL";
                        } else if (time1.toLocalTime().isBefore(LocalTime.of(22, 0))
                                && (time2.toLocalTime().isAfter(LocalTime.of(7, 0)))) {
                            return "LAKE";
                        } else {
                            return "PIGEON";
                        }
                    })
                    .toList();

            long owlType = types.stream()
                    .filter(s -> s.equals("OWL"))
                    .count();
            long lakeType = types.stream()
                    .filter(s -> s.equals("LAKE"))
                    .count();
            long pigeonType = types.stream()
                    .filter(s -> s.equals("PIGEON"))
                    .count();
            if (owlType > lakeType && owlType > pigeonType) {
                return new SleepAnalysisResult<>(description, "OWL");
            } else if (lakeType > owlType && lakeType > pigeonType) {
                return new SleepAnalysisResult<>(description, "LAKE");
            } else {
                return new SleepAnalysisResult<>(description, "PIGEON");
            }

        }
    }
}