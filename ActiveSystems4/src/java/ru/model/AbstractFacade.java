/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.model;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vitaly_Petrov
 */
@Component
public abstract class AbstractFacade<T> {
    
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract HibernateTemplate getHibernateTemplate();

    public Long create(T entity) {
        return (Long) getHibernateTemplate().save(entity);
    }

    public void edit(T entity) {
        getHibernateTemplate().update(entity);
    }

    public void remove(T entity) {
        getHibernateTemplate().delete(entity);
    }

    public List<T> findAll() {
        return (List<T>) getHibernateTemplate().find("from "+entityClass.getName());
    }

    public int count() {
        List<T> listT = (List<T>) getHibernateTemplate().find("from "+entityClass.getName());
        return listT.size();
    }
    
}
