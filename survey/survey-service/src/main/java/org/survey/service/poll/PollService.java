package org.survey.service.poll;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.survey.model.poll.Poll;

/**
 * PollServiceImpl is an WebService implementation of PollService. Can not use
 * delegate design pattern, since PollRepository uses overloaded methods, and
 * apparently WS-I profile does not support them. Even so, it might be better to
 * reveal only some of the PollRepository methods as WebService. Also,
 * WebService does not support lists without extensions. WebParam annotations
 * are not necessary, but adds proper names to parameters in WSDL.
 */
@WebService
public interface PollService {
    /**
     * Returns all polls from repository.
     */
    Poll[] findAll();

    Poll[] findByOwner(String username);

    /**
     * Creates a poll to repository.
     * 
     * @throws NullPointerException
     *             if poll is null.
     * @throws IllegalArgumentException
     *             if poll with same name already exists
     */
    Poll create(@WebParam(name = "poll") Poll poll);

    /**
     * Updates a poll in repository.
     */
    Poll update(@WebParam(name = "poll") Poll poll);

    /**
     * Returns a poll by name.
     */
    Poll findOne(@WebParam(name = "name") String name);

    /**
     * Returns true if a poll by name exists.
     */
    boolean exists(@WebParam(name = "name") String name);

    /**
     * Deletes a poll by name.
     */
    void delete(@WebParam(name = "name") String name);

    /**
     * Returns the count of polls in repository.
     */
    long count();
}
