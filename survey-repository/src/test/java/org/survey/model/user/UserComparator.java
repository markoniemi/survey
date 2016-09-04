package org.survey.model.user;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.survey.repository.EntityComparator;

public class UserComparator extends EntityComparator<User, Long> {
    @Override
    public int compare(User user1, User user2) {
        if (user1 == user2) {
            return 0;
        }
        if (user1 == null) {
            return -1;
        }
        if (user2 == null) {
            return 1;
        }
        return new CompareToBuilder()
                .append(user1.getUsername(), user2.getUsername())
                .append(user1.getPassword(), user2.getPassword())
                .append(user1.getEmail(), user2.getEmail())
                .append(user1.getRole(), user2.getRole()).toComparison();
    }
}
