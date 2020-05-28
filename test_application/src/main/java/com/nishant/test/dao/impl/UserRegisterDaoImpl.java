package com.nishant.test.dao.impl;

import com.nishant.test.dao.UserRegisterDao;
import com.nishant.test.entity.UserRegister;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRegisterDaoImpl implements UserRegisterDao {

    private SessionFactory sessionFactory;
    
    public UserRegisterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Long saveUser(UserRegister userRegister) {
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(userRegister);
    }
}
