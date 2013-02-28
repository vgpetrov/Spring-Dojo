/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import ru.db.entities.Deviation;

/**
 *
 * @author Vitaly_Petrov
 */
@Component
public class DeviationFacade extends AbstractFacade<Deviation> {
    
    @Autowired
    HibernateTemplate hibernateTemplate;

    public DeviationFacade() {
        super(Deviation.class);
    }
    
    @Override
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }
    
}
