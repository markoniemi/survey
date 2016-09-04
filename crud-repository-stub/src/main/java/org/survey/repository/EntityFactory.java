package org.survey.repository;

import java.io.Serializable;
import java.util.List;

/**
 * EntityFactory is needed for testing CrudRepository.
 * 
 * @see org.springframework.data.repository.CrudRepository<T, ID>
 * @param <T>
 *            the domain type the repository manages
 * @param <ID>
 *            the type of the id of the entity the repository manages
 */
public interface EntityFactory<T, ID extends Serializable> {
    /**
     * Return a list containing entities amount of which is specified by count.
     */
    List<T> getEntities(int count);

    /**
     * Create an updated entity, used by update test.
     */
    T getUpdatedEntity(T entity);
}
