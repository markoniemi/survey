package org.survey.model.user;

import java.util.ArrayList;
import java.util.List;

import org.survey.repository.EntityFactory;

public class UserFactory implements EntityFactory<User, Long> {

	public static User createUser1() {
		return new User("username", "password", "email", Role.ROLE_USER);
	}

	public static User createUser2() {
		return new User("user2", "password2", "email2", Role.ROLE_USER);
	}
	@Override
	public List<User> getEntities(int count) {
		List<User> entities = new ArrayList<User>();
		for (int i = 0; i < count; i++) {
			User user = new User("username" + i, "password" + i, "email" + i,
					Role.ROLE_USER);
            entities.add(user);
		}
		return entities;
	}

	@Override
	public User getUpdatedEntity(User entity) {
	    entity.setUsername(entity.getUsername());
	    entity.setPassword(entity.getPassword()
                + "_updated");
	    entity.setEmail(entity.getEmail() + "_updated");
		return entity;
	}

}
