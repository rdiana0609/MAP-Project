package com.example.demo.repository.database;

import com.example.demo.domain.FriendRequest;
import com.example.demo.domain.Prietenie;
import com.example.demo.domain.RequestStatus;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.FriendshipRequestValidator;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FriendshipRequestDB extends AbstractDatabaseRepository<UUID,FriendRequest>{
    UtilizatorDB userRepo;
    public FriendshipRequestDB(String url, String user, String password, FriendshipRequestValidator validator, UtilizatorDB userRepository) {
        super(validator);
        this.url=url;
     this.username=user;
     this.password=password;
     this.tableName="\"FriendshipRequests\"";

        this.userRepo = userRepository;
        //loadData();
    }

    @Override
    public FriendRequest findOne(UUID id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where invite_id = '" + id.toString() + "';");
             ResultSet resultSet = statement.executeQuery()) {

            FriendRequest invite = null;
            while (resultSet.next()) {
                UUID id_ = UUID.fromString(resultSet.getString("invite_id"));
                System.out.println(id);
                /*Utilizator from = userRepo.findOne(UUID.fromString(resultSet.getString("from_id")));
                System.out.println(from);
                Utilizator to =userRepo.findOne(UUID.fromString("to_id"));*/
                UUID id1 = UUID.fromString(resultSet.getString("from_id"));
                UUID id2 = UUID.fromString(resultSet.getString("to_id"));
                Utilizator u1 = (Utilizator) userRepo.findOne(id1);

                Utilizator u2 = (Utilizator) userRepo.findOne(id2);
                RequestStatus status = FriendRequest.stringToStatus(resultSet.getString("status"));
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                invite = new FriendRequest(u1, u2, status, date);
                invite.setId(id_);
                //requests.add(invite);
                super.save(invite);

            }
            return invite;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        Set<FriendRequest> requests =new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {

                UUID id = UUID.fromString(resultSet.getString("invite_id"));
                System.out.println(id);
                /*Utilizator from = userRepo.findOne(UUID.fromString(resultSet.getString("from_id")));
                System.out.println(from);
                Utilizator to =userRepo.findOne(UUID.fromString("to_id"));*/
                UUID id1 = UUID.fromString(resultSet.getString("from_id"));
                UUID id2 = UUID.fromString(resultSet.getString("to_id"));
                Utilizator u1= (Utilizator) userRepo.findOne(id1);

                Utilizator u2 = (Utilizator) userRepo.findOne(id2);
                RequestStatus status = FriendRequest.stringToStatus(resultSet.getString("status"));
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                FriendRequest invite = new FriendRequest(u1, u2, status, date);
                invite.setId(id);
                requests.add(invite);
                super.save(invite);

            }
            return requests ;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }

    @Override
    public FriendRequest update(FriendRequest entity) {
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
               // String sql = "UPDATE " + tableName + " set from_id = '" + entity.getFromId() + "' " + " where invite_id = '" + entity.getId().toString() + "';";
              //  statement.executeUpdate(sql);
               // sql = "UPDATE " + tableName + " set to_id = '" + entity.getToId() + "' " + " where id = '" + entity.getId().toString() + "';";
               // statement.executeUpdate(sql);
                String sql = "UPDATE " + tableName + " set status = '" + entity.getStatus() + "' " + " where invite_id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return entity;

    }

    @Override
    protected void loadData() {
        this.findAll().forEach( f -> super.save(f) );
    }

    @Override
    public FriendRequest save(FriendRequest entity){
        FriendRequest f=super.save(entity);
        if (f == null)
        {    try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + tableName
                    + "VALUES ('" + entity.getId().toString() + "', '" +entity.getFrom().getId().toString()  +"', '" + entity.getTo().getId().toString() + "', '"
                    +  entity.getDate().toLocalDate().toString()+ "', '" +entity.getStatus().toString()+"');";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        }
        return f;



    }
}
