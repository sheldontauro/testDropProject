package com.drop.newPack.resources;

import com.drop.newPack.core.Person;
import com.drop.newPack.db.PersonDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;
import java.util.Optional;

@Path("/person")
public class PersonResource {
    private PersonDAO personDAO;

    public PersonResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public List<Person> findByName(@QueryParam("name") Optional<String> name ) {
        if(name.isPresent()) {
            return personDAO.findByName(name.get());
        }
        else {
            return personDAO.findAll();
        }
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPerson(@Valid Person person) {
        personDAO.create(person.getId(), person.getFirstname(), person.getLastname(), person.getPhone());
    }
}
