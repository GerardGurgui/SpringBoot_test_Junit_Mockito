package udemy.test_SpringBoot.app.repositories;

import org.springframework.stereotype.Repository;
import udemy.test_SpringBoot.app.entities.Account;

import java.util.List;

@Repository
public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);

    void update(Account account);

}
