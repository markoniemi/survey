package org.survey.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.survey.model.file.File;
import org.survey.model.user.User;
import org.survey.repository.file.FileRepository;
import org.survey.repository.user.UserRepository;

import com.google.common.collect.Iterables;

import lombok.extern.slf4j.Slf4j;

/**
 * WebService implementation of UserService. Can not use delegate design
 * pattern, since UserRepository uses overloaded methods, and apparently WS-I
 * profile does not support them. Even so, it might be better to reveal only
 * some of the UserRepository methods as WebService. Also, WebService does not
 * support lists without extensions. WebParam annotations are not necessary, but
 * adds proper names to parameters in WSDL. endpointInterface and serviceName
 * are probably unneccessary.
 */
//@Service
@Slf4j
//@WebService(endpointInterface = "org.survey.service.user.UserService", serviceName = "userService")
public class UserServiceImpl implements UserService {
    @Resource
//    @Autowired
    private UserRepository userRepository;
    @Resource
//    @Autowired
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
    // TODO rename to findByUsername
    public User findOne(String username) {
        log.trace("findByUsername: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        log.trace("findByEmail: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean exists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public void delete(String username) {
        log.trace("delete: {}", username);
        User user = userRepository.findByUsername(username);
        // Validate.notNull(user, "User does not exist.");
        if (user == null) {
            return;
        }
        List<File> files = fileRepository.findAllByOwner(user);
        fileRepository.delete(files);
        userRepository.delete(user.getId());
    }

    @Override
    public long count() {
        return userRepository.count();
    }
}
