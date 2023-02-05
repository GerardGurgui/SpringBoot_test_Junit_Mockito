package udemy.test_SpringBoot.app.services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import udemy.test_SpringBoot.app.data.BddData;
import udemy.test_SpringBoot.app.entities.Account;
import udemy.test_SpringBoot.app.entities.Bank;
import udemy.test_SpringBoot.app.exceptions.InsufficentFundsException;
import udemy.test_SpringBoot.app.repositories.AccountRepository;
import udemy.test_SpringBoot.app.repositories.BankRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    @InjectMocks
    AccountServiceImpl accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    BankRepository bankRepository;


    @Test
    void contextLoads() {

        when(accountRepository.findById(1L)).thenReturn(BddData.getAccount01());
        when(accountRepository.findById(2L)).thenReturn(BddData.getAccount02());

        when(bankRepository.findByid(1L)).thenReturn(BddData.getBank());

        BigDecimal originSalary = accountService.checkSalary(1L);
        BigDecimal destinySalary = accountService.checkSalary(2L);

        assertEquals("1000.00", originSalary.toPlainString());
        assertEquals("2000.00", destinySalary.toPlainString());

        accountService.transferCredit(1L, 2L, 1L, new BigDecimal("100.00"));
        originSalary = accountService.checkSalary(1L);
        destinySalary = accountService.checkSalary(2L);

        assertEquals("900.00", originSalary.toPlainString());
        assertEquals("2100.00", destinySalary.toPlainString());

        int totalTransfers = accountService.checkTotalTransfers(1L);
        assertEquals(1, totalTransfers);

        verify(accountRepository,times(3)).findById(1L);
        verify(accountRepository, times(3)).findById(2L);
        verify(accountRepository, times(6)).findById(anyLong());

        verify(bankRepository, times(2)).findByid(1L);
        verify(bankRepository).update(any(Bank.class));


    }

    @Test
    void contextLoadsThrows() {

        //cuidado con los valores constantes en este segundo contextLoad
        //en el primer metodo los modificamos con las operaciones de transferencia
        when(accountRepository.findById(1L)).thenReturn(BddData.getAccount01());
        when(accountRepository.findById(2L)).thenReturn(BddData.getAccount02());

        when(bankRepository.findByid(1L)).thenReturn(BddData.getBank());

        BigDecimal originSalary = accountService.checkSalary(1L);
        BigDecimal destinySalary = accountService.checkSalary(2L);

        assertEquals("1000.00", originSalary.toPlainString());
        assertEquals("2000.00", destinySalary.toPlainString());

//        PROBAMOS A LANZAR LA EXCEPCION
        assertThrows(InsufficentFundsException.class, () -> {
            accountService.transferCredit(1L, 2L, 1L, new BigDecimal("1200.00"));
        });

        originSalary = accountService.checkSalary(1L);
        destinySalary = accountService.checkSalary(2L);

        assertEquals("1000.00", originSalary.toPlainString());
        assertEquals("2000.00", destinySalary.toPlainString());

        int totalTransfers = accountService.checkTotalTransfers(1L);
        //no se ha realizado ninguna transferencia por la excepcion
        assertEquals(0, totalTransfers);

        verify(accountRepository,times(3)).findById(1L);
        verify(accountRepository, times(2)).findById(2L);

        verify(bankRepository, times(1)).findByid(1L);
        verify(bankRepository,never()).update(any(Bank.class));
    }

    @Test
    void contextLoadSame(){

        when(accountRepository.findById(1L)).thenReturn(BddData.getAccount01());

        Account account = accountService.findById(1L);
        Account account2 = accountService.findById(1L);

        assertSame(account, account2);
        verify(accountRepository, times(2)).findById(1L);

    }
}