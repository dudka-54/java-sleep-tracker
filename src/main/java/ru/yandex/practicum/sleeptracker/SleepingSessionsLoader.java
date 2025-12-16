package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.lines;

public class SleepingSessionsLoader {

    public Sessions sessionsLoader() throws IOException {
        List<SleepingSession> sessions = List.of();
        try {
            List<String> sleepingSessions = lines(Paths.get("src/main/resources/sleep_log.txt"), StandardCharsets.UTF_8)
                    .toList();
        sessions = sleepingSessions.stream()
                .map(SleepingSession::new)
                .toList();
    } catch(IOException e){
            System.out.println("Файл не найден " + e.getMessage());
        }
        return new Sessions(sessions);
    }
}
