package core.basesyntax.dao.impl;

import core.basesyntax.dao.UserDao;
import core.basesyntax.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User create(User entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = super.factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error creating user" + entity, e);
        } finally {
            if (session != null) session.close();
        }
        return entity;
    }

    @Override
    public User get(Long id) {
        try (Session session = super.factory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting user" + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = super.factory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all users", e);
        }
    }

    @Override
    public void remove(User entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = super.factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error removing user" + entity, e);
        } finally {
            if (session != null) session.close();
        }
    }
}
