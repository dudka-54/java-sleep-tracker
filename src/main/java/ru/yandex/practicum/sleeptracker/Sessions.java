package ru.yandex.practicum.sleeptracker;

import java.util.ArrayList;
import java.util.List;

public class Sessions {
    public List<SleepingSession> getSessions() {
        return sessions;
    }

    List<SleepingSession> sessions = new ArrayList<>();

    public Sessions(List<SleepingSession> sessions) {
        this.sessions = sessions;
    }
}
