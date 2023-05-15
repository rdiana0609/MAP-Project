package com.example.demo.repository.database;

import com.example.demo.domain.*;
import com.example.demo.domain.validators.MessageValidator;
import com.example.demo.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDB extends AbstractDatabaseRepository<UUID,Message> {
    protected UtilizatorDB userRepository;

    public MessageDB(String url, String user, String password, MessageValidator validator, UtilizatorDB userRepository) {
        super(validator);
        this.url=url;
        this.username=user;
        this.password=password;
        this.userRepository = userRepository;
        this.tableName="\"Messages\"";
        loadData();
    }
    @Override
    public Message findOne(UUID id) {

        Message message;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName + " where msg_id = '" + id.toString() + "';");
             ResultSet resultSet = statement.executeQuery()) {

            message = null;
            while (resultSet.next()) {
                UUID id_ = UUID.fromString(resultSet.getString("mesg_id"));
                UUID from_id = UUID.fromString(resultSet.getString("from_id"));
                UUID to_id = UUID.fromString(resultSet.getString("to_id"));
                Utilizator from = userRepository.findOne(from_id);
                Utilizator to = userRepository.findOne(to_id);
                String text = resultSet.getString("text");
                String subject = resultSet.getString("subject");
                LocalDateTime date = resultSet.getTimestamp("sentAt").toLocalDateTime();
                message = new Message(date, subject, text, from, to);
                message.setId(id_);

                super.save(message);
            }


        return message;
    }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> msgs =new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from " + tableName +";");
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                UUID id_ = UUID.fromString(resultSet.getString("msg_id"));
                UUID from_id = UUID.fromString(resultSet.getString("from_id"));
                UUID to_id = UUID.fromString(resultSet.getString("to_id"));
                Utilizator from = userRepository.findOne(from_id);
                Utilizator to = userRepository.findOne(to_id);
                String text = resultSet.getString("text");
                String subject=resultSet.getString("subject");
                LocalDateTime date = resultSet.getTimestamp("sentAt").toLocalDateTime();
                Message message = new Message(date,subject,text,from, to);
                message.setId(id_);
                msgs.add(message);
                super.save(message);}
            return msgs;}
         catch (SQLException e) {
            e.printStackTrace();
        }
        return msgs;
    }


    @Override
    protected void loadData() {
        this.findAll().forEach( f -> super.save(f) );
    }

    @Override
    public Message save(Message entity){
       Message f=super.save(entity);
        if (f == null)
        {    try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + tableName+
            "VALUES ('" + entity.getSender().getId().toString() + "', '" +entity.getReceiver().getId().toString() +"', '" + entity.getSubject() + "', '"
                    +  entity.getText()+ "', '" +entity.getSentAt().toLocalDate()+"', '"+ entity.getId().toString()+"');";
            statement.executeUpdate(sql);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        }
        return f;



    }

    @Override
    public Message update(Message entity) {
        {String sql = "UPDATE messages SET sent_at = to_timestamp(?, ?)::timestamp, subject = ?, text = ?, sender = ?, receiver = ? WHERE messages.id = ?::int";
            try(Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, entity.getSentAt().toLocalDate().toString());
                    statement.setString(3, entity.getSubject());
                    statement.setString(4, entity.getText());
                    statement.setString(5, entity.getSender().getId().toString());
                    statement.setString(6, entity.getReceiver().getId().toString());
                    statement.setString(7, String.valueOf(entity.getId()));
                    statement.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }

        return entity;

    }
}
