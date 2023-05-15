package com.example.demo.service;

import com.example.demo.MessagesController;
import com.example.demo.domain.*;
import com.example.demo.domain.validators.FriendshipRequestValidator;
import com.example.demo.domain.validators.ValidationException;
import com.example.demo.repository.Repository;
import com.example.demo.repository.database.FriendshipRequestDB;
import com.example.demo.repository.database.MessageDB;
import com.example.demo.repository.database.PrietenieDB;
import com.example.demo.repository.database.UtilizatorDB;

import com.example.demo.utils.events.observer.Subject;
import javafx.collections.ObservableList;

import java.io.ObjectInputFilter;
import java.time.LocalDateTime;
import java.util.*;

public class Service1 extends Subject {
    private UtilizatorDB repo;
    private PrietenieDB repofr;
    private FriendshipRequestDB repoinvite;
    private MessageDB msgrepo;
    public Service1(UtilizatorDB repo,PrietenieDB repofr,FriendshipRequestDB repoinvite,MessageDB msgrepo) {
        this.repo = repo;
        this.repofr=repofr;
        this.repoinvite=repoinvite;
        this.msgrepo=msgrepo;
    }
    public Service1(UtilizatorDB repo,PrietenieDB repofr) {
        this.repo = repo;
        this.repofr=repofr;
    }

    /**
     * @param email a string that represents the user's email we have to find
     * @return the user that has that specific email
     * or null if there is no user with that email
     */

    public Utilizator getUserByEmail(String email) {
        Iterable<Utilizator> it = repo.findAll();
        for (Utilizator u : it)
            if (u.getEmail().equals(email))
                return u;
        return null;
    }
    public Utilizator getUserByUsername(String email) {
        Iterable<Utilizator> it = repo.findAll();
        for (Utilizator u : it)
            if (u.getUsername().equals(email))
                return u;
        return null;
    }
    public Utilizator getUserByID(UUID id) {
        Iterable<Utilizator> it = repo.findAll();
        for (Utilizator u : it)
            if (u.getId().toString().equals(id.toString()))
                return u;
        return null;
    }

    //@Override
    public boolean addUser(Utilizator user) {
        Entity<UUID> u = null;
        try {
            if (user.getEmail()==null || user.getUsername()==null ||user.getEmail().equals("") || user.getUsername().equals(""))
                throw new ValidationException("The email must be not null");
            else if (getUserByEmail(user.getEmail()) != null)
                throw new ValidationException("There is another user with this email");

            u = repo.save(user);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if (u != null) {
            throw new ValidationException("Exista deja un user cu acest ID!");
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
    //@Override
    public void deleteUser(String email) {
        Utilizator u = null;
        try {
            u = getUserByEmail(email);
            System.out.println(u);
            if (u == null) {
                System.err.println("Nu exista niciun user cu acest email");
                //return null;
            }
            Iterable<Prietenie> friends=repofr.findAll();
            for(Prietenie fr:friends )
            {if(fr.getPrieten1().equals(u) || (fr.getPrieten2().equals(u) ))
            {repofr.delete(fr.getId());
                break;}}
            // prieten.unfriend(u);

            u = (Utilizator) repo.delete(u.getId());
        } catch (Exception e) {
            System.err.println(e);
            // return null;
        }


        // return getUserByEmail(email);

    }

    /**
     * @return an Iterable of all the users
     */
    //@Override
    public Iterable<Utilizator> getAllUsers() {
        return repo.findAll();
    }


    //@Override
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
    //@Override
    public boolean adaugaPrietenie(String email1,String email2){
       /* Entity<UUID> fr=null;
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
        return true;*/
        Entity<UUID> f = null;
        Utilizator u1, u2;
        try{
            if(email1 == null || email2 == null)
                throw new IllegalArgumentException("Emails must not be null!");

            u1 = getUserByEmail(email1);
            u2 = getUserByEmail(email2);
            if(u1 == null || u2 == null || u1.equals(u2))
                throw new ValidationException("There are no two users with these two emails!");

            f = repofr.save(new Prietenie(u1, u2));
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }

        if(f != null) {
            System.err.println("These two users are already friends!");
            return false;
        }

        //u1.addFriend(u2);
        //u2.addFriend(u1);
        return true;
    }

    //@Override
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
        // u1.unfriend(u2);
        // u2.unfriend(u1);
        return f;
    }
    /**
     * @return an Iterable of all the friendships
     */
    private List<String> getFriendsUUID(Utilizator user){
        List prieteniUUID= new ArrayList<String>();
        List<Utilizator> listapr=null;
        Iterable<Utilizator> listaprieteni;
        for(Prietenie pr:(Iterable<Prietenie>)repofr.findAll())
        {if(pr.getPrieten1().equals(user) )
            listapr.add(pr.getPrieten1());
            if(pr.getPrieten2().equals(user))
                listapr.add(pr.getPrieten1());
        }
        for(Utilizator u:listapr){prieteniUUID.add(u.getId().toString());}
        return prieteniUUID;
    }
    //@Override
    public Iterable<Prietenie> getAllFriendships() {
        return repofr.findAll();
    }
    //@Override
    public int numberOfCommunities() {
        Map<String, HashSet<String>> adjMap = new HashMap<>();

        for (Utilizator user : getAllUsers()) {
            for (String friendId : getFriendsUUID(user)) {
                String userId = user.getId().toString();
                adjMap.putIfAbsent(userId, new HashSet<>());
                adjMap.putIfAbsent(friendId, new HashSet<>());
                adjMap.get(userId).add(friendId);
                adjMap.get(userId).add(userId);
                adjMap.get(friendId).add(userId);
                adjMap.get(friendId).add(friendId);
            }
        }
        Graf graph = new Graf(adjMap);
        return graph.getConnectedComponentsCount();
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

    private List<Utilizator> DFS(Utilizator u, Set<Utilizator> set) {
        List<Utilizator> list = new ArrayList<>();
        list.add(u);
        set.add(u);
        List<Utilizator> listapr=null;
        Iterable<Utilizator> listaprieteni;
        for(Prietenie pr:(Iterable<Prietenie>)repofr.findAll())
        {if(pr.getPrieten1().equals(u) )
            listapr.add(pr.getPrieten1());
            if(pr.getPrieten2().equals(u))
                listapr.add(pr.getPrieten1());
        }
        for(Utilizator f : listapr)//lista de adiacenta
            if(!set.contains(f))//dc nu a fost vizitat
            {
                List<Utilizator> l = DFS(f, set);
                for(Utilizator x : l)
                    list.add(x);
            }

        return list;
    }
    //@Override
    public List<List<Utilizator>> getAllCommunities() {
        Iterable<Utilizator> it = repo.findAll();
        Set<Utilizator> set = new HashSet<>();

        List<List<Utilizator>> l = new ArrayList<>();

        for(Utilizator u : it)
            if(!set.contains(u))
                l.add(DFS(u, set));

        return l;
    }

    /**
     * Gets the number of communities with more than one user
     * @return the number of communities
     */

    /**
     * Gets the community with most users
     * @return the users of the most sociable community
     */
    public Iterable<Utilizator> getMostSociableCommunity() {
        Map<String, HashSet<String>> adjMap = new HashMap<>();
        for (Utilizator user : getAllUsers()) {
            for (String friendId : getFriendsUUID(user)) {
                String userId = user.getId().toString();
                adjMap.putIfAbsent(userId, new HashSet<>());
                adjMap.putIfAbsent(friendId, new HashSet<>());
                adjMap.get(userId).add(friendId);
                adjMap.get(userId).add(userId);
                adjMap.get(friendId).add(userId);
                adjMap.get(friendId).add(friendId);
            }
        }
        Graf graph = new Graf(adjMap);
        Collection<Utilizator> mostSociableNetwork = new ArrayList<>();
        Iterable<String> connectedComponent = graph.getConnectedComponentWithLongestRoad();
        for (String vertex : connectedComponent) {
            mostSociableNetwork.add((Utilizator) repo.findOne(UUID.fromString(vertex)));
        }
        return mostSociableNetwork;
    }

    /**
     * Gets all the friends of a user and the dates of the friendships
     * @param email the email of the user
     * @return the friends and friendship dates of the user
     */
    //@Override
    public void updateUtilizator(String email,String newvalues,Integer nr){
        Utilizator u=getUserByEmail(email);
        System.out.println(u);
        Utilizator user=getUserByEmail(email);
        if(user==null) System.out.println("nu exista utilizator");
        if(nr==1){user.setUsername(newvalues);}
        if(nr==2){user.setEmail(newvalues);}
        System.out.println(user);
        repo.update(user);
        if(getAllFriendships()!=null){
            Iterable<Prietenie> frlist=getAllFriendships();
            for(Prietenie pr:frlist){
                if(pr.getPrieten1().equals(u)) {pr.setPrieten1(user);repofr.update(pr);}
                if(pr.getPrieten2().equals(u)) {pr.setPrieten2(user);repofr.update(pr);}

            }
        }

    }
    public List<Utilizator> getFriends(Utilizator owner){
        List<Utilizator> lista = new ArrayList<>();
        var set=getAllFriendships();
        for(Prietenie f:set) {
            if (f.getPrieten1().equals(owner)) {

                lista.add(f.getPrieten2());
            }
            else{
                if (f.getPrieten2().equals(owner)) {

                    lista.add(f.getPrieten1());
                }}
        }
        return lista;

    }

    public Iterable<FriendRequest> getAllInvites() {
        return repoinvite.findAll();
    }
    /**
     * Adds a new friendship invite to the repo
     * @param username1 the id of the user inviting
     * @param username2 the id of the user being invited
     * @return the newly created invite if the operation was successful
     */

    public FriendRequest sendInvite(UUID username1, UUID username2) {
        Utilizator from = (Utilizator) repo.findOne(username1);
        Utilizator to = (Utilizator) repo.findOne(username2);
        if (from == null ||
                to == null ||
                from.equals(to))
            throw new ValidationException("invalid users");
        if (getFriends(to).contains(from))
            throw new IllegalArgumentException("users already friends");
        for (FriendRequest invite : repoinvite.findAll())
            if (((invite.getFrom() == from && invite.getTo() == to) ||
                    (invite.getFrom() == to && invite.getTo() == from)) &&
                    invite.getStatus() == RequestStatus.PENDING)
                throw new IllegalArgumentException("users already trying to connect");

        FriendRequest invite = new FriendRequest(from, to);
        repoinvite.save(invite);
        //FriendshipRequestValidator.validate(invite);
        //notifyObservers();
        return invite;
    }

    /**
     * Gets the invites implicating a user
     * @param userId the id of the user taking part in the invite
     * @return the list of invites
     */
    public List<FriendRequest> getInvites(UUID userId) {
        Utilizator user = (Utilizator) repo.findOne(userId);
        List<FriendRequest> invites = new ArrayList<>();
        for (FriendRequest invite : repoinvite.findAll())
            if (invite.getFrom() == user || invite.getTo() == user)
                invites.add(invite);
        return invites;
    }
    public List<Utilizator> getInvitesFrom(UUID userId) {
        Utilizator user = (Utilizator) repo.findOne(userId);
        List<Utilizator> invites = new ArrayList<>();
        for (FriendRequest invite : repoinvite.findAll())
        {System.out.println(invite);
            if (invite.getTo().equals(user) && invite.getStatus().equals(RequestStatus.PENDING))
                invites.add(invite.getFrom());}
        System.out.println("Invitatii de la"+invites);
        return invites;
    }
    public List<Utilizator> getRequests(UUID userId) {
        Utilizator user = (Utilizator) repo.findOne(userId);
        List<Utilizator> invites = new ArrayList<>();
        for (FriendRequest invite : repoinvite.findAll())
        {System.out.println(invite);
            if (invite.getFrom().equals(user) && invite.getStatus().equals(RequestStatus.PENDING))
                invites.add(invite.getTo());}
        System.out.println("Invitatie catre"+invites);
        return invites;
    }

    /**
     * Accepts an invite and adds a friendship between the two users of the invite
     *
     * @param userId   the id of the user being invited
     * @param user2id the id of the invite being accepted
     * @return the newly created friendship after accepting the invite if the operation was successful
     */
    public void acceptInvite(UUID userId, UUID user2id) {
        Utilizator user = (Utilizator) repo.findOne(userId);
        Utilizator user2 = (Utilizator) repo.findOne(user2id);

        for (FriendRequest invite : repoinvite.findAll())
        {if (invite.getTo().getId().equals(userId) && invite.getFrom().getId().equals(user2id)) {
            if (repo.findOne(user.getId()) == null)
                throw new IllegalArgumentException("nonexistent user");
            if (repoinvite.findOne(invite.getId()) == null ||
                    invite.getFrom().getId().equals(userId) ||
                    !(invite.getTo().getId().equals(userId)) ||
                    !(invite.getStatus().equals(RequestStatus.PENDING)))
                throw new ValidationException("invalid invite");

            invite.setStatus(RequestStatus.APPROVED);
            repoinvite.update(invite);
            System.out.println("<<<<<<"+invite+">>>>>>");
            adaugaPrietenie(invite.getFrom().getEmail(), invite.getTo().getEmail());
        }}
        //notifyObservers();
        //return adaugaPrietenie(invite.getFrom().getEmail(), invite.getTo().getEmail());
    }

    /**
     * Rejects a invite
     * @param userId the id of the user rejecting the invite
     * @param inviteId the id of the invite being rejected
     * @return the invite after being rejected if the operation was successful
     */
    public FriendRequest rejectInvite(UUID userId, UUID inviteId) {
        Utilizator user = (Utilizator) repo.findOne(userId);
        FriendRequest invite = repoinvite.findOne(inviteId);
        if (repo.findOne(user.getId()) == null)
            throw new IllegalArgumentException("nonexistent user");
        if (repoinvite.findOne(invite.getId()) == null ||
                (invite.getFrom() != user &&
                        invite.getTo() != user) ||
                invite.getStatus() != RequestStatus.PENDING)
            throw new ValidationException("invalid invite");

        invite.setStatus(RequestStatus.REJECTED);
        repoinvite.update(invite);

        //notifyObservers();
        return invite;
    }
    public void addMessage(Message message) {
        if (message.getReceiver() == null ||
                message.getSender() == null ||
                message.getReceiver().equals(message.getSender()) || (message.getText().equals("")))
            throw new ValidationException("invalid message");
        msgrepo.save(message);
        notifyObservers();

    }


    public List<Message> getAllMessagesForSomeone(UUID id) {
        List<Message> msgsrecived= new ArrayList<>();
        Utilizator user=repo.findOne(id);
        for(Message m: msgrepo.findAll())
        {
            if(m.getReceiver().equals(user)){msgsrecived.add(m);}
        }
        return msgsrecived;
    }
    public List<Message> getAllMessagesForSomeone2(UUID id,UUID id2) {
        List<Message> msgsrecived= new ArrayList<>();
        Utilizator user=repo.findOne(id);
        Utilizator user2=repo.findOne(id2);
        for(Message m: msgrepo.findAll())
        {
            if(m.getReceiver().equals(user) && m.getSender().equals(user2)){msgsrecived.add(m);}
        }
        return msgsrecived;
    }
    /*public List<Message> getAllMessagesFor2Users(Utilizator u1,Utilizator u2)
    {
        var mesages=getAllMessagesForSomeone(u1);
        var messages2=getAllMessagesForSomeone(u2);
        List<Message> lista=new ArrayList<>();

    }*/
    public void deleteMsg(UUID msgid){
        msgrepo.delete(msgid);
    }
}



