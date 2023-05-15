package com.example.demo.domain.validators;

import com.example.demo.domain.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator>{
    @Override
    public void validate(Utilizator entity) throws ValidationException {
        validateUsername(entity.getUsername());
        validateEmail(entity.getEmail());
    }


    /**
     * It must not be null
     * the first name must be less than 100 characters
     * it mustn't be empty
     * it's first character must be a letter
     */
    private void validateUsername(String username) throws ValidationException {
        if(username == null)
            throw new ValidationException("Username can not be null!");
        else if(username.length() >= 100)
            throw new ValidationException("Username too long");
        else if(username.isEmpty())
            throw new ValidationException("Username can not be empty");
        else if(! Character.isAlphabetic(username.charAt(0)))
            throw new ValidationException("First letter of the username must be a letter");
    }


    /**
     * It must not be null
     * the first name must be less than 100 characters
     * it mustn't be empty
     * it must contain one and only one @ character
     * --- The email must also be unique, for that we will check in the service
     */
    private void validateEmail(String email) throws ValidationException {
        if(email == null)
            throw new ValidationException("First Name must not be null!");
        else if(email.length() >= 100)
            throw new ValidationException("First Name too long");
        else if(email.isEmpty())
            throw new ValidationException("First Name must not be empty");
        else if(email.chars().filter(ch -> ch == '@').count() != 1)
            throw new ValidationException("Wrong email");
    }

}
