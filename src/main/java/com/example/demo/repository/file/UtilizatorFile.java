package com.example.demo.repository.file;

import com.example.demo.domain.Utilizator;
import com.example.demo.domain.validators.Validator;

import java.util.List;
import java.util.UUID;

;

public class UtilizatorFile extends AbstractFileRepository<UUID, Utilizator> {

    public UtilizatorFile(String fileName, Validator<Utilizator> validator) {
        super(fileName, validator);
    }

  /*@Override
    protected void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                Utilizator entity = extractEntity(Arrays.asList(line.split(";")));
                super.save(entity);
            });}
         catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public Utilizator extractEntity(List<String> attributes) {
        Utilizator user = new Utilizator(attributes.get(0), attributes.get(1));
        //user.setId(UUID.fromString(attributes.get(0)));

        return user;
    }

    @Override
    protected String createEntityAsString(Utilizator entity) {
        return entity.getUsername() + ";"+ entity.getEmail();
    }



    @Override
    public Utilizator update(Utilizator u){
        Utilizator user=super.update(u);
        rewriteToFile();
        return user;

    }


}