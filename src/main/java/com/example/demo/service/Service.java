package com.example.demo.service;

import com.example.demo.domain.Entity;
import com.example.demo.domain.Prietenie;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.ValidationException;

import java.util.List;

public interface Service<ID> {
    /**
     *
     * @param user - the entity that has to be added
     * @return true if the entity was added
     *         false otherwise
     * @throws IllegalArgumentException
     *                  if entity is null
     * @throws ValidationException
     *                  if the entity validation did not work.
     */
    boolean addUser(Utilizator user);

    /**
     * @param email - the id of the entity that we have to remove
     * @throws IllegalArgumentException if id is null
     */
    void deleteUser(String email);
    /**
     * @return an Iterable of all the users
     */
    Iterable<Utilizator> getAllUsers();
    /**
     * Adds predefined users and friendships
     */
    void add_Predefined_Values();
    /**
     *
     * @param email1 and
     * @param email2 - the emails of the user we have to create a friendship between
     *
     * @return true if the entity was added
     *         false otherwise
     * @throws IllegalArgumentException
     *                  if any of the emails are null
     * @throws ValidationException
     *                  if the emails are the same (you cannot add yourself as a friend)
     */
    boolean adaugaPrietenie(String email1,String email2);
    /**
     * The function deletes a friendship between two users
     *  @param email1 and
     *  @param email2 - the emails of the user we have to create a friendship between
     *
     *  @return the friendship if it exists
     *          null otherwise
     *  @throws IllegalArgumentException
     *                if any of the emails are null
     */
    Entity<ID> stergePrietenie(String email1, String email2);
    /**
     * @return an Iterable of all the friendships
     */
    Iterable<Prietenie> getAllFriendships();
    /**
     * @return an int that represents the number of communities
     */
    int numberOfCommunities();


    /**
     * Returns the most sociable community
     * the most sociable community is the community of users with the longest path
     *
     * @return an Iterable of all the most sociable communities users
     */
   // Iterable<Iterable<Utilizator>> mostSociableCommunity();

    /**
     * @returns a list of all the communities
     */
    public List<List<Utilizator>> getAllCommunities();
    public Iterable<Utilizator> getMostSociableCommunity();
    public void updateUtilizator(String email,String newvalues,Integer nr);
}
