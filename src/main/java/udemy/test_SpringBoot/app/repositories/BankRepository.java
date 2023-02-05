package udemy.test_SpringBoot.app.repositories;

import org.springframework.stereotype.Repository;
import udemy.test_SpringBoot.app.entities.Bank;

import java.util.List;
@Repository
public interface BankRepository {

    List<Bank> findAll();
    Bank findByid(Long id);

    void update(Bank bank);
}
