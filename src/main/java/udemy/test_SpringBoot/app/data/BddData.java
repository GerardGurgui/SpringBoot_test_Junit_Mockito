package udemy.test_SpringBoot.app.data;

import udemy.test_SpringBoot.app.entities.Account;
import udemy.test_SpringBoot.app.entities.Bank;

import java.math.BigDecimal;

public class BddData {

    public static Account getAccount01() {
        return new Account(1L, "User01", new BigDecimal("1000.00"));
    }

    public static Account getAccount02() {
        return new Account(2L, "User02", new BigDecimal("2000.00"));
    }

    public static Bank getBank() {
        return new Bank(1L, "Bank01", 0);
    }
}
