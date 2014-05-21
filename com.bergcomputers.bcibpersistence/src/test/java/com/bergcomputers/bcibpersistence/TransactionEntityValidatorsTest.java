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

import com.bergcomputers.domain.Transaction;

public class TransactionEntityValidatorsTest {
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
    public void test() {

        try {
            Transaction transaction = new Transaction();
        	transaction.setTransactionDate(new Date(10,10,2014));   	
        	transaction.setType("Alimentare Cont");
        	transaction.setAmount(120.0);
        	transaction.setSender("Cineva");
        	transaction.setDetails("Alimentare cont extern");
        	transaction.setStatus("Validat");
        	
            em.persist(transaction);
            
            //em.getTransaction().commit();
            fail("Expected ConstraintViolationException wasn't thrown.");
        }
        catch (ConstraintViolationException e) {
            
        }
    }
}
