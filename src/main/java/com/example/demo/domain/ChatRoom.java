/*package com.example.demo.domain;

import com.example.demo.utils.events.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private List<Observer> participants = new ArrayList<>();
    private String messageHistory = "";

    public void addParticipant(Observer participant) {
        participants.add(participant);
        participant.update(messageHistory);
    }

    public void removeParticipant(Observer participant) {
        participants.remove(participant);
    }

    public void sendMessage(String message) {
        messageHistory += message + "\n";
        for (Observer participant : participants) {
            participant.update(message);
        }
    }
}*/