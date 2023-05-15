package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class FriendRequest extends Entity<UUID> {
    private final Utilizator from;
    private final Utilizator to;
    private RequestStatus status;
    private final LocalDateTime date;

    public FriendRequest(UUID Id,Utilizator from, Utilizator to) {
        this.from = from;
        this.to = to;
        this.status = RequestStatus.PENDING;
        date = LocalDateTime.now();
        this.id=Id;

    }
    public FriendRequest(Utilizator from, Utilizator to) {
        this.from = from;
        this.to = to;
        this.status = RequestStatus.PENDING;
        date = LocalDateTime.now();
        this.setId(UUID.randomUUID());
    }

    public FriendRequest(Utilizator from, Utilizator to, RequestStatus status, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.status = status;
        this.date = date;
        this.setId(UUID.randomUUID());
    }

    public Utilizator getFrom() {
        return from;
    }

    public UUID getFromId() {
        return from.getId();
    }

    public Utilizator getTo() {
        return to;
    }

    public UUID getToId() {
        return to.getId();
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public static String statusToString(RequestStatus status) {
        switch(status) {
            case PENDING: return "PENDING";
            case APPROVED: return "APPROVED";
            case REJECTED: return "REJECTED";
            case CANCELLED: return "CANCELLED";
            default: return null;
        }
    }

    public static RequestStatus stringToStatus(String status) {
        switch (status) {
            case "PENDING": return RequestStatus.PENDING;
            case "APPROVED": return RequestStatus.APPROVED;
            case "REJECTED": return RequestStatus.REJECTED;
            case "CANCELLED": return RequestStatus.CANCELLED;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return "Invite{" +
                "\n from=" + from +
                ",\n status=" + status +
                ",\n date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest invite = (FriendRequest) o;
        return from.equals(invite.from) &&
                to.equals(invite.to) &&
                status == invite.status &&
                date.equals(invite.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, status, date);
    }
}
