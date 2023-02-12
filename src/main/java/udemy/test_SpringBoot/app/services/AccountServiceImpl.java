package udemy.test_SpringBoot.app.services;

import org.springframework.stereotype.Service;
import udemy.test_SpringBoot.app.entities.Account;
import udemy.test_SpringBoot.app.entities.Bank;
import udemy.test_SpringBoot.app.repositories.AccountRepository;
import udemy.test_SpringBoot.app.repositories.BankRepository;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }


    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public int checkTotalTransfers(Long bankId) {
        Bank bank = bankRepository.findByid(bankId).orElseThrow();
        return bank.getTotalTransfer();
    }

    @Override
    public BigDecimal checkSalary(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        return account.getSalary();
    }

    @Override
    public void transferCredit(Long originAccountId, Long toAccountId,
                               Long bankId,
                               BigDecimal amount) {

        Account originAccount = accountRepository.findById(originAccountId).orElseThrow();
        originAccount.withdraw(amount);
        accountRepository.save(originAccount);

        Account toAccount = accountRepository.findById(toAccountId).orElseThrow();
        toAccount.deposit(amount);

        //si se realiza correctamente la transferencia (sin exceptions) entonces actualizamos el total de transferencias
        Bank bank = bankRepository.findByid(bankId).orElseThrow();
        int totalTransfer = bank.getTotalTransfer();
        bank.setTotalTransfer(++totalTransfer);
        bankRepository.save(bank);
    }
}
