package com.bergcomputers.bcibintegrationtest.persistence;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bergcomputers.domain.Account;
import com.bergcomputers.domain.Transaction;


@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.DISABLED)
public class TransactionTest {
	@Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Transaction.class.getPackage())
            .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	
	@PersistenceContext
    EntityManager em;

    @Inject
    UserTransaction utx;

    @Before
    public void preparePersistenceTest() throws Exception {
        clearData();
        insertData();
        startTransaction();
    }
    //@Transactional(TransactionMode.DISABLED)
    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Dumping old records...");
        em.createQuery("delete from Transaction").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        Transaction u1 = new Transaction();
        Account a1 = new Account();
        u1.setAccount(a1);
        u1.setTransactionDate(new Date());
        u1.setType("Credit");
        u1.setAmount(1222.0);
        u1.setSender("Cineva");
        u1.setDetails("Alimentare cont");
        u1.setStatus("Accepted");
        em.persist(u1);
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @Test
    public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
        // given
        String fetchingAllUsersInJpql = "select u from Transaction u order by u.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Transaction> transactions = em.createQuery(fetchingAllUsersInJpql, Transaction.class).getResultList();

        // then
        System.out.println("Found " + transactions.size() + " transactions (using JPQL):");
        Assert.assertTrue(transactions.get(0).getAccount().equals("RO01BC1234"));
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }
}
