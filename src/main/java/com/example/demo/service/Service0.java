package com.example.demo.service;/*package service;

import domain.Entity;
import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import repository.Repository;

import java.util.*;

public class Service0 implements Service<UUID> {
    private Repository repo;
    private Repository repofr;
    public Service0(Repository repo,Repository repofr) {
        this.repo = repo;
        this.repofr=repofr;
    }

    /**
     * @param email a string that represents the user's email we have to find
     * @return the user that has that specific email
     * or null if there is no user with that email
     */

  /*  public Utilizator getUserByEmail(String email) {
        Iterable<Utilizator> it = repo.findAll();
        for (Utilizator u : it)
            if (u.getEmail().equals(email))
                return u;
        return null;
    }

    @Override
    public boolean addUser(Utilizator user) {
        Entity<UUID> u = null;
        try {
            if (user.getEmail() == null)
                throw new IllegalArgumentException("The email must be not null");
            else if (getUserByEmail(user.getEmail()) != null)
                throw new IllegalArgumentException("There is another user with this email");

            u = repo.save(user);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if (u != null) {
            System.err.println("Exista deja un user cu acest ID!");
            return false;
        }

        return true;
    }

    /**
     * The function removes an user from the userRepo by a given email
     * if there are anny exception we will show an error message
     * returns the user if we found one
     * and null otherwise
     * <p>
     * we also have to delete all the friendships that include this user in the friendshipRepo.
     */
 /*   @Override
    public Entity<UUID> deleteUser(String email) {
        Utilizator u = null;
        try {
            u = getUserByEmail(email);
            if (u == null) {
                System.err.println("Nu exista niciun user cu acest email");
                return null;
            }
            for(Utilizator prieten:u.getPrieteni())
            {
                for(Prietenie fr:(Iterable<Prietenie>) repofr.findAll())
                    if((fr.getPrieten1().equals(u)&&fr.getPrieten2().equals(prieten)) || (fr.getPrieten1().equals(prieten) && fr.getPrieten2().equals(u)))
                {repofr.delete(fr.getId());
                break;}
                prieten.unfriend(u);
            }
            u = (Utilizator) repo.delete(u.getId());
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

        if (u == null) {
            System.err.println("Nu exista utilizator cu acest email");
            return null;
        }
        return u;

    }

    /**
     * @return an Iterable of all the users
     */
  /*  @Override
    public Iterable<Utilizator> getAllUsers() {
        return repo.findAll();
    }


    @Override
    public void add_Predefined_Values() {


        Utilizator u1 = new Utilizator("MPopescu", "marian.popescu@yahoo.com");
        Utilizator u2 = new Utilizator("Kitty", "k123@yahoo.com");
        Utilizator u3 = new Utilizator("Stefan", "stefan69@yahoo.com");
        Utilizator u4 = new Utilizator("Stefi13", "stefi@gmail.com");
        Utilizator u5 = new Utilizator("Ana", "ana.maria@gmail.com");
        Utilizator u6 = new Utilizator("Nicu", "nicu.12@yahoo.com");
        Utilizator u7 = new Utilizator("Pop", "nicu.pop@yahoo.com");

        this.addUser(u1);
        this.addUser(u2);
        this.addUser(u3);
        this.addUser(u4);
        this.addUser(u5);
        this.addUser(u6);
        this.addUser(u7);

        this.adaugaPrietenie(u1.getEmail(), u2.getEmail());
        this.adaugaPrietenie(u1.getEmail(), u7.getEmail());

        this.adaugaPrietenie(u6.getEmail(), u5.getEmail());
        this.adaugaPrietenie(u5.getEmail(), u4.getEmail());
        this.adaugaPrietenie(u4.getEmail(), u3.getEmail());
    }
    @Override
    public boolean adaugaPrietenie(String email1,String email2){
        Entity<UUID> fr=null;
        Utilizator u1,u2;
        try{
            if(email1==null|| email2==null)
                throw new IllegalArgumentException("Emails must be not null");
            u1=getUserByEmail(email1);
            u2=getUserByEmail(email2);
            if(u1==null || u2==null||u1.equals(u2))
                throw new ValidationException("The emails can not be the same for both users");
            fr=repofr.save(new Prietenie(u1,u2));
        }
        catch(Exception e){
            System.err.println(e);
            return false;
        }
        if(fr!=null){
            System.err.println("The friendship relation already exists");
            return false;
        }
        u1.addFriend(u2);
        u2.addFriend(u1);
        return true;
    }

    @Override
    public Entity<UUID> stergePrietenie(String email1,String email2){
        Entity<UUID> f=null;
        Utilizator u1,u2;
        try {
            if (email1 == null || email2 == null)
                throw new IllegalArgumentException("Emails must be not null");
            u1 = getUserByEmail(email1);
            u2 = getUserByEmail(email2);
            if (u1 == null || u2 == null || u1.equals(u2))
                throw new ValidationException("There are two users with the same email");
            Iterable<Prietenie> p = repofr.findAll();
            for (Prietenie p1 : p)
                if (
                        (p1.getPrieten1().getId().equals(u1.getId()) && p1.getPrieten2().getId().equals(u2.getId()))
                                || (p1.getPrieten1().getId().equals(u2.getId()) && p1.getPrieten2().getId().equals(u1.getId()))
                ) {
                    f = repofr.delete(p1.getId());
                    break;
                }
        }
        catch(Exception e){
            System.err.println("There is no friendship relationship between these users ");
            return null;}
        u1.unfriend(u2);
        u2.unfriend(u1);
        return f;
        }
    /**
     * @return an Iterable of all the friendships
     */
  /*  @Override
    public Iterable<Prietenie> getAllFriendships() {
        return repofr.findAll();
    }
   @Override
    public int numberOfCommunities() {
        Iterable<Utilizator> it = repo.findAll();
        Set<Utilizator> set = new HashSet<>();
        int count = 0;

        for(Utilizator u : it)
            if(!set.contains(u))//nu a fost vizitat
            {
                ++count;
                DFS(u, set);

            }

        return count;
    }


    public int[][] matriceAd() {
        Iterable<Utilizator> ut = repo.findAll();
        List<Utilizator> list = new ArrayList<>();
        for (Utilizator u : ut)
            list.add(u);

        int[][] m = new int[list.size()][list.size()];
        int i = 0, j = 0;
        for (Utilizator u : ut)
        {       i++;
        for (Utilizator f : u.getPrieteni())
            j++;
        m[i][j] = 1;
    }
        return m;



    }

   /* void DFSUtil(int v, boolean[] visited)
        {
            // Mark the current node as visited and print it
            Iterable<Utilizator> ut = repo.findAll();
            List<Utilizator> list = new ArrayList<>();
            for (Utilizator u : ut)
                list.add(u);
            visited[v] = true;
            // Recur for all the vertices
            // adjacent to this vertex
            for (int x : list.get(v)) {
                if (!visited[x])
                    DFSUtil(x, visited);
            }
        }
        void connectedComponents()
        {
            // Mark all the vertices as not visited
            boolean[] visited = new boolean[V];
            for (int v = 0; v < V; ++v) {
                if (!visited[v]) {
                    // print all reachable vertices
                    // from v
                    DFSUtil(v, visited);

                }
            }
        }*/

  /*  private List<Utilizator> DFS(Utilizator u, Set<Utilizator> set) {
        List<Utilizator> list = new ArrayList<>();
        list.add(u);
        set.add(u);

        for(Utilizator f : u.getPrieteni())//lista de adiacenta
            if(!set.contains(f))//dc nu a fost vizitat
            {
                List<Utilizator> l = DFS(f, set);
                for(Utilizator x : l)
                    list.add(x);
            }

        return list;
    }

@Override

public List<List<Utilizator>> getAllCommunities() {
    Iterable<Utilizator> it = repo.findAll();
        Set<Utilizator> set = new HashSet<>();

        List<List<Utilizator>> l = new ArrayList<>();

        for(Utilizator u : it)
            if(!set.contains(u))
                l.add(DFS(u, set));

        return l;
    }


}
*/