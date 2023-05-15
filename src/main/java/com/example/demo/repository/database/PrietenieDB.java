package com.example.demo.repository.database;

import com.example.demo.domain.Prietenie;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.ValidatorPrietnie;
import com.example.demo.repository.Repository;
import com.example.demo.service.Service1;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PrietenieDB extends AbstractDatabaseRepository<UUID, Prietenie> {

    UtilizatorDB userRepo;

    private Repository PrietenieDB;

    public PrietenieDB(String url, String username, String password, ValidatorPrietnie validator,UtilizatorDB userRepo) {
        super(validator);
        this.url = url;
        this.username = username;
        this.password = password;
        this.tableName = "\"Friendship\"";
        this.userRepo = userRepo;
        //loadData();
    }

    @Override
    protected void loadData() {
        findAll().forEach( f -> super.save(f) );
    }

    @Override
    public Prietenie findOne(UUID id) {
       Prietenie friendship = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = " + id.toString() + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            resultSet.next();

            UUID fid = UUID.fromString(resultSet.getString("id"));
            UUID e1 = UUID.fromString(resultSet.getString("id1"));
            UUID e2 = UUID.fromString(resultSet.getString("id2"));
            LocalDateTime from = LocalDateTime.parse(resultSet.getString("from"));

            Utilizator u1 = userRepo.findOne(e1);
            Utilizator u2 = userRepo.findOne(e2);
            friendship = new Prietenie(u1, u2,from);
            friendship.setId(fid);

            return friendship;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return friendship;
    }


    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> friendShips = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                UUID fid = UUID.fromString(resultSet.getString("id"));
                UUID id1 = UUID.fromString(resultSet.getString("id1"));
                UUID id2 = UUID.fromString(resultSet.getString("id2"));
                LocalDateTime from = LocalDateTime.parse(resultSet.getString("from"));


                Utilizator u1= (Utilizator) userRepo.findOne(id1);

                Utilizator u2 = (Utilizator) userRepo.findOne(id2);
               // System.out.println(u2);
                //System.out.println(u1);
               // u1.addFriend(u2);
              // u2.addFriend(u1);

                Prietenie friendship = new Prietenie(fid,u1, u2,from);
                friendship.setId(fid);

               friendShips.add(friendship);
            }
            return friendShips;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return friendShips;
    }

    @Override
    public Prietenie save(Prietenie entity) {
        var allFriendships = findAll();
        for (Prietenie friendship1 : allFriendships)
        { if ((entity.getPrieten1().getEmail().equals(friendship1.getPrieten1().getEmail())&& entity.getPrieten2().getEmail().equals(friendship1.getPrieten2().getEmail())) ||
                (entity.getPrieten1().getEmail().equals(friendship1.getPrieten2().getEmail()) && entity.getPrieten2().getEmail().equals(friendship1.getPrieten1().getEmail()
                )) ){
            throw new IllegalArgumentException("entity already exists\n");
        }
        }
        Prietenie f=super.save(entity);
        if (f == null)
        {    try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + tableName + " "
                    + "VALUES ('"+entity.getId().toString()+ "', '" +entity.getPrieten1().getId().toString()  +"', '" + entity.getPrieten2().getId().toString() + "', '"
                    +  entity.getFriendsFrom().toString() +"');";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        }
            return f;



    }

    @Override
    public Prietenie update(Prietenie entity) {


        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " set id1 = '" + entity.getPrieten1().getId().toString() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set id2 = '" + entity.getPrieten2().getId().toString() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return entity;
    }
}