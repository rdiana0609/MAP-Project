package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Utilizator extends Entity<UUID> {
    private String username;
    private String email;
    private Map<UUID,Utilizator> prieteni;
    public Utilizator(UUID id,String username,String email)
    { this.username=username;
        this.email=email;
    this.id=id;}
    public Utilizator(String username,String email) {
     this.username=username;
     this.email=email;
     this.setId(UUID.randomUUID());
     prieteni=new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Iterable<Utilizator> getPrieteni() {
        return prieteni.values();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFriend(Utilizator prieten) {
        this.prieteni.put(prieten.id,prieten);
    }
    public void unfriend(Utilizator prieten){
        this.prieteni.remove(prieten.id,prieten);
    }
    @Override
    public String toString() {
        return "Utilizator{" +
                "username='" + username + '\'' +
                ", email='" + email + '\''
                ;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getUsername().equals(that.getUsername()) &&
               getEmail().equals(that.getEmail());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getEmail());
    }
}
