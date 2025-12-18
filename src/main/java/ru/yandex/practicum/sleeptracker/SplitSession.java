package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitSession {
    public static List<String> getSplitSessionWithoutQuality (SleepingSession session){
        String[] sessionArray = session.getSession().split(";");
        List<String> list = Arrays.stream(sessionArray).toList();
        List<String> mutableList = new ArrayList<>(list);
        mutableList.removeLast();
        return mutableList;
    }

    public static List<String> getSplitAllSessionWithoutQuality (Sessions sessions){
        List<SleepingSession> sessionsList = sessions.getSessions();
        return sessionsList.stream()
                .map(SleepingSession::getSession)
                .map(s -> {
                   List<String> strings = new ArrayList<>( Arrays.asList(s.split(";")));
                           if(!strings.isEmpty()){
                               strings.removeLast();
                           }
                           return strings;
                })
                .flatMap(List::stream)
                        .toList();
    }

    public static List<String> getAllQuality (Sessions sessions){
        List<SleepingSession> sessionsList = sessions.getSessions();
        return sessionsList.stream()
                .map(SleepingSession::getSession)
                .map(s -> {
                    List<String> strings = new ArrayList<>( Arrays.asList(s.split(";")));
                    return strings.getLast();
                })
                .toList();
    }

    public static List<String> getAllSession(SleepingSession session){
        return Arrays.stream(session.getSession().split(";")).toList();
    }

    public static String getQuality(SleepingSession session){
        return Arrays.stream(session.getSession().split(";")).toList().getLast();
    }



}
