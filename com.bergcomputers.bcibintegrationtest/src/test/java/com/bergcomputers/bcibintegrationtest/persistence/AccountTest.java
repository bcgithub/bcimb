package com.bergcomputers.bcibintegrationtest.persistence;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.PersistenceTest;
import org.jboss.arquillian.persistence.TestExecutionPhase;
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


@RunWith(Arquillian.class)
@PersistenceTest
@Transactional(TransactionMode.DISABLED)
public class AccountTest {
	@Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
            .addPackage(Account.class.getPackage())
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
        em.createQuery("delete from Account").executeUpdate();
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        System.out.println("Inserting records...");
        Account u1 = new Account();
        u1.setIban("RO01BC1234");
        u1.setAmount(1000.0);
        u1.setCreationDate(new Date());
        em.persist(u1);
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }

    @CleanupUsingScript(phase = TestExecutionPhase.BEFORE,value="datasets/cleanup-AccountTest.sql")
    @Test
    public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
        // given
        String fetchingAllUsersInJpql = "select u from Account u order by u.id";

        // when
        System.out.println("Selecting (using JPQL)...");
        List<Account> accounts = em.createQuery(fetchingAllUsersInJpql, Account.class).getResultList();

        // then
        System.out.println("Found " + accounts.size() + " accounts (using JPQL):");
        Assert.assertTrue(accounts.get(0).getIban().equals("RO01BC1234"));
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
    }
}
