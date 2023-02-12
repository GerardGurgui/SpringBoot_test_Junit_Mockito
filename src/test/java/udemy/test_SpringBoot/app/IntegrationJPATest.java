package udemy.test_SpringBoot.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import udemy.test_SpringBoot.app.entities.Account;
import udemy.test_SpringBoot.app.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class IntegrationJPATest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void testFindById(){

        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("User01", account.orElseThrow().getAccountHolder());
    }

    @Test
    void testFindByPersona(){

        Optional<Account> account = accountRepository.findByAccountHolder("User01");
        assertTrue(account.isPresent());
        assertEquals("User01", account.orElseThrow().getAccountHolder());
        assertEquals("1000.00", account.orElseThrow().getSalary().toPlainString());
    }

    @Test
    void testFindByPersona_throwException(){

        Optional<Account> account = accountRepository.findByAccountHolder("UserNotExists");
        assertThrows(NoSuchElementException.class, account::orElseThrow);
        assertFalse(account.isPresent());

    }

    @Test
    void testFindAll(){

        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());
        assertEquals(2, accounts.size());

    }

    @Test
    void testSave(){

        //GIVEN
        Account accountTest = new Account(null,"UserTest", new BigDecimal("3000.00"));
        accountRepository.save(accountTest);

        //when
        //no hay certeza de los ids que se generan en el test, por los rollbacks que utilizamos en jpa y h2 con test
        Account account = accountRepository.findByAccountHolder("UserTest").orElseThrow();

        //then
        assertTrue(accountRepository.findByAccountHolder("UserTest").isPresent());
        assertEquals("UserTest", account.getAccountHolder());
//        assertEquals(3, accountTest.getId());
    }

    @Test
    void testUpdate(){

        //GIVEN
        Account accountTest = new Account(null,"UserTest", new BigDecimal("3000.00"));


        //when
        accountTest.setAccountHolder("UserTestUpdated");
        accountTest.setSalary(new BigDecimal("4000.00"));
        Account accountUpdated = accountRepository.save(accountTest);

        //then
        assertEquals("UserTestUpdated", accountUpdated.getAccountHolder());
        assertEquals("4000.00", accountUpdated.getSalary().toPlainString());
//        assertEquals(3, accountTest.getId());
    }

    @Test
    void testDelete(){

        //GIVEN
        Account accountTest = accountRepository.findById(1L).orElseThrow();
        assertEquals("User01", accountTest.getAccountHolder());

        accountRepository.delete(accountTest);

        assertThrows(NoSuchElementException.class, () -> accountRepository.findById(1L).orElseThrow());

        assertEquals(1, accountRepository.findAll().size());

    }



}
