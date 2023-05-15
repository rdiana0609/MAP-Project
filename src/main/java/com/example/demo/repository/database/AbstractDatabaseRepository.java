package com.example.demo.repository.database;

import com.example.demo.domain.Entity;
import com.example.demo.domain.validators.ValidationException;
import com.example.demo.domain.validators.Validator;
import com.example.demo.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public abstract class AbstractDatabaseRepository<ID,E extends Entity<ID>>implements Repository<ID,E> {
    private final HashMap<ID,E> entities = null;
    protected String url;
    protected String username;
    protected String password;
    protected String tableName;
    private Validator<E> validator;

    public AbstractDatabaseRepository(Validator<E> validator) {
        this.validator = validator;
    }
    public AbstractDatabaseRepository(String url, String username, String password, Validator<E> validator, String tableName){
        this.validator=validator;
        this.url=url;
        this.username=username;
        this.password=password;
        this.tableName=tableName;
        loadData();
    }
    protected abstract void loadData();
    //protected abstract Iterable<E>findAll_DB();

    public E delete(ID id) {
        if(id==null)
            throw new IllegalArgumentException("entity must be not null");

        if(id != null)
        {   try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            String sql = "DELETE from " + tableName +
                    " where id = '" + id.toString() + "';";
            statement.executeUpdate(sql);
        }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        return  null;
    }
    @Override
    public E save(E entity) {
        if (entity==null)
            throw new IllegalArgumentException("entity must be not null");
        try{validator.validate(entity);}
        catch (ValidationException e) {
            throw new IllegalArgumentException("invalid entity\n");
        }
   return null;
    }


}