package udemy.test_SpringBoot.app.data;

import udemy.test_SpringBoot.app.entities.Account;
import udemy.test_SpringBoot.app.entities.Bank;

import java.math.BigDecimal;
import java.util.Optional;

public class BddData {

    public static Optional<Account> getAccount01() {
        return Optional.of(new Account(1L, "User01", new BigDecimal("1000.00")));
    }

    public static Optional<Account> getAccount02() {
        return Optional.of(new Account(2L, "User02", new BigDecimal("2000.00")));
    }

    public static Optional<Bank> getBank() {
        return Optional.of(new Bank(1L, "Bank01", 0));
    }
}
