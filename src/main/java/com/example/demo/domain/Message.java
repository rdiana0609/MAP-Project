package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message extends Entity<UUID>{
        private UUID id;
        private LocalDateTime sentAt;
        private String subject;
        private String text;
        private Utilizator sender;
        private Utilizator receiver;
        private String senderName;

        public Message(LocalDateTime sentAt, String subject, String text, Utilizator sender, Utilizator receiver) {
            this.sentAt = sentAt;
            this.subject = subject;
            this.text = text;
            this.sender = sender;
            this.receiver = receiver;
            this.senderName=sender.getUsername();
            this.setId(UUID.randomUUID());
        }
    public Message( String subject, String text, Utilizator sender, Utilizator receiver) {
        this.subject = subject;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.sentAt=LocalDateTime.now();
        this.senderName=sender.getUsername();
        this.setId(UUID.randomUUID());
    }

    public String getSenderName() {
        return senderName;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Utilizator getReceiver() {
        return receiver;
    }

    public void setReceiver(Utilizator receiver) {
        this.receiver = receiver;
    }

    public Utilizator getSender() {
        return sender;
    }

    public void setSender(Utilizator sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(sentAt, message.sentAt) && Objects.equals(subject, message.subject) && Objects.equals(text, message.text) && Objects.equals(sender, message.sender) && Objects.equals(receiver, message.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sentAt, subject, text, sender, receiver);
    }

    @Override
    public String toString() {
        return " text='" + text
               ;
    }
}
