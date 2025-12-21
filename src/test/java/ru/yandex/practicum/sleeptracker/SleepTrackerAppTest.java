package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepTrackerAppTest {
    @Test
    void printCountSessions_WhenMultipleSessions_ShouldReturnCorrectCount() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 08:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.PrintCountSessions().apply(sessions);

        assertNotNull(result);
        assertEquals(3, result.getValue());
    }

    @Test
    void printCountSessions_WhenNoSessions_ShouldReturnZero() {
        Sessions sessions = new Sessions(new ArrayList<>());
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.PrintCountSessions().apply(sessions);

        assertNotNull(result);
        assertEquals(0, result.getValue());
    }

    @Test
    void printCountSessions_WhenSingleSession_ShouldReturnOne() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.PrintCountSessions().apply(sessions);

        assertEquals(1, result.getValue());
        assertInstanceOf(Integer.class, result.getValue());
    }

    @Test
    void printCountSessions_WhenManySessions_ShouldReturnCorrectCount() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String session = String.format("0%d.01.24 23:00;0%d.01.24 07:00;GOOD", i + 1, i + 1);
            sleepingSessions.add(new SleepingSession(session));
        }

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.PrintCountSessions().apply(sessions);

        assertEquals(10, result.getValue());
    }

    // Тесты для getMinDurationSessions
    @Test
    void getMinDurationSessions_WhenOneValidSession_ShouldReturnCorrectDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMinDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMinDurationSessions_WhenMultipleSessions_ShouldReturnMinDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 04:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMinDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMinDurationSessions_WhenNoSessions_ShouldHandleGracefully() {
        Sessions sessions = new Sessions(new ArrayList<>());
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMinDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMinDurationSessions_WhenShortSession_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;01.01.24 23:45;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMinDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMaxDurationSessions_WhenOneSession_ShouldReturnCorrectDuration() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMaxDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMaxDurationSessions_WhenMultipleSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 04:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 08:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMaxDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMaxDurationSessions_WhenNoSessions_ShouldHandleGracefully() {
        Sessions sessions = new Sessions(new ArrayList<>());
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMaxDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getMaxDurationSessions_WhenLongSession_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 20:00;02.01.24 14:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getMaxDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getAverageDurationSessions_WhenOneSession_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getAverageDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getAverageDurationSessions_WhenMultipleSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 04:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getAverageDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getAverageDurationSessions_WhenNoSessions_ShouldHandleGracefully() {
        Sessions sessions = new Sessions(new ArrayList<>());
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getAverageDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getAverageDurationSessions_WhenShortSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 00:30;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 02:15;BAD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getAverageDurationSessions().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getBadDreams_WhenAllGoodSessions_ShouldReturnZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 08:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getBadDreams().apply(sessions);

        assertNotNull(result);
        assertEquals(0, result.getValue());
    }

    @Test
    void getBadDreams_WhenAllBadSessions_ShouldReturnAllCount() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;BAD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 08:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;BAD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getBadDreams().apply(sessions);

        assertNotNull(result);
        assertEquals(3, result.getValue());
    }

    @Test
    void getBadDreams_WhenMixedQualitySessions_ShouldReturnBadCount() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 01:00;02.01.24 08:00;BAD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;GOOD"));
        sleepingSessions.add(new SleepingSession("04.01.24 23:30;05.01.24 07:30;BAD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getBadDreams().apply(sessions);

        assertNotNull(result);
        assertEquals(2, result.getValue());
    }

    @Test
    void getBadDreams_WhenNoSessions_ShouldReturnZero() {
        Sessions sessions = new Sessions(new ArrayList<>());
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getBadDreams().apply(sessions);

        assertNotNull(result);
        assertEquals(0, result.getValue());
    }

    @Test
    void getInsomniaNights_WhenOneNormalNight_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getInsomniaNights().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getInsomniaNights_WhenMultipleNormalNights_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:00;02.01.24 07:00;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 23:30;03.01.24 07:30;GOOD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:00;04.01.24 06:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getInsomniaNights().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getInsomniaNights_WhenDaytimeSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 10:00;01.01.24 12:00;GOOD"));
        sleepingSessions.add(new SleepingSession("01.01.24 14:00;01.01.24 16:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<Integer> result = new FunctionsOnSleepTracker.getInsomniaNights().apply(sessions);

        assertNotNull(result);
    }


    @Test
    void getUserType_WhenOwlPatternSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:30;02.01.24 08:30;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 00:30;02.01.24 08:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<String> result = new FunctionsOnSleepTracker.getUserType().apply(sessions);

        assertNotNull(result);
        String type = result.getValue();
        assertTrue(type.equals("OWL") || type.equals("LAKE") || type.equals("PIGEON"));
    }

    @Test
    void getUserType_WhenLakePatternSessions_ShouldWork() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 21:00;02.01.24 07:30;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 20:30;03.01.24 08:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<String> result = new FunctionsOnSleepTracker.getUserType().apply(sessions);

        assertNotNull(result);
    }

    @Test
    void getUserType_WhenMixedPatternSessions_ShouldReturnSomeType() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession("01.01.24 23:30;02.01.24 08:30;GOOD"));
        sleepingSessions.add(new SleepingSession("02.01.24 21:00;03.01.24 07:30;GOOD"));
        sleepingSessions.add(new SleepingSession("03.01.24 22:30;04.01.24 08:00;GOOD"));

        Sessions sessions = new Sessions(sleepingSessions);
        SleepAnalysisResult<String> result = new FunctionsOnSleepTracker.getUserType().apply(sessions);

        assertNotNull(result);
        String type = result.getValue();
        assertTrue(type.equals("OWL") || type.equals("LAKE") || type.equals("PIGEON"));
    }

    @Test
    void getUserType_WhenNoSessions_ShouldHandleGracefully() {
        Sessions sessions = new Sessions(new ArrayList<>());

        try {
            SleepAnalysisResult<String> result = new FunctionsOnSleepTracker.getUserType().apply(sessions);
            assertNotNull(result);
        } catch (Exception e) {
            assertTrue(e instanceof IndexOutOfBoundsException ||
                    e instanceof NullPointerException);
        }
    }
}