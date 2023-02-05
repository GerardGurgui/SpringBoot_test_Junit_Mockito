package udemy.test_SpringBoot.app.services;

import udemy.test_SpringBoot.app.entities.Account;

import java.math.BigDecimal;

public interface IAccountService {
    Account findById(Long id);

    int checkTotalTransfers(Long bankId);

    BigDecimal checkSalary(Long id);

    void transferCredit(Long fromAccountId, Long toAccountId, Long bankId, BigDecimal amount);

}
