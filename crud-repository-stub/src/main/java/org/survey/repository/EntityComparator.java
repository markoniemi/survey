package org.survey.repository;

import java.io.Serializable;
import java.util.Comparator;

/**
 * EntityComparator is needed for testing CrudRepository. It should test all
 * persistent fields of entity.
 * 
 * @see org.springframework.data.repository.CrudRepository<T, ID>
 * @param <T>
 *            the domain type the repository manages
 * @param <ID>
 *            the type of the id of the entity the repository manages
 */
public abstract class EntityComparator<T, ID extends Serializable> implements
        Comparator<T> {

    @Override
    public abstract int compare(T entity1, T entity2);
}
