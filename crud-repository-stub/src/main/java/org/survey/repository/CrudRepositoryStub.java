package org.survey.repository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.Lists;

public class CrudRepositoryStub<T, ID extends Serializable> implements PagingAndSortingRepository<T, ID> {
    protected Set<T> entities = new HashSet<T>();
    protected Long generatedId = Long.valueOf(1);

    @Override
    public <S extends T> S save(S entity) {
        if (getId(entity) == null) {
            generateId(entity);
        } else {
            delete(entity);
        }
        entities.add(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        Iterable<S> entityList = IteratorUtils.toList(entities.iterator());
        for (T entity : entityList) {
            if (getId(entity) == null) {
                generateId(entity);
            }
            this.entities.add(entity);
        }
//        this.entities.addAll(entities.iterator());
        return entityList;
    }

    @Override
    public T findOne(ID id) {
        if (entities.isEmpty()) {
            return null;
        }
        if (id == null) {
            return null;
        }
        for (T entity : entities) {
            if (id.equals(getId(entity))) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        Set<T> foundEntities = new HashSet<T>();
        for (ID id : ids) {
            foundEntities.add(findOne(id));
        }
        return foundEntities;
    }

    @Override
    public boolean exists(ID id) {
        if (id == null) {
            throw new InvalidDataAccessApiUsageException("The given id must not be null");
        }
        return findOne(id) != null;
    }

    @Override
    public Iterable<T> findAll() {
        return entities;
    }

    @Override
    public long count() {
        return entities.size();
    }

    @Override
    public void delete(ID id) {
        T entity = findOne(id);
        if (entity != null) {
            entities.remove(entity);
        }
    }

    @Override
    public void delete(T entity) {
        if (exists(getId(entity))) {
            entities.remove(entity);
        }
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        this.entities.removeAll(IteratorUtils.toList(entities.iterator()));
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @SuppressWarnings("unchecked")
    ID getId(T entity) {
        return (ID) BeanHelper.getValueOfAnnotatedField(entity, Id.class);
    }

    @SuppressWarnings("unchecked")
    void generateId(T entity) {
        BeanHelper.setValueOfAnnotatedField(entity, GeneratedValue.class, (ID) generatedId);
        generatedId++;
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return new PageImpl<T>(Lists.newArrayList(findAll()));
    }
}
