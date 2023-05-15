package com.example.demo.repository.file;

import com.example.demo.domain.Entity;
import com.example.demo.domain.validators.Validator;
import com.example.demo.repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia
// propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    protected String fileName;

    public AbstractFileRepository(Validator<E> validator) {
        super(validator);
    }

    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    protected void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                E e = extractEntity(attr);
                super.save(e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //sau cu lambda - curs 4, sem 4 si 5
//        Path path = Paths.get(fileName);
//        try {
//            List<String> lines = Files.readAllLines(path);
//            lines.forEach(linie -> {
//                E entity=extractEntity(Arrays.asList(linie.split(";")));
//                super.save(entity);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    /**
     * extract entity  - template method design pattern
     * creates an entity of type E having a specified list of @code attributes
     *
     * @param attributes
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity) {
        E e = super.save(entity);
        if (e == null)
            writeToFile(entity);
        return e;
    }

    @Override
    public E delete(ID id) {
        if(id==null)
            throw new IllegalArgumentException("entity must be not null");
        E entity=entities.get(id);
        if (entity != null)
            removeFromDatabase(id);
        return entity;
    }

    private void removeFromDatabase(ID id) {
    }
   //protected void updateInDatabase (E entity) { }

    //protected void updateDatabase() { }
    protected void writeToFile(E entity) {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rewriteToFile() {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName))) {
            List<E> values = entities.values().stream().toList();
            int nr = values.size();
            for (int i = 0; i < nr; i++) {
                bW.write(createEntityAsString(values.get(i)));
               if(i<nr-1) bW.newLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E update(E entity) {
        E e = super.update(entity);
        rewriteToFile();
        return e;

    }

}