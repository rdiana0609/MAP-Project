/*package repository.database;


import domain.Utilizator;
import domain.validators.UtilizatorValidator;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UtilizatorDB extends AbstractDatabaseRepository<UUID, Utilizator> {

    public UtilizatorDB(String url, String username, String password, UtilizatorValidator validator) {
        super(url, username, password, validator, "\"User\"");
        System.out.println(url);

        loadData();
    }

   /* @Override
    protected void loadData() {
        findAll().forEach( f -> super.save(f) );
    }

    @Override
    public Utilizator findOne(UUID id) {
        Utilizator user = null;System.out.println("user");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = '" + id.toString() + "';");
             ResultSet resultSet = statement.executeQuery())
        {
            System.out.println("user");
            resultSet.next();

            UUID id_ = UUID.fromString(resultSet.getString("id"));
            String username= resultSet.getString("username");
            String email = resultSet.getString("email");

            //user = new Utilizator(username, email);
            user.setId(id_);
            user.setEmail(email);
            user.setUsername(username);
             System.out.println("user");
            return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                Utilizator utilizator = new Utilizator(username, email);
                utilizator.setId(id);
                users.add(utilizator);
                super.save(utilizator);
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
       System.out.println(users);
        System.out.println("ok");
       return users;
    }

    @Override
    public Utilizator save(Utilizator entity) {
        Utilizator u = super.save(entity);
        if (u == null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO " + tableName + " "
                        + "VALUES ('" + entity.getId().toString() + "', '"
                        + entity.getUsername() + "', '" + entity.getEmail() + "');";
                statement.executeQuery(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return u;
    }
 @Override
   public Iterable<Utilizator> findAll() {
       Set<Utilizator> users = new HashSet<>();
       try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
            ResultSet resultSet = statement.executeQuery())
       {
           while (resultSet.next()) {
               UUID id = UUID.fromString(resultSet.getString("id"));
               String username = resultSet.getString("username");
               String email = resultSet.getString("email");

               Utilizator utilizator = new Utilizator(username, email);
               utilizator.setId(id);
               users.add(utilizator);
               super.save(utilizator);
           }
           return users;
       }
       catch (SQLException e) {
           e.printStackTrace();
       }
       System.out.println(users);
       System.out.println("ok");
       return users;
   }

    @Override
    public Utilizator update(Utilizator entity) {
        Utilizator user = super.update(entity);
        if(user.equals(entity))
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " set username = '" + entity.getUsername() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set email = '" + entity.getEmail() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return user;
    }
   @Override
   protected void loadData() {
       Set<Utilizator> users = new HashSet<>();
       try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
            ResultSet resultSet = statement.executeQuery())
       {
           while (resultSet.next()) {
               UUID id = UUID.fromString(resultSet.getString("id"));
               String username = resultSet.getString("username");
               String email = resultSet.getString("email");

               Utilizator utilizator = new Utilizator(username, email);
               utilizator.setId(id);

               entities.putIfAbsent(utilizator.getId(), utilizator);
           }
       } catch (SQLException e) {
           System.out.println(e);
       }
   }

    @Override
    protected void addToDatabase(Utilizator entity) {
        try {
            statement.executeUpdate("insert into users(id, username, email) " +
                    "values (" + entity.getId() + ", '" + entity.getUsername() +"','"+  entity.getEmail()+ "');");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void removeFromDatabase(UUID id) {
        try {
            statement.executeUpdate("delete from users where id = " + id.toString() + ";");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    protected void updateInDatabase(Utilizator entity) {
        try {
            statement.executeUpdate("update users " +
                    "set username = '" + entity.getUsername() + "set email = '"+ entity.getEmail()+
                    "where user_id = " + entity.getId() .toString()+ ";");
        }  catch (SQLException e) {
            System.out.println(e);
        }
    }

}*/
package com.example.demo.repository.database;

import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.UtilizatorValidator;
import com.example.demo.service.Service;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UtilizatorDB extends AbstractDatabaseRepository<UUID, Utilizator> {
    Service srv;

    public UtilizatorDB(String url, String username, String password, UtilizatorValidator validator) {
        super(url, username, password, validator, "\"User\"");
    }

    @Override
    protected void loadData() {
        this.findAll().forEach( f -> super.save(f) );
    }

    @Override
    public Utilizator findOne(UUID id) {
        //Utilizator user = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where id = '" + id.toString() + "';");
             ResultSet resultSet = statement.executeQuery()) {

            Utilizator user = null;
            while (resultSet.next()) {

                //UUID id_ = UUID.fromString(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                user = new Utilizator(username, email);
                user.setId(id);
                user.setEmail(email);
                user.setUsername(username);
            }
            // System.out.println(user);
            return user;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                Utilizator utilizator = new Utilizator(username, email);
                utilizator.setId(id);
                users.add(utilizator);
                super.save(utilizator);
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Utilizator save(Utilizator entity) {
        Iterable<Utilizator> ut=findAll();
       /* for (Utilizator user :ut)
        {if(user.getUsername().equals(entity.getUsername()) && user.getEmail().equals(entity.getEmail()))
        throw new IllegalArgumentException("Nu se pot adauga 2 utilizatori cu acelasi email");}*/
        Utilizator u = super.save(entity);
        if (u == null)
        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "INSERT INTO " + tableName + " "
                        + "VALUES ('" + entity.getId().toString() + "', '"
                        + entity.getUsername() + "', '" + entity.getEmail() + "');";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return u;
    }
 /*   @Override
    public Iterable<Utilizator> findAll() {
        Set<Utilizator> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + ";");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                UUID id = UUID.fromString(resultSet.getString("id"));
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");

                Utilizator utilizator = new Utilizator(username, email);
                utilizator.setId(id);
                users.add(utilizator);
                super.save(utilizator);
            }
            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(users);
        System.out.println("ok");
        return users;
    }*/

    @Override
    public Utilizator update(Utilizator entity) {


        {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                String sql = "UPDATE " + tableName + " set username = '" + entity.getUsername() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
                sql = "UPDATE " + tableName + " set email = '" + entity.getEmail() + "' " + " where id = '" + entity.getId().toString() + "';";
                statement.executeUpdate(sql);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return entity;
    }
}