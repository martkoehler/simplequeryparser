package de.koehler.jpa;

import de.koehler.parser.ParseException;
import de.koehler.parser.SimpleQueryParser;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class MinionRepository {
    private EntityManager entityManager;

    public MinionRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Minion find(final String name) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Minion> query = cb.createQuery(Minion.class);

        Root<Minion> root = query.from(Minion.class);
        root.fetch(Minion_.boss, JoinType.LEFT);

        query
                .select(root)
                .where(cb.equal(root.get(Minion_.name), name));

        TypedQuery<Minion> emQuery = entityManager.createQuery(query);

        return emQuery.getSingleResult();

    }

    public Minion find() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Minion> query = cb.createQuery(Minion.class);

        SimpleQueryParser parser = new SimpleQueryParser(cb, "(query");

        Root<Minion> root = query.from(Minion.class);

        try {
            query
                    .select(root)
                    .where(parser.parse());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TypedQuery<Minion> emQuery = entityManager.createQuery(query);

        return emQuery.getSingleResult();
    }

}
