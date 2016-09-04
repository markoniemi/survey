package org.survey.repository.user;

import org.survey.model.user.User;
import org.survey.repository.CrudRepositoryStub;

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
