package udemy.test_SpringBoot.app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import udemy.test_SpringBoot.app.exceptions.InsufficentFundsException;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Account {

    private Long id;
    private String accountHolder;
    private BigDecimal salary;

    public void deposit(BigDecimal amount) {
        //volver a asignar el valor de la suma al atributo salary
        this.salary = this.salary.add(amount);
    }

    public void withdraw(BigDecimal amount) {

        BigDecimal newSalary = this.salary.subtract(amount);
        if (newSalary.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficentFundsException("Insufficient funds");
        }
        this.salary = newSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(id, account.id)
                && Objects.equals(accountHolder, account.accountHolder)
                && Objects.equals(salary, account.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountHolder, salary);
    }
}
