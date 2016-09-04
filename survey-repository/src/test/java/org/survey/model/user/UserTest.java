package org.survey.model.user;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void testToString() {
        User user = new User("username", "password", "email", Role.ROLE_USER);
        Assert.assertEquals("User(id=null, username=username, email=email, role=ROLE_USER)",
                user.toString());
    }

    @Test
    public void equals() {
        // equals method of user is based on username field
        User user1 = new User("username", "password", "email", Role.ROLE_USER);
        user1.setId(Long.valueOf(0));
        User user2 = new User("username", "password", "email", Role.ROLE_USER);
        user2.setId(Long.valueOf(0));
        Assert.assertTrue(user1.equals(user2));
        user2 = new User("username2", "password", "email", Role.ROLE_USER);
        user2.setId(Long.valueOf(1));
        Assert.assertFalse(user1.equals(user2));
        user2 = new User("username", "password2", "email2", Role.ROLE_USER);
        user2.setId(Long.valueOf(0));
        Assert.assertTrue(user1.equals(user2));
    }
}
