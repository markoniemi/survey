package org.survey.model.user;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.survey.model.user.Role;
import org.survey.model.user.User;

public class UserComparatorTest {
    @Test
    public void compareTo() {
        UserComparator userComparator = new UserComparator();
        User user1 = UserFactory.createUser1();
        User user2 = UserFactory.createUser2();
        Assert.assertTrue(userComparator.compare(user1, user2) > 0);
    }

    @Test
    public void compareToWithNull() {
        UserComparator userComparator = new UserComparator();
        User user1 = UserFactory.createUser1();
        Assert.assertTrue(userComparator.compare(user1, null) > 0);
    }

    @Ignore("all fields are now @NonNull")
    @Test
    public void compareToWithNullField() {
        UserComparator userComparator = new UserComparator();
        User user1 = UserFactory.createUser1();
        User user2 = new User("username", "password", null, Role.ROLE_USER);
        Assert.assertTrue(userComparator.compare(user1, user2) > 0);
    }
}
