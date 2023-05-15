package com.example.demo.repository.file;

import com.example.demo.domain.Prietenie;
import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.Validator;
import com.example.demo.repository.Repository;

import java.util.List;
import java.util.UUID;

public class PrietenieRepoFile extends AbstractFileRepository<UUID, Prietenie>{

    Repository<UUID, Utilizator> userFile;
    public PrietenieRepoFile(String fileName, Validator<Prietenie> validator, Repository<UUID, Utilizator>  userFile) {
        super(validator);
        this.userFile = userFile;
        this.fileName = fileName;
        super.loadData();
    }

    @Override
    public Prietenie extractEntity(List<String> attributes) {

        Utilizator u1=null;
        Utilizator u2=null;
        Prietenie entity=new Prietenie(u1,u2);

        Iterable<Utilizator> l = userFile.findAll();
       for (Utilizator u : l) {
           /* System.out.println(u);
            System.out.println(u.getUsername());
            System.out.println(attributes.get(0));
            System.out.println(u.getUsername().equals(attributes.get(0)));
            //System.out.println("idk");*/
           if(!attributes.get(0).equals(null)){
            if (u.getUsername().equals(attributes.get(0))) {
                //entity.setPrieten1(u);
                System.out.println("idk");
                //u1=userFile.findOne(u.getId());
                entity.setPrieten1(u);
                System.out.println(u);
                break;
            }}}
        for (Utilizator u : l) {
            if(!attributes.get(1).equals(null)){
            if (u.getUsername().equals(attributes.get(1))) {
                //entity.setPrieten2(u);
                //u2=userFile.findOne(u.getId());
                entity.setPrieten2(u);
                System.out.println(u);
                break;
            }}}



       Utilizator p1=entity.getPrieten2();
       Utilizator p2=entity.getPrieten1();
       // p1.addFriend(p2);
       // p2.addFriend(p1);
       System.out.println(entity);


        return entity;
    }
  @Override
    public Prietenie save(Prietenie entity) {
            if ((entity.getPrieten1() == entity.getPrieten2() && entity.getPrieten2() == entity.getPrieten1()) ||
                    entity.getPrieten1() == entity.getPrieten2() && entity.getPrieten2() == entity.getPrieten1())
                System.out.println("friendship already existent");
        return super.save(entity);
    }

    @Override
    protected String createEntityAsString(Prietenie entity) {
        return  entity.getPrieten1().getUsername() + ";" + entity.getPrieten2().getUsername() ;
    }


}