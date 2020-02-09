package com.drop.newPack.db;

import com.drop.newPack.core.Person;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO extends AbstractDAO<Person> {

    private SessionFactory factory;

    public PersonDAO(SessionFactory factory) {
        super(factory);
        this.factory = factory;
    }

//    public List<Person> findByName(String name) {
//        Query query = factory.getCurrentSession().createSQLQuery("select * from person where firstname like :name or lastname like :name")
//                .setParameter("name", "%" + name + "%");
//        return query.getResultList();
//    }

    public List<Person> findByName(String name) {
        String nameModified = "%" + name + "%";
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Person> cr = builder.createQuery(Person.class);
        Root<Person> root = cr.from(Person.class);
        cr.select(root).where(builder.or(builder.like(root.get("firstname"), nameModified), builder.like(root.get("lastname"), nameModified)));

        Query<Person> query = currentSession().createQuery(cr);
        return query.getResultList();
    }

//    public List<Person> findAll() {
//        Query query = factory.getCurrentSession().createSQLQuery("select * from person");
//        return query.getResultList();
//    }

    //Implemented query using criteria query
    public List<Person> findAll() {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);

        Query<Person> query = currentSession().createQuery(criteriaQuery);
        List<Person> retList = query.getResultList();
        return retList;
    }

    public void create(int id, String firstname, String lastname, String phone) {
        Query query = factory.getCurrentSession().createSQLQuery("insert into person values(:id, :firstname, :lastname, :phone)")
                .setParameter("id", id).setParameter("firstname", firstname)
                .setParameter("lastname", lastname).setParameter("phone", phone);
        query.executeUpdate();
    }
}
