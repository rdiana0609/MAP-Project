package com.example.demo.domain.validators;

import com.example.demo.domain.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        boolean okTo = true;
        if (entity.getSender().equals(entity.getReceiver()) ||
                entity.getText().isEmpty())
            throw new ValidationException("invalid message data");
    }
}