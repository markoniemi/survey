package org.survey.service.user;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.lang3.Validate;
import org.survey.model.file.File;
import org.survey.model.user.User;
import org.survey.repository.file.FileRepository;
import org.survey.repository.user.UserRepository;

import com.google.common.collect.Iterables;

import lombok.extern.log4j.Log4j2;

/**
 * WebService implementation of UserService. Can not use delegate design
 * pattern, since UserRepository uses overloaded methods, and apparently WS-I
 * profile does not support them. Even so, it might be better to reveal only
 * some of the UserRepository methods as WebService. Also, WebService does not
 * support lists without extensions. WebParam annotations are not necessary, but
 * adds proper names to parameters in WSDL. endpointInterface and serviceName
 * are probably unneccessary.
 */
@Log4j2
@WebService(endpointInterface = "org.survey.service.user.UserService", serviceName = "userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private FileRepository fileRepository;
    
    @Override
    public User[] findAll() {
        log.trace("findAll");
        return Iterables.toArray(userRepository.findAll(), User.class);
    }

    @Override
//    @Transactional(readOnly=false)
    public User create(User user) {
        Validate.notNull(user, "Tried to create null user.");
        Validate.notBlank(user.getUsername(), "Username must not be blank.");
        Validate.isTrue(userRepository.findByUsername(user.getUsername()) == null, "User already exists: %s",
                user.getUsername());
        log.trace("create: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User databaseUser = userRepository.findByUsername(user.getUsername());
        Validate.notNull(databaseUser, "User does not exist.");
        databaseUser.setEmail(user.getEmail());
        databaseUser.setPassword(user.getPassword());
        databaseUser.setRole(user.getRole());
        databaseUser.setUsername(user.getUsername());
        log.trace("update: {}", databaseUser);
        return userRepository.save(databaseUser);
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean exists(Long id) {
        return userRepository.findOne(id) != null;
    }

    @Override
    public void delete(Long id) {
        if (userRepository.exists(id)) {
            userRepository.delete(id);
        }
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
