
package com.example.demo.domain.validators;

import com.example.demo.domain.FriendRequest;

public class FriendshipRequestValidator implements Validator<FriendRequest> {

        @Override
        public void validate(FriendRequest entity) throws ValidationException {
            if (entity.getFrom() == null || entity.getTo() == null)
                throw new ValidationException("invalid invite data");
        }
}

