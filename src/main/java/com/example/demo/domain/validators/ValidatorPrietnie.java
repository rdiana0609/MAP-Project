package com.example.demo.domain.validators;

import com.example.demo.domain.Prietenie;


public class ValidatorPrietnie implements Validator<Prietenie> {

    /**
     * FriendShip's user must have different ids
     */
    @Override
    public void validate(Prietenie entity) throws ValidationException {
        if(entity.getPrieten1().getId() == entity.getPrieten2().getId())
            throw new ValidationException("You cannot add yourself as a friend ");
    }
}
