package com.bergcomputers.bcibpersistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bergcomputers.domain.Account;

public class AccountEntityValidatorsTest {
	private static EntityManagerFactory emf;

    private EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("testPU");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        emf.close();
    }

    @Before
    public void beginTransaction() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {

        if (em.getTransaction().isActive())
            em.getTransaction().rollback();

        if (em.isOpen())
            em.close();
    }

    @Test
    public void ibanTooShort() {

        try {
            Account account = new Account();
           account.setIban("robuc");
           account.setCreationDate(new Date());
           // account.setAmount(200.0);
            em.persist(account);
            //em.getTransaction().commit();
            fail("Expected ConstraintViolationException wasn't thrown.");
        }
        catch (ConstraintViolationException e) {
            assertEquals(1, e.getConstraintViolations().size());
            ConstraintViolation<?> violation =
                e.getConstraintViolations().iterator().next();

            assertEquals("iban", violation.getPropertyPath().toString());
            assertEquals(
                Size.class,
                violation.getConstraintDescriptor().getAnnotation().annotationType());
        }
    }
}
