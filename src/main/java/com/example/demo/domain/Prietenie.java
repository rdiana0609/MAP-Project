package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Prietenie extends Entity<UUID>{
    private Utilizator prieten1;
    private Utilizator prieten2;
    private LocalDateTime friendsFrom;
    public Prietenie(Utilizator prieten1,Utilizator prieten2){
        this.prieten1=prieten1;
        this.prieten2=prieten2;
        this.setId(UUID.randomUUID());
        friendsFrom = LocalDateTime.now();
    }

    public Prietenie(UUID id,Utilizator user1, Utilizator user2, LocalDateTime friendsFrom) {
        this.id=id;
        this.prieten1= user1;
        this.prieten2 = user2;
        this.setId(UUID.randomUUID());
        this.friendsFrom = friendsFrom;
    }

    public Prietenie(Utilizator user1, Utilizator user2, LocalDateTime friendsFrom) {
        this.prieten1= user1;
        this.prieten2 = user2;
        this.setId(UUID.randomUUID());
        this.friendsFrom = friendsFrom;
    }
    public LocalDateTime getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDateTime friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Utilizator getPrieten1() {
        return prieten1;
    }

    public Utilizator getPrieten2() {
        return prieten2;
    }

    public void setPrieten1(Utilizator prieten1) {
        this.prieten1 = prieten1;
    }

    public void setPrieten2(Utilizator prieten2) {
        this.prieten2 = prieten2;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prietenie))
            return false;
        Prietenie that = (Prietenie) o;
        return id.equals(that.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getPrieten1(), getPrieten2());
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "prieten1=" + prieten1 +
                ", prieten2=" + prieten2 +
                '}';
    }
}
