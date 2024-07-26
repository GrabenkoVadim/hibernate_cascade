package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Session session = null;
        Transaction tx = null;
        try {
            session = super.factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Can't create comment " + entity, e);
        } finally {
            if (session != null) session.close();
        }
        return entity;

    }

    @Override
    public Comment get(Long id) {
        try (Session session = super.factory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get comment " + id, e);
        }
    }

    @Override
    public List<Comment> getAll() {
        try (Session session = super.factory.openSession()) {
            return session.createQuery("from Comment", Comment.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all comments", e);
        }
    }

    @Override
    public void remove(Comment entity) {
        Session session = null;
        Transaction tx;
        try {
            session = super.factory.openSession();
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        } catch (Exception e) {
            throw new RuntimeException("Can't remove comment " + entity, e);
        } finally {
            if (session != null) session.close();
        }
    }
}
