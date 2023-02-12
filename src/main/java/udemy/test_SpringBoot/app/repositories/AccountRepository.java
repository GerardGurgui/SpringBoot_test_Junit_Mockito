package udemy.test_SpringBoot.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udemy.test_SpringBoot.app.entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    //consulta a la clase entity cuenta, recibe un wildcard para encontrarlo
    @Query("SELECT a FROM Account a WHERE a.accountHolder = ?1")
    Optional<Account> findByAccountHolder(String accountHolder);

}
