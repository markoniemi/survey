package org.survey.user.repository;

import org.survey.repository.CrudRepositoryStub;
import org.survey.user.model.User;

/**
 * UserRepository stub for testing purposes.
 */
public class UserRepositoryStub extends CrudRepositoryStub<User, Long>
        implements UserRepository {
    @Override
    public User findByEmail(String email) {
        for (User user : entities) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : entities) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
