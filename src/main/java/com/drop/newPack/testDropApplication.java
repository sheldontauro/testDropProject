package com.drop.newPack;

import com.drop.newPack.core.Person;
import com.drop.newPack.db.PersonDAO;
import com.drop.newPack.resources.PersonResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class testDropApplication extends Application<testDropConfiguration> {

    public static void main(String[] args) throws Exception {
        new testDropApplication().run(args);
    }

    private final HibernateBundle<testDropConfiguration> hibernateBundle = new HibernateBundle<testDropConfiguration>(Person.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(testDropConfiguration testDropConfiguration) {
            return testDropConfiguration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<testDropConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(testDropConfiguration testDropConfiguration, Environment environment) throws Exception {
        final PersonDAO personDAO = new PersonDAO(hibernateBundle.getSessionFactory());

        environment.jersey().register(new PersonResource(personDAO));
    }
}
